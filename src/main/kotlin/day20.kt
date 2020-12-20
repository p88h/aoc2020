internal data class Tile(val name: String, val area: List<CharArray>) {
    fun code(side: Char): Int {
        when (side) {
            'L' -> return (0 until 10).fold(0) { r, i -> r * 2 + if (area[0][9 - i] == '#') 1 else 0 }
            'T' -> return (0 until 10).fold(0) { r, i -> r * 2 + if (area[i][0] == '#') 1 else 0 }
            'B' -> return (0 until 10).fold(0) { r, i -> r * 2 + if (area[i][9] == '#') 1 else 0 }
            'R' -> return (0 until 10).fold(0) { r, i -> r * 2 + if (area[9][9 - i] == '#') 1 else 0 }
        }
        throw IllegalArgumentException()
    }
}

fun main(args: Array<String>) {
    val tiles = allBlocks(args, "day19.in").map {
        val lines = it.split('\n')
        Tile(lines.first(), lines.takeLast(10).map { it.toCharArray() })
    }
}
