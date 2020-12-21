internal fun bswap(code: Int): Int {
    return (0 until 10).fold(0) { r, i -> r * 2 + if (code and (1 shl i) != 0) 1 else 0 }
}

internal val monster = arrayListOf(
    "                  # ".toCharArray(),
    "#    ##    ##    ###".toCharArray(),
    " #  #  #  #  #  #   ".toCharArray()
)

internal data class Tile(val id: Long, var area: ArrayList<CharArray>, val dim: Int = 10) {
    init {
        if (area.isEmpty()) area = ArrayList((0 until dim).map { CharArray(dim) { ' ' } })
    }

    fun code(side: Char): Int {
        when (side) {
            'T' -> return (0 until 10).fold(0) { r, i -> r * 2 + if (area[0][i] == '#') 1 else 0 }
            'R' -> return (0 until 10).fold(0) { r, i -> r * 2 + if (area[i][9] == '#') 1 else 0 }
            'B' -> return (0 until 10).fold(0) { r, i -> r * 2 + if (area[9][i] == '#') 1 else 0 }
            'L' -> return (0 until 10).fold(0) { r, i -> r * 2 + if (area[i][0] == '#') 1 else 0 }
        }
        throw IllegalArgumentException()
    }

    fun scan(pattern: List<CharArray>): Int {
        var cnt = 0
        for (y in 0..dim - pattern.size) {
            for (x in 0..dim - pattern[0].size) {
                var match = true
                for (i in pattern.indices) {
                    for (j in pattern[i].indices) {
                        if (pattern[i][j] == '#' && area[y + i][x + j] == '.') match = false
                    }
                }
                if (match) {
                    for (i in pattern.indices) {
                        for (j in pattern[i].indices) {
                            if (pattern[i][j] == '#') area[y + i][x + j] = 'O'
                        }
                    }
                    cnt++
                }
            }
        }
        return cnt
    }

    fun rotate() {
        var rot = ArrayList<CharArray>(area.size)
        for (i in area.indices) {
            rot.add(CharArray(area[i].size))
            for (j in area.indices) rot[i][j] = area[(dim - 1) - j][i]
        }
        area = rot
    }

    fun flipX() {
        for (i in area.indices) area[i].reverse()
    }

    fun flipY() {
        area.reverse()
    }

    fun align(side: Char, expected: Int) {
        while (code(side) != expected) {
            if (code(side) == bswap(expected)) when (side) {
                'L', 'R' -> flipY()
                'T', 'B' -> flipX()
            } else rotate()
        }
    }

    var neighbors = ArrayList<Int>()
    var pos = Point(0, 0)
    var cache = HashSet<Int>()

}

fun main(args: Array<String>) {
    val fmt = "Tile (\\d+):".toRegex()
    var sides = arrayListOf('L', 'T', 'B', 'R')
    var tiles = allBlocks(args, "day20.in").map {
        val lines = it.split('\n')
        val id = fmt.matchEntire(lines.first())!!.groupValues[1].toLong()
        Tile(id, ArrayList<CharArray>(lines.subList(1, 11).map { it.toCharArray() }))
    }
    var codes = HashMap<Int, Int>()
    for (i in tiles.indices) {
        for (side in sides) {
            var code = tiles[i].code(side)
            if (codes.containsKey(code)) {
                tiles[i].neighbors.add(codes[code]!!)
                tiles[codes[code]!!].neighbors.add(i)
            }
            tiles[i].cache.add(code)
            codes[code] = i
            code = bswap(code)
            tiles[i].cache.add(code)
            codes[code] = i
        }
    }
    println(tiles.fold(1L) { r, t -> if (t.neighbors.size == 2) r * t.id else r })
    var cur = tiles.indices.fold(0) { r, t -> if (tiles[t].neighbors.size < tiles[r].neighbors.size) t else r }
    val nnc = tiles[cur].neighbors.flatMap { tiles[it].cache }.toHashSet()
    val lc = tiles[cur].code('L')
    if (nnc.contains(lc)) tiles[cur].flipX()
    val tc = tiles[cur].code('T')
    if (nnc.contains(tc)) tiles[cur].flipY()
    var visited = arrayListOf(cur)
    while (true) {
        val rc = tiles[cur].code('R')
        cur = tiles[cur].neighbors.firstOrNull { tiles[it].cache.contains(rc) } ?: break
        tiles[cur].align('L', rc)
        tiles[cur].pos.x = visited.size
        visited.add(cur)
    }
    var idx = 0
    while (visited.size < tiles.size) {
        cur = visited[idx]
        val bc = tiles[cur].code('B')
        val next = tiles[cur].neighbors.first { tiles[it].cache.contains(bc) }
        tiles[next].align('T', bc)
        tiles[next].pos = tiles[cur].pos + Point(0, 1)
        visited.add(next)
        idx += 1
    }
    var map = Tile(0, ArrayList(), 8 * Math.sqrt(tiles.size.toDouble()).toInt())
    println("Building map dim=${map.dim}")
    for (t in tiles.indices) for (y in 1..8) for (x in 1..8) {
        map.area[tiles[t].pos.y * 8 + y - 1][tiles[t].pos.x * 8 + x - 1] = tiles[t].area[y][x]
    }
    for (t in 1..7) {
        val cnt = map.scan(monster)
        println("try $t: $cnt")
        if (cnt > 0) break
        if (t == 4) map.flipY() else map.rotate()
    }
    for (k in 0 until map.dim) println(map.area[k])
    println(map.area.map { it.count { c -> c == '#' } }.sum())
}
