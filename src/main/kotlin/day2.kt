import com.google.common.io.Resources
import java.io.BufferedReader
import java.io.FileReader

internal val regex = "([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)".toRegex()

internal data class Password(val min: Int, val max: Int, val letter: Char, val text: String) {
    fun pred1(): Boolean {
        val cnt: Int = text.count { it.equals(letter) }
        return cnt in min..max
    }
    fun pred2(): Boolean {
        val first = text.length >= min && text[min - 1] == letter
        val second = text.length >= max && text[max - 1] == letter
        return first xor second
    }
}

fun main(args: Array<String>) {
    var inputFileName = if (args.isEmpty()) Resources.getResource("day2.in").path else args[0]
    val inputs = BufferedReader(FileReader(inputFileName)).lineSequence().map {
        regex.matchEntire(it)?.destructured?.let { (mins, maxs, letters, text) ->
            Password(mins.toInt(), maxs.toInt(), letters[0], text)
        }
    }.toList()
    println(inputs.count { it!!.pred1() })
    println(inputs.count { it!!.pred2() })
}
