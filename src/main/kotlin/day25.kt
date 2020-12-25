fun main(args: Array<String>) {
    val dim = 20201227L
    var rev = LongArray(dim.toInt()) { 0 }
    var num = 1L
    for (cnt in 1..dim) {
        num = (num * 7L) % dim
        rev[num.toInt()] = cnt
    }
    val a = readLine()!!.toLong()
    val b = readLine()!!.toLong()
    val c = rev[a.toInt()]
    println("7 ^ ${rev[a.toInt()]} % $dim = $a")
    println("7 ^ ${rev[b.toInt()]} % $dim = $b")
    num = 1L
    for (cnt in 1..c) num = (num * b) % dim
    println("$b ^ $c % $dim = $num")
}