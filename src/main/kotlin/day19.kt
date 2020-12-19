fun expand(rules: Map<Int, String>): Regex {
    var e = rules[0]!!
    val num = "\\s?(\\d+)\\s?".toRegex()
    while (e.contains(' ')) e = e.replace(num) { res -> "(" + rules[res.groupValues[1].toInt()]!! + ")" }
    e = e.replace('A', '2').replace('B', '3').replace('C', '4')
    return e.toRegex()
}
fun main(args: Array<String>) {
    val fmt = "(\\d+): \"?(.*?)\"?".toRegex()
    val input = allLines(args, "day19.in").toList()
    var rules = input.takeWhile { it.contains(':') }
        .map { fmt.matchEntire(it)!!.destructured.let { (id, pattern) -> id.toInt() to pattern } }.toMap()
        .toMutableMap()
    val rule1 = expand(rules)
    rules[8] = "42+";
    rules[11] = "42 31 | 42{A} 31{A}| 42{B} 31{B}| 42{C} 31{C}";
    val rule2 = expand(rules)
    println(input.count { rule1.matches(it) })
    println(input.count { rule2.matches(it) })
}