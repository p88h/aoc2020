import java.lang.NullPointerException
import java.util.Scanner
import kotlin.collections.HashMap
import kotlin.collections.HashSet

fun part1() {
    val reader = Scanner(System.`in`)
    val prev = HashSet<Int>();
    while (reader.hasNextInt()) {
        val num: Int = reader.nextInt();
        if (prev.contains(2020 - num)) {
            println(num * (2020 - num));
        }
        prev.add(num);
    }

}

fun main(args: Array<String>) {
    val reader = Scanner(System.`in`)
    val prev = HashSet<Int>();
    val sums = HashMap<Int, Int>();
    while (reader.hasNextInt()) {
        val num: Int = reader.nextInt();
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