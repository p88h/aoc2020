import com.google.common.io.Resources
import java.io.File

fun main(args: Array<String>) {
    val inputFileName = if (args.isEmpty()) Resources.getResource("day11.in").path else args[0]
    var area = File(inputFileName).bufferedReader().lineSequence().map { it.toCharArray() }.toList()
    while (true) {
        var mod = 0
        var cnt = 0
        for (i in area.indices) {
            for (j in area[i].indices) {
                var occ = 0;
                for (ii in -1..1) {
                    if (i + ii >= 0 && i + ii < area.size) for (jj in -1..1) {
                        if (j + jj >= 0 && j + jj < area[i].size) {
                            if (ii == 0 && jj == 0) continue
                            if (area[i+ii][j+jj] == '#' || area[i+ii][j+jj] == '@') occ += 1;
                        }
                    }
                }
                if (area[i][j]=='L' && occ == 0) {
                    area[i][j] = '*'
                }
                if (area[i][j]=='#' && occ >= 4) {
                    area[i][j] = '@'
                }
            }
        }
        for (i in area.indices) {
            for (j in area[i].indices) {
                if (area[i][j]=='*') {
                    area[i][j] = '#'
                    mod += 1
                }
                if (area[i][j]=='@') {
                    area[i][j] = 'L'
                    mod += 1
                }
                if (area[i][j] == '#') {
                    cnt += 1
                }
            }
            //println(area[i])
        }
        println(cnt)
        if (mod == 0) {
            break
        }
    }

    for (i in area.indices) {
        for (j in area[i].indices) {
            if (area[i][j] != '.') {
                area[i][j] = 'L'
            }
        }
    }

    while (true) {
        var mod = 0
        var cnt = 0
        for (i in area.indices) {
            for (j in area[i].indices) {
                var occ = 0;
                for (ii in -1..1) {
                    for (jj in -1..1) {
                        if (ii == 0 && jj == 0) continue
                        var k=1
                        while (i + k*ii >= 0 && i + k*ii < area.size && j + k*jj >= 0 && j + k*jj < area[i].size) {
                            if (area[i+k*ii][j+k*jj] == '#' || area[i+k*ii][j+k*jj] == '@') {
                                occ += 1
                                break
                            }
                            if (area[i+k*ii][j+k*jj] == 'L' || area[i+k*ii][j+k*jj] == '*') {
                                break
                            }
                            k+=1
                        }
                    }
                }
                if (area[i][j]=='L' && occ == 0) {
                    area[i][j] = '*'
                }
                if (area[i][j]=='#' && occ >= 5) {
                    area[i][j] = '@'
                }
            }
        }
        for (i in area.indices) {
            for (j in area[i].indices) {
                if (area[i][j]=='*') {
                    area[i][j] = '#'
                    mod += 1
                }
                if (area[i][j]=='@') {
                    area[i][j] = 'L'
                    mod += 1
                }
                if (area[i][j] == '#') {
                    cnt += 1
                }
            }
            //println(area[i])
        }
        println(cnt)
        //Thread.sleep(1000)
        if (mod == 0) {
            break
        }
    }

}
