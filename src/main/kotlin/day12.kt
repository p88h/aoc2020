internal fun rotate(vec: Point, deg: Int): Point {
    when (deg) {
        90 -> return Point(-vec.y , vec.x)
        180 -> return Point(-vec.x, -vec.y)
        270 -> return Point(vec.y, -vec.x)
    }
    throw IllegalArgumentException()
}

internal fun move(vec: Point, dir: Point, num: Int): Point {
    return Point(vec.x + dir.x * num, vec.y + dir.y * num)
}

internal data class Ship(var pos: Point, var dir: Point)
internal data class Instr(val op: Char, val num: Int);

fun main(args: Array<String>) {
    val insns = allLines(args, "day12.in").map { Instr(it.get(0), it.substring(1).toInt()) }.toList()
    var ship = Ship(Point(0, 0), Point(1, 0))
    val dirs = mapOf('N' to Point(0, -1), 'S' to Point(0, 1), 'E' to Point(1, -0), 'W' to Point(-1, 0))
    insns.forEach { when (it.op) {
        'F' -> ship.pos = move(ship.pos, ship.dir, it.num)
        'L', 'R' -> ship.dir = rotate(ship.dir, if (it.op == 'R') it.num else 360 - it.num)
        'N', 'S', 'E', 'W' -> ship.pos = move(ship.pos, dirs[it.op]!!, it.num)
    } }
    println(Math.abs(ship.pos.x) + Math.abs(ship.pos.y))
    // part 2
    ship = Ship(Point(0, 0), Point(10, -1))
    insns.forEach { when (it.op) {
        'F' -> ship.pos = move(ship.pos, ship.dir, it.num)
        'L', 'R' -> ship.dir = rotate(ship.dir, if (it.op == 'R') it.num else 360 - it.num)
        'N', 'S', 'E', 'W' -> ship.dir = move(ship.dir, dirs[it.op]!!, it.num)
    } }
    println(Math.abs(ship.pos.x) + Math.abs(ship.pos.y))
}