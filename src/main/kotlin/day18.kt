internal data class Token(val op: Char, var v: Long)

internal fun Parse(input: String): List<Token> {
    return input.split(' ').chunked(2).map { Token(it[0].first(), it[1].toLong()) }
}

internal fun Evaluate(input: String, pri: Char? = null): Long {
    var tokens = Parse(input)
    if (pri != null) {
        var tokens2 = arrayListOf(Token('+', 0L))
        for (t in tokens) {
            if (t.op == pri) {
                if (t.op == '+') tokens2.last().v += t.v else tokens2.last().v *= t.v
            } else {
                tokens2.add(t)
            }
        }
        tokens = tokens2
    }
    return tokens.fold(0L) { r, t -> if (t.op == '+') r + t.v else r * t.v }
}

internal fun Process(line: String, pri: Char? = null): Long {
    var stack = arrayListOf(StringBuilder("+ "))
    for (i in line.indices) when (line[i]) {
        '(' -> stack.add(StringBuilder("+ "))
        ')' -> {
            val v = Evaluate(stack.removeLast().toString(), pri)
            stack.last().append(v)
        }
        else -> stack.last().append(line[i])
    }
    return Evaluate(stack.last().toString(), pri)
}

fun main(args: Array<String>) {
    val lines = allLines(args, "day18.in").toList()
    println(lines.map { Process(it) }.sum())
    println(lines.map { Process(it, '+') }.sum())
}