fun main() {
    var prev = HashMap<Int, Int>()
    var age = 0
    val input = readLine()!!.split(',').map{ it.toInt() }
    for (turn in 1..30000000) {
        val num = if (turn <= input.size) input[turn - 1] else age
        age = if (prev.containsKey(num)) turn - prev[num]!! else 0
        prev[num] = turn
        if (turn == 2020 || turn % 1000000 == 0) {
            println("$turn: $num")
        }
    }
}
