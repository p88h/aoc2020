import java.io.File
import java.util.Scanner
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.collections.set
import com.google.common.io.Resources;

fun part1(input: ArrayList<Int>) {
    val prev = HashSet<Int>();
    for (num in input) {
        if (prev.contains(2020 - num)) {
            println(num * (2020 - num));
        }
        prev.add(num);
    }
}

fun part2(input: ArrayList<Int>) {
    val prev = HashSet<Int>();
    val sums = HashMap<Int, Int>();
    for (num in input) {
        val rem = 2020 - num;
        if (sums.containsKey(rem)) {
            val pa: Int = sums[rem] ?: throw NullPointerException();
            val pb: Int = rem - pa;
            println(pa * pb * num);
        }
        for (other in prev) {
            sums[num + other] = num;
        }
        prev.add(num);
    }
}

fun main(args: Array<String>) {
    var inputFileName = Resources.getResource("day1.in").path;
    if (!args.isEmpty()) {
        inputFileName = args[0];
    }
    val reader = Scanner(File(inputFileName));
    val input = ArrayList<Int>();
    while (reader.hasNextInt()) {
        input.add(reader.nextInt());
    }
    part1(input);
    part2(input);
}