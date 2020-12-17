internal data class Point4D(val x: Int, val y: Int, val z: Int = 0, val zz: Int = 0)
internal fun game(area: List<CharArray>, dim: Int) {
    var active = ArrayList<Point4D>()
    for (i in area.indices) for (j in area[i].indices) if (area[i][j] != '.') active.add(Point4D(i, j))
    for (loop in 1..6) {
        var nc = HashMap<Point4D, Int>()
        for (cube in active) {
            for (dx in -1..1) for (dy in -1..1) for (dz in -1..1) for (dzz in -1..1) {
                if (dx == 0 && dy == 0 && dz == 0 && dzz == 0) continue
                if (dim == 3 && dzz != 0) continue
                if (dim == 2 && dz != 0) continue
                val m = Point4D(cube.x + dx,cube.y + dy,cube.z + dz, cube.zz + dzz)
                nc[m] = nc.getOrDefault(m, 0) + 1
            }
        }
        for (cube in active) {
            val c = nc.getOrDefault(cube, 0)
            if (c < 3) nc[cube] = c + 1
        }
        active.clear()
        active.addAll(nc.filter { it.value == 3 }.keys)
    }
    println(active.size)
}

fun main(args: Array<String>) {
    var area = allLines(args, "day17.in").map { it.toCharArray() }.toList()
    game(area, 3)
    game(area, 4)
}