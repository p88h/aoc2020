fun main() {
    val blocks = System.`in`.reader().readText().split("\n\n");
    println(blocks.map { it.replace("\n", "").toSet().size }.sum())
    println(blocks.map { it.split("\n").map { s->s.toSet() }.reduce{ a,b-> a.intersect(b) }.size }.sum())
}
