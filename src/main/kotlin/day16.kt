internal data class Range(val name: String, val a: Pair<Int, Int>, val b: Pair<Int, Int>) {
    var invalid = HashSet<Int>()
    var pos = -1
}

internal val pattern = "([a-z ]+): ([0-9]+)-([0-9]+) or ([0-9]+)-([0-9]+)".toRegex()
internal fun parseRange(input: String): Range {
    pattern.matchEntire(input)!!.destructured.let { (n, a1, a2, b1, b2) ->
        return Range(n, a1.toInt() to a2.toInt(), b1.toInt() to b2.toInt())
    }
}

internal fun validFor(range: Range, value: Int): Boolean {
    return (value in range.a.first..range.a.second) || (value in range.b.first..range.b.second)
}

fun main(args: Array<String>) {
    var ranges = ArrayList<Range>()
    var state = 0
    var errors = 0
    var ticket : List<Int>? = null
    var others = ArrayList<List<Int>>()
    for (line in allLines(args, "day16.in")) {
        if (line.isEmpty()) {
            state += 1
        } else when (state) {
            0 -> ranges.add(parseRange(line))
            1, 3 -> state += 1  // headers
            2 -> ticket = line.split(',').map { it.toInt() }.toList()
            4 -> {
                val tkt = line.split(',').map { it.toInt() }
                val err = tkt.filter { ranges.none { r -> validFor(r, it) } }
                if (err.isEmpty()) {
                    others.add(tkt)
                    tkt.forEachIndexed { pos, n ->
                        ranges.forEach {
                            if (!it.invalid.contains(pos) && !validFor(it, n)) {
                                it.invalid.add(pos)
                            }
                        }
                    }
                } else {
                    errors += err.sum()
                }
            }
        }
    }
    println(errors)
    var prod = 1L
    // naive elimination seems to handle this...
    while (ranges.any { it.pos == -1 } ) {
        var r = ranges.first{ it.invalid.size == ranges.size - 1 }
        val p = (0 until ranges.size).first { !r.invalid.contains(it) }
        r.pos = p
        ranges.forEach { it.invalid.add(p) }
        println("${r.name}: $p")
        if (r.name.startsWith("departure")) prod *= ticket!![p].toLong()
    }
    println(prod)
}
