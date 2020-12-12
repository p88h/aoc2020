fun main(args: Array<String>) {
    val seats = allLines(args, "day5.in").map {
        it.toCharArray().fold(0) { r, c -> (r * 2 + (1 - (c.toInt().and(4)) / 4)) }
    }.sorted().toList()
    println("min: ${seats.first()} max: ${seats.last()}")
    println(seats.fold(seats.first()) { r, n -> if (n == r) n + 1 else r })
}