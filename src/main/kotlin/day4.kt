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

    fun valid1(): Boolean {
        return checks.keys.fold(true) { s, it -> s && fields.containsKey(it) }
    }

    fun valid2(): Boolean {
        return checks.entries.fold(valid1()) { s, (key, regex) -> s && regex.matches(fields[key]!!) }
    }
}

fun main(args: Array<String>) {
    var passports = arrayListOf(Passport())
    for (line in allLines(args, "day4.in")) {
        if (line.isEmpty()) {
            passports.add(Passport())
        } else {
            for (keyval in line.split(" ")) {
                val (key, value) = keyval.split(":")
                passports.last().fields.put(key, value)
            }
        }
    }
    println(passports.count { it.valid1() })
    println(passports.count { it.valid2() })
}
