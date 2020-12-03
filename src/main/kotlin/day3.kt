import com.google.common.io.Resources
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.*

fun main(args: Array<String>) {
    val inputFileName = if (args.isEmpty()) Resources.getResource("day3.in").path else args[0]
    val scanner = Scanner(File(inputFileName));
    var count = 0;
    var pos = 0;
    while (scanner.hasNextLine()) {
        var line = scanner.nextLine().toCharArray();
        if (line[pos] == '.') {
            line[pos] = 'O';
        } else {
            line[pos] = 'X';
            count += 1;
        }
        println(String(line));
        pos = (pos + 3) % line.size;
    }
    println(count);
}