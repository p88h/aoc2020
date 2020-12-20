internal data class Tile(val id: Long, val area: List<CharArray>) {
    fun code(side: Char): Int {
        when (side) {
            'T' -> return (0 until 10).fold(0) { r, i -> r * 2 + if (area[0][i] == '#') 1 else 0 }
            'R' -> return (0 until 10).fold(0) { r, i -> r * 2 + if (area[i][9] == '#') 1 else 0 }
            'B' -> return (0 until 10).fold(0) { r, i -> r * 2 + if (area[9][i] == '#') 1 else 0 }
            'L' -> return (0 until 10).fold(0) { r, i -> r * 2 + if (area[i][0] == '#') 1 else 0 }
        }
        throw IllegalArgumentException()
    }

    var neighbors = ArrayList<Int>()
    var x = -1
    var y = -1
}

internal fun flip(code: Int): Int {
    return (0 until 10).fold(0) { r, i -> r * 2 + if (code and (1 shl i) != 0) 1 else 0 }
}

fun main(args: Array<String>) {
    val fmt = "Tile (\\d+):".toRegex()
    var sides = arrayListOf('L', 'T', 'B', 'R')
    var tiles = allBlocks(args, "day20.in").map {
        val lines = it.split('\n')
        val id = fmt.matchEntire(lines.first())!!.groupValues[1].toLong()
        Tile(id, lines.takeLast(10).map { it.toCharArray() })
    }
    var codes = HashMap<Int, Int>()
    for (i in tiles.indices) {
        for (side in sides) {
            var code = tiles[i].code(side)
            if (!codes.containsKey(code)) {
                code = flip(code)
            }
            if (codes.containsKey(code)) {
                tiles[i].neighbors.add(codes[code]!!)
                tiles[codes[code]!!].neighbors.add(i)
            }
            codes[code] = i
        }
    }
    println(tiles.fold(1L) { r, t -> if (t.neighbors.size == 2) r * t.id else r })
}
