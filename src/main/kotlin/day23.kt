internal fun game(nums: List<Int>, iter: Int) : IntArray {
    var next = IntArray(nums.size + 1)
    nums.zipWithNext { a, b -> next[a] = b }
    next[nums.last()] = nums.first()
    var cur = nums.first()
    for (i in 1..iter) {
        var tail = cur
        var head = next[cur]
        var used = hashSetOf(cur)
        for (j in 1..3) {
            tail = next[tail]
            used.add(tail)
        }
        var dst = cur
        while (used.contains(dst)) dst = if (dst > 1) dst - 1 else nums.size
        next[cur] = next[tail]
        next[tail] = next[dst]
        next[dst] = head
        cur = next[cur]
    }
    return next
}

internal fun walk(next: IntArray, count: Int = 9): String {
    var ret = StringBuilder()
    var cur = 1
    for (i in 1..count) {
        ret.append(cur)
        cur = next[cur]
    }
    return ret.toString()
}

fun main() {
    var input = ArrayList(readLine()!!.map { it.toString().toInt() })
    println(walk(game(input, 100)))
    input.addAll(10..1000000)
    val res = game(input, 10000000)
    println(res[1].toLong() * res[res[1]].toLong())
}