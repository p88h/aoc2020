import com.google.common.io.Resources
import java.io.BufferedReader
import java.io.FileReader

fun main(args: Array<String>) {
    val inputFileName = if (args.isEmpty()) Resources.getResource("day5.in").path else args[0]
    val seats = BufferedReader(FileReader(inputFileName)).lineSequence().map {
        it.replace(Regex("[BR]"), "1")
            .replace(Regex("[FL]"),"0")
            .toInt(2)
    }.sorted().toList();
    println("min: ${seats.first()} max: ${seats.last()}")
    seats.forEachIndexed{ pos, num -> if (num != pos + seats.first()) { println(num - 1); return } }
}