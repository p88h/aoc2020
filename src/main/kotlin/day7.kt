import com.google.common.io.Resources
import java.io.File

internal data class Graph(val start: String) {
    var contents = HashMap<String, ArrayList<Pair<String, Int>>>()
    var inverted = HashMap<String, ArrayList<String>>()
    var totals = HashMap<String, Long>()

    // BFS on inverted graph, return count of visited nodes
    fun search(from: String = start): Int {
        var visited = hashSetOf(from)
        var queue = ArrayDeque<String>()
        queue.add(from)
        while (queue.isNotEmpty()) {
            val inner = queue.removeFirst()
            inverted[inner]?.forEach {
                if (!visited.contains(it)) {
                    queue.add(it)
                    visited.add(it)
                }
            }
        }
        return visited.size
    }

    // DFS on contents graph, return number of bags in subtree
    fun compute(from: String = start): Long {
        if (!totals.containsKey(from)) {
            var total = 0L
            contents[from]!!.forEach {
                total += it.second.toLong() * compute(it.first)
            }
            totals[from] = total
        }
        return totals[from]!! + 1
    }
}

fun main(args: Array<String>) {
    val inputFileName = if (args.isEmpty()) Resources.getResource("day7.in").path else args[0]
    var graph = Graph("shiny gold")
    File(inputFileName).bufferedReader().readLines().forEach {
        val tokens = it.split(" ")
        val outer = tokens[0] + " " + tokens[1];
        graph.contents[outer] = ArrayList()
        for (idx in 1 until tokens.size / 4) {
            val inner = tokens[4 * idx + 1] + " " + tokens[4 * idx + 2]
            graph.contents[outer]!!.add(inner to tokens[4 * idx].toInt())
            graph.inverted.getOrPut(inner, { ArrayList() }).add(outer)
        }
    }
    println(graph.search() - 1)
    println(graph.compute() - 1)
}