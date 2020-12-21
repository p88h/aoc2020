import java.util.TreeMap

internal data class Food(val ingredients: HashSet<String>, val allergens: HashSet<String>) {
    fun matches(a: String, i: String) = !allergens.contains(a) || ingredients.contains(i)
}

fun main(args: Array<String>) {
    val fmt = "(.*) \\(contains (.*)\\)".toRegex()
    val foods = allLines(args, "day21.in").toList().map {
        fmt.matchEntire(it)!!.destructured.let { (ing, all) ->
            Food(HashSet(ing.split(' ')), HashSet(all.split((", "))))
        }
    }
    val ing = HashSet<String>(foods.flatMap { it.ingredients })
    val all = HashSet<String>(foods.flatMap { it.allergens })
    val imap = ing.map { it to all.filter { a -> foods.all { f -> f.matches(a, it) } } }.toMap()
    println(imap.filter { it.value.isEmpty() }.map { foods.count{ f -> f.ingredients.contains(it.key)} }.sum() )
    var cand = imap.filter { it.value.isNotEmpty() }.toList()
    var used = TreeMap<String, String>()
    while (used.size < cand.size) {
        val i = cand.first { c -> c.second.count { !used.contains(it) } == 1 }
        used[i.second.first { !used.contains(it) }] = i.first
    }
    println(used.values.joinToString(","))
}