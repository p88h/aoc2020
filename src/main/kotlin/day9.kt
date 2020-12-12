fun part1(nums: List<Long>): Long {
    var sums = HashMap<Long, Long>()
    for (a in nums.indices) {
        val b = a - 25
        if (b >= 0 && nums[a]!! !in sums) {
            println(nums[a])
            return nums[a]
        }
        for (c in b + 1 until a) {
            if (b >= 0) {
                // remove (b+c)
                val s1 = nums[b] + nums[c]
                sums.merge(s1, -1, Long::plus)
                // cleanup
                if (sums.getValue(s1) == 0L) {
                    sums.remove(s1)
                }
            }
            if (c >= 0) {
                // add (c+a)
                val s2 = nums[c] + nums[a]
                sums.merge(s2, 1, Long::plus)
            }
        }
    }
    return 0L
}

fun part2(nums: List<Long>, q: Long): Long {
    var tot = 0L
    var i = 0
    for (j in nums.indices) {
        tot += nums[j]
        while (i <= j && tot > q) {
            tot -= nums[i]
            i += 1
        }
        if (tot == q) {
            val found = nums.subList(i, j)
            return found.min()!! + found.max()!!
        }
    }
    return 0L
}

fun main(args: Array<String>) {
    val nums = allLines(args, "day9.in").map { it.toLong() }.toList()
    println(part2(nums, part1(nums)))
}