internal data class Cup(val label: Int, var next: Cup? = null)

internal fun game(nums: List<Int>, iter: Int) : Cup {
    var cups = ArrayList(nums.map { Cup(it) })
    cups.zipWithNext { a, b -> a.next = b }
    cups.last().next = cups.first()
    var cur = cups.first()
    for (i in 1..nums.size) {
        cups[cur.label - 1] = cur
        cur = cur.next!!
    }
    for (i in 1..iter) {
        var tail = cur
        var head = cur.next
        var used = hashSetOf(cur.label)
        for (j in 1..3) {
            tail = tail.next!!
            used.add(tail.label)
        }
        var d = cur.label
        while (used.contains(d)) d = if (d > 1) d - 1 else nums.size
        var dst = cups[d - 1]
        cur.next = tail.next
        tail.next = dst.next
        dst.next = head
        cur = cur.next
    }
    while (cur.label != 1) cur = cur.next!!
    return cur
}

internal fun CupsString(from: Cup, count: Int = 9): String {
    var ret = StringBuilder()
    var cur = from
    for (i in 1..count) {
        ret.append(cur.label)
        cur = cur.next!!
    }
    return ret.toString()
}

fun main() {
    var input = ArrayList(readLine()!!.map { it.toString().toInt() })
    println(CupsString(game(input, 100)))
    input.addAll(10..1000000)
    val res = game(input, 10000000)
    println(res.next!!.label.toLong() * res.next!!.next!!.label.toLong())
}