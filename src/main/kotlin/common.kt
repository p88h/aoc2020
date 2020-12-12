import com.google.common.io.Resources
import java.io.File

internal data class Point(var x: Int, var y: Int)

fun allLines(args: Array<String>, defaultFile: String): Sequence<String> {
    val inputFileName = if (args.isEmpty()) Resources.getResource(defaultFile).path else args[0]
    return File(inputFileName).bufferedReader().lineSequence()
}
