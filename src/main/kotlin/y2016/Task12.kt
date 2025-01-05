package y2016

import Task

//--- Day 12: Leonardo's Monorail ---
class Task12(val input: List<String>) : Task {

	private data class Instruction(val command: String, val arg1: String, val arg2: String)

	override fun a(): Any {
		val registers = mutableMapOf("a" to 0, "b" to 0, "c" to 0, "d" to 0)
		var ix = 0
		val instructions = mutableListOf<Instruction>()
		for (i in input) {
			println("line: $i")
			val parts = i.split(" ")
			instructions.add(Instruction(parts[0], parts[1], parts.getOrNull(2) ?: ""))
		}
		while (ix in instructions.indices) {
			val instruction = instructions[ix]
			when (instruction.command) {
				"cpy" -> {
					val value = if (instruction.arg1.toIntOrNull() == null) registers[instruction.arg1]!! else instruction.arg1.toInt()
					registers[instruction.arg2] = value
				}
				"inc" -> registers[instruction.arg1] = registers[instruction.arg1]!! + 1
				"dec" -> registers[instruction.arg1] = registers[instruction.arg1]!! - 1
				"jnz" -> {
					val value = if (instruction.arg1.toIntOrNull() == null) registers[instruction.arg1]!! else instruction.arg1.toInt()
					if (value != 0) {
						ix += instruction.arg2.toInt() - 1
					}
				}
			}
			ix++
		}
		return registers["a"]!!
	}

	override fun b(): String {
		return ""
	}
}