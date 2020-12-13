internal fun inv(a0: Long, m0: Long): Long {
    var m = m0; var a = a0; var x0 = 0L; var x1 = 1L
    while (a > 1) {
        val q = a / m;
        val t0 = m; m = a % m; a = t0
        val t1 = x0; x0 = x1 - q * x0; x1 = t1
    }
    return if (x1 < 0) x1 + m0 else x1
}

fun main(args: Array<String>) {
    val input = allLines(args, "day13.in").toList()
    val start = input.first().toLong()
    val sched = input.last().split(',').mapIndexed { p, v -> v to p }
        .filter { it.first != "x" }.map { it.first.toLong() to it.second }.toList()
    val bus = sched.map { it.first }.map { it to (it - (start % it)) % it }.minByOrNull { it.second }
    println(bus!!.first * bus.second)
    val prod = sched.map { it.first }.reduce { a, b -> a * b }
    println(sched.map {
        val rem = (it.first - it.second) % it.first
        val pp = prod / it.first
        ( rem * inv(pp, it.first) * pp )
    }.sum() % prod)
}