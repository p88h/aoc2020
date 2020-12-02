import com.google.common.io.Resources
import java.io.BufferedReader
import java.io.FileReader

internal data class Password(val min: Int, val max: Int, val letter: Char, val text: String) {
    fun pred1(): Boolean {
        return text.count { it.equals(letter) } in min..max
    }
    fun pred2(): Boolean {
        return (text.length >= min && text[min - 1] == letter) xor (text.length >= max && text[max - 1] == letter)
    }
}

fun main(args: Array<String>) {
    val inputFileName = if (args.isEmpty()) Resources.getResource("day2.in").path else args[0]
    val regex = "([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)".toRegex()
    val inputs = BufferedReader(FileReader(inputFileName)).lineSequence().map {
        regex.matchEntire(it)?.destructured?.let { (mins, maxs, letters, text) ->
            Password(mins.toInt(), maxs.toInt(), letters[0], text)
        }
    }.toList()
    println(inputs.count { it!!.pred1() })
    println(inputs.count { it!!.pred2() })
}