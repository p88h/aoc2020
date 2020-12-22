import java.util.*

internal data class Deck(val name: String, var cards: Deque<Int>) {
    fun str() = cards.joinToString(",")
    fun copy(limit: Int = cards.size) = Deck(name, ArrayDeque(cards.take(limit)))
}

internal fun load(deck: String): Deck {
    val lines = deck.split('\n')
    return Deck(lines.first(), ArrayDeque(lines.subList(1, lines.size).map { it.toInt() }))
}

internal fun game(p1: Deck, p2: Deck, recurse: Boolean = false, level: Int = 0): Boolean {
    var history = HashSet<String>()
    while (p1.cards.isNotEmpty() && p2.cards.isNotEmpty()) {
        val state = p1.str() + ":" + p2.str()
        if (history.contains(state)) return true
        history.add(state)
        // println("Level $level Round ${history.size}\n$p1\n$p2")
        var c1 = p1.cards.pop()
        var c2 = p2.cards.pop()
        var win = true
        if (recurse && p1.cards.size >= c1 && p2.cards.size >= c2) {
            win = game(p1.copy(c1), p2.copy(c2), recurse, level + 1)
        } else win = c1 > c2
        if (win) {
            // println("Player 1 wins")
            p1.cards.addLast(c1)
            p1.cards.addLast(c2)
        } else {
            // println("Player 2 wins")
            p2.cards.addLast(c2)
            p2.cards.addLast(c1)
        }
    }
    if (level == 0) {
        val cards = if (p1.cards.isEmpty()) p2.cards else p1.cards
        println(cards.toList().mapIndexed { i, v -> (cards.size - i) * v }.sum())
    }
    return p1.cards.isNotEmpty()
}

fun main(args: Array<String>) {
    val decks = allBlocks(args, "day22.in").map { load(it) }
    game(decks[0].copy(), decks[1].copy())
    game(decks[0].copy(), decks[1].copy(), true)
}
