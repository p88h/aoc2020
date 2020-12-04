import com.google.common.io.Resources
import java.io.BufferedReader
import java.io.FileReader

internal class Passport {
    companion object {
        val checks = mapOf(
            "byr" to "(19[2-9][0-9]|200[0-2])".toRegex(),
            "iyr" to "20(1[0-9]|20)".toRegex(),
            "eyr" to "20(2[0-9]|30)".toRegex(),
            "hgt" to "(1[5-8][0-9]cm|19[0-3]cm|59in|6[0-9]in|7[0-6]in)".toRegex(),
            "hcl" to "#[0-9a-f]{6}".toRegex(),
            "ecl" to "(amb|blu|brn|gry|grn|hzl|oth)".toRegex(),
            "pid" to "([0-9]{9})".toRegex()
        )
    }

    var fields = HashMap<String, String>()
    fun add(key: String, value: String) {
        fields[key] = value;
    }

    fun valid1(): Int {
        for (key in checks.keys) {
            if (!fields.containsKey(key)) {
                return 0;
            }
        }
        return 1;
    }

    fun valid2(): Int {
        for (key in checks.keys) {
            if (!fields.containsKey(key) || !checks[key]!!.matches(fields[key]!!)) {
                return 0;
            }
        }
        return 1;
    }
}

fun main(args: Array<String>) {
    val inputFileName = if (args.isEmpty()) Resources.getResource("day4.in").path else args[0]
    val reader = BufferedReader(FileReader(inputFileName))
    var cur = Passport()
    var tot1 = 0
    var tot2 = 0
    for (line in reader.lineSequence()) {
        if (line.isEmpty()) {
            tot1 += cur.valid1()
            tot2 += cur.valid2()
            cur = Passport()
            continue
        }
        for (keyval in line.split(" ")) {
            val (key, value) = keyval.split(":")
            cur.add(key, value)
        }
    }
    tot1 += cur.valid1()
    tot2 += cur.valid2()
    println(tot1)
    println(tot2)
}

