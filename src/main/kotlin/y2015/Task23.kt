package y2015

import Task

//--- Day 23: Opening the Turing Lock ---
class Task23(val input: List<String>) : Task {

	private data class Instruction(val command: String, val register: String, val offset: Int)

	override fun a(): Any {
		return executeProgram(initialRegisters = mutableMapOf("a" to 0, "b" to 0))
	}

	override fun b(): Any {
		return executeProgram(initialRegisters = mutableMapOf("a" to 1, "b" to 0))
	}

	private fun executeProgram(initialRegisters: MutableMap<String, Int>): Int {
		val instructions = parseInstructions(input)
		var ix = 0

		while (ix in instructions.indices) {
			val instruction = instructions[ix]
			ix = executeInstruction(instruction, initialRegisters, ix)
		}

		return initialRegisters["b"] ?: 0
	}

	private fun parseInstructions(input: List<String>): List<Instruction> {
		return input.map { line ->
			val parts = line.split(" ")
			when (parts[0]) {
				"inc" -> Instruction("inc", parts[1], 0)
				"hlf" -> Instruction("hlf", parts[1], 0)
				"tpl" -> Instruction("tpl", parts[1], 0)
				"jmp" -> Instruction("jmp", "", parts[1].toInt())
				"jie" -> Instruction("jie", parts[1].replace(",", ""), parts[2].toInt())
				"jio" -> Instruction("jio", parts[1].replace(",", ""), parts[2].toInt())
				else -> throw IllegalArgumentException("Unknown command: ${parts[0]}")
			}
		}
	}

	private fun executeInstruction(
		instruction: Instruction,
		registers: MutableMap<String, Int>,
		currentIndex: Int
	): Int {
		return when (instruction.command) {
			"inc" -> {
				registers[instruction.register] = registers.getOrDefault(instruction.register, 0) + 1
				currentIndex + 1
			}

			"hlf" -> {
				registers[instruction.register] = registers.getOrDefault(instruction.register, 0) / 2
				currentIndex + 1
			}

			"tpl" -> {
				registers[instruction.register] = registers.getOrDefault(instruction.register, 0) * 3
				currentIndex + 1
			}

			"jmp" -> currentIndex + instruction.offset

			"jie" -> {
				if (registers.getOrDefault(instruction.register, 0) % 2 == 0) {
					currentIndex + instruction.offset
				} else {
					currentIndex + 1
				}
			}

			"jio" -> {
				if (registers.getOrDefault(instruction.register, 0) == 1) {
					currentIndex + instruction.offset
				} else {
					currentIndex + 1
				}
			}

			else -> throw IllegalArgumentException("Unknown command: ${instruction.command}")
		}
	}
}

