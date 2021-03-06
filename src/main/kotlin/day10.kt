fun main(args: Array<String>) {
    var nums = arrayListOf(0L).also { it.addAll(allLines(args, "day10.in").map { s -> s.toLong() }) }.also { it.sort() }
    nums.add(nums.last() + 3)
    val deltas = nums.zipWithNext { a, b -> b - a }
    println(deltas.count { it.equals(1L) } * deltas.count { it.equals(3L) });
    var f = LongArray(nums.size) { 0L }
    f[nums.size - 1] = 1
    for (i in nums.size - 2 downTo 0) {
        f[i] = f[i + 1]
        if (i + 3 < nums.size && nums[i + 3] <= nums[i] + 3) {
            f[i] += f[i + 3]
        }
        if (i + 2 < nums.size && nums[i + 2] <= nums[i] + 3) {
            f[i] += f[i + 2]
        }
    }
    println(f[0])
}