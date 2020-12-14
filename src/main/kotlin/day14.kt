internal var counter = 0L

internal data class FuzzyTree(var left: FuzzyTree?, var right: FuzzyTree?, var fuzzy: Boolean, var value: Long) {
    constructor(bits: Int = 36, data: Long = 0) : this(null, null, false, data) {
        if (bits > 0) {
            left = FuzzyTree(bits - 1, data)
            fuzzy = true
        }
    }

    fun write(mask: CharSequence, addr: Long, data: Long, pos: Int = 0) {
        if (pos < mask.length) {
            when (mask[pos]) {
                'X' -> {
                    left?.write(mask, addr, data, pos + 1)
                    if (!fuzzy) right?.write(mask, addr, data, pos + 1)
                }
                '1' -> {
                    defuzz()
                    right!!.write(mask, addr, data, pos + 1)
                }
                '0' -> {
                    defuzz()
                    val bit = addr and (1L shl (35 - pos))
                    if (bit != 0L) {
                        right!!.write(mask, addr, data, pos + 1)
                    } else {
                        left!!.write(mask, addr, data, pos + 1)
                    }
                }
            }
        } else {
            value = data
            counter += 1
        }
    }

    fun defuzz() {
        if (fuzzy) {
            right = left!!.copy()
            fuzzy = false
        }
    }

    fun copy(): FuzzyTree {
        var leftCopy = left?.copy()
        var rightCopy = if (fuzzy) leftCopy else right?.copy()
        return FuzzyTree(leftCopy, rightCopy, fuzzy, value)
    }

    fun dump(bits: Int = 36, prefix: String = "") {
        if (bits > 0) {
            if (fuzzy) {
                left!!.dump(bits - 1, prefix + "X")
            } else {
                left?.dump(bits - 1, prefix + "0")
                right?.dump(bits - 1, prefix + "1")
            }
        } else {
            println("$prefix: $value")
        }
    }

    fun sum(bits: Int = 36): Long {
        if (bits > 0) {
            if (fuzzy) {
                return 2 * left!!.sum(bits - 1)
            } else {
                var rsum = right?.sum( bits -1 ) ?: 0L
                return rsum + left!!.sum( bits - 1)
            }
        } else {
            return value
        }
    }
}

fun main(args: Array<String>) {
    val fmt = "([a-z]+)\\[?([0-9]+)?\\]?\\s=\\s([0-9X]+)".toRegex()
    var fuzz = "000000000000000000000000000000000000"
    var mask = 0L
    var bits = 0L
    var memm = Array(65536) { 0L }
    var tree = FuzzyTree()
    allLines(args, "day14.in").toList().forEach {
        fmt.matchEntire(it)?.destructured?.let { (cmd, addr, value) ->
            //println("$cmd $addr $value")
            when (cmd) {
                "mask" -> {
                    mask = value.replace('0', '1').replace('X', '0').toLong(2)
                    bits = value.replace('X', '0').toLong(2)
                    fuzz = value
                }
                "mem" -> {
                    val num = value.toLong()
                    val masked = (num - (num and mask)) or bits
                    memm[addr.toInt()] = masked
                    tree.write(fuzz, addr.toLong(), num)
                }
            }
            //tree.dump()
        }
    }
    println(memm.sum())
    println(tree.sum())
    println(counter)
}
