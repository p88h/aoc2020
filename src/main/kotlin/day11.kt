import com.google.common.io.Resources
import java.io.File

internal data class Seat(val pos: Pair<Int, Int>) {
    var adj = ArrayList<Pair<Int, Int>>()
    var nc = 0

    fun InitAdj(area: List<CharArray>, maxd: Int) {
        adj.clear()
        for (ii in -1..1) for (jj in -1..1) {
            if (ii == 0 && jj == 0) continue
            for (k in 1..maxd) {
                val i = pos.first + k * ii
                val j = pos.second + k * jj
                if (!(i >= 0 && i < area.size && j >= 0 && j < area[i].size)) break
                if (area[i][j] != '.') {
                    adj.add(i to j)
                    break
                }
            }
        }
    }

    fun ComputeNC(area: List<CharArray>) {
        nc = adj.count { area[it.first][it.second] == '#' }
    }

    fun Update(area: List<CharArray>, maxc: Int): Boolean {
        val p = area[pos.first][pos.second]
        if (nc == 0) area[pos.first][pos.second] = '#'
        if (nc >= maxc) area[pos.first][pos.second] = 'L'
        return area[pos.first][pos.second] != p
    }
}

fun main(args: Array<String>) {
    val inputFileName = if (args.isEmpty()) Resources.getResource("day11.in").path else args[0]
    var area = File(inputFileName).bufferedReader().lineSequence().map { it.toCharArray() }.toList()
    var seats = ArrayList<Seat>()
    for (i in area.indices) for (j in area[i].indices) if (area[i][j] != '.') seats.add(Seat(i to j))
    seats.forEach { it.InitAdj(area, 1) }
    do {
        seats.forEach { it.ComputeNC(area) }
    } while (seats.count { it.Update(area, 4) } > 0)
    println(seats.count { area[it.pos.first][it.pos.second] == '#' });
    seats.forEach { it.Update(area, 0) }
    seats.forEach { it.InitAdj(area, 999999) }
    do {
        seats.forEach { it.ComputeNC(area) }
    } while (seats.count { it.Update(area, 5) } > 0)
    println(seats.count { area[it.pos.first][it.pos.second] == '#' });
}