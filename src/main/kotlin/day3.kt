import com.google.common.io.Resources
import java.io.BufferedReader
import java.io.FileReader

internal data class Point(var x: Int, var y: Int)

internal fun tobogan(area: List<CharArray>, dir: Point): Long {
    var pos = Point(0, 0)
    var count = 0L
    while (pos.y < area.size) {
        if (area[pos.y][pos.x] == '#') {
            count += 1
        }
        pos.x = (pos.x + dir.x) % area[pos.y].size
        pos.y += dir.y
    }
    println("slope ${dir}: ${count}")
    return count
}

fun main(args: Array<String>) {
    val inputFileName = if (args.isEmpty()) Resources.getResource("day3.in").path else args[0]
    val area = BufferedReader(FileReader(inputFileName)).lineSequence().map { it.toCharArray() }.toList()
    val dirs = arrayOf(Point(1, 1), Point(3, 1), Point(5, 1), Point(7, 1), Point(1, 2))
    println(dirs.fold(1L) { s, it -> s * tobogan(area, it) })
}
