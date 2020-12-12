fun part1(input: List<Int>) {
    val prev = HashSet<Int>();
    for (num in input) {
        if (prev.contains(2020 - num)) {
            println(num * (2020 - num));
        }
        prev.add(num);
    }
}

fun part2(input: List<Int>) {
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
    val input = allLines(args, "day1.in").map{it.toInt()}.toList()
    part1(input)
    part2(input)
}