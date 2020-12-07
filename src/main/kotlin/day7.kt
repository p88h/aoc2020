import com.google.common.io.Resources
import java.io.File

internal data class Bag(val name: String) {
    var total = 0L
    var contents = ArrayList<Pair<String, Int>>()

    fun compute(graph: HashMap<String, Bag>): Long {
        if (total == 0L) contents.forEach {
            val inner = graph[it.first]
            total += it.second.toLong() * inner!!.compute(graph)
        }
        return total + 1
    }
}

fun main(args: Array<String>) {
    val inputFileName = if (args.isEmpty()) Resources.getResource("day7.in").path else args[0]
    var graph = HashMap<String, Bag>()
    var inverted = HashMap<String, ArrayList<String>>()
    File(inputFileName).bufferedReader().readLines().forEach {
        val tokens = it.split(" ")
        val outer = tokens[0] + " " + tokens[1];
        graph[outer] = Bag(outer);
        for (idx in 1 until tokens.size / 4) {
            val inner = tokens[4 * idx + 1] + " " + tokens[4 * idx + 2]
            graph[outer]!!.contents.add(inner to tokens[4 * idx].toInt())
            inverted.getOrPut(inner, { ArrayList() }).add(outer)
        }
    }
    var visited = hashSetOf("shiny gold")
    var queue = ArrayDeque<String>()
    queue.add("shiny gold")
    while (queue.isNotEmpty()) {
        val inner = queue.removeFirst()
        inverted[inner]?.forEach {
            if (!visited.contains(it)) {
                queue.add(it)
                visited.add(it)
            }
        }
    }
    println(visited.size - 1)
    println(graph["shiny gold"]!!.compute(graph) - 1)
}