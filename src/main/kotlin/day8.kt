import com.google.common.io.Resources
import java.io.File

enum class Opcode {
    nop, acc, jmp
}

data class Instruction(val op: Opcode, val mod: Int)

data class Machine(val code: List<Instruction>) {
    var acc = 0L
    var pos = 0
    var used = BooleanArray(code.size) { false }
    var magic = -1

    fun loop() : Long {
        //println("magic:$magic")
        while (pos < code.size && !used[pos]) {
            used[pos] = true;
            var ins = code[pos]
            if (pos == magic) when (ins.op) {
                Opcode.jmp -> { ins = Instruction(Opcode.nop, ins.mod) }
                Opcode.nop -> { ins = Instruction(Opcode.jmp, ins.mod) }
            }
            when (ins.op) {
                Opcode.nop -> { pos += 1 }
                Opcode.acc -> { acc += ins.mod; pos += 1 }
                Opcode.jmp -> { pos += ins.mod }
            }
            //println("$pos: $ins => $acc")
        }
        //println("END")
        return acc
    }

    fun magicloop() : Long {
        for (idx in code.indices) {
            if (code[idx].op == Opcode.acc) {
                continue
            }
            magic = idx
            pos = 0
            acc = 0
            used = BooleanArray(code.size) { false }
            loop()
            if (pos == code.size) {
                return acc
            }
        }
        return -1
    }

}

fun main(args: Array<String>) {
    val inputFileName = if (args.isEmpty()) Resources.getResource("day8.in").path else args[0]
    val machine = Machine(File(inputFileName).bufferedReader().readLines().map {
        val fields = it.split(' ')
        Instruction(Opcode.valueOf(fields[0]), fields[1].toInt())
    }.toList())
    println(machine.loop())
    println(machine.magicloop())
}