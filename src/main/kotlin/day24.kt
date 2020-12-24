fun main(args: Array<String>) {
    var black = HashSet<Point>()
    val dirs = hashMapOf(
        "ne" to Point(+1, -1), "se" to Point(+1, +1), "e" to Point(+2, +0),
        "nw" to Point(-1, -1), "sw" to Point(-1, +1), "w" to Point(-2, +0),
    )
    val fmt = "(?<=[ew])".toRegex()
    for (line in allLines(args, "day24.in")) {
        val p = line.trim().split(fmt).fold(Point(0, 0)) { r, c -> r + dirs.getOrDefault(c, Point(0, 0)) }
        if (black.contains(p)) black.remove(p) else black.add(p)
    }
    println(black.size)
    for (d in 1..100) {
        var adj = HashMap<Point, Int>().also { it.putAll(black.map { it to 10 }) }
        for (p in black) for (dir in dirs.values) adj[p + dir] = adj.getOrDefault(p + dir, 0) + 1
        black = adj.filter { it.value == 2 || it.value in 11..12 }.keys.toHashSet()
    }
    println(black.size)
}
