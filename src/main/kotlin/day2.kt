import com.google.common.io.Resources
import java.io.File
import java.util.Scanner

val regex = "([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)".toRegex();

fun main(args: Array<String>) {
    var inputFileName = Resources.getResource("day2.in").path;
    if (!args.isEmpty()) {
        inputFileName = args[0];
    }
    val reader = Scanner(File(inputFileName));
    var total1 = 0;
    var total2 = 0;
    while (reader.hasNextLine()) {
        regex.matchEntire(reader.nextLine())
            ?.destructured
            ?.let { (mins, maxs, letters, text) ->
                val min = mins.toInt();
                val max = maxs.toInt();
                val letter = letters[0];
                val cnt:Int = text.count{it.equals(letter)};
                if (cnt in min..max) {
                    total1 += 1;
                }
                val first = text.length >= min && text[min-1] == letter;
                val second = text.length >= max && text[max-1] == letter;
                if (first xor second) {
                    total2 += 1;
                }
            }
    }
    println(total1);
    println(total2);
}