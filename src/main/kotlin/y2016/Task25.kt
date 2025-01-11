package y2016

import Task
import y2016.Task12.Instruction

//--- Day 25: Clock Signal ---
class Task25(val input: List<String>) : Task {


	override fun a(): Any {
		//"a" to 0, "b" to 0, "c" to 1, "d" to 0)
		for (i in 0..100_000) {
			val registers = intArrayOf(i, 0, 0, 0)
			val instructions = Task12(listOf()).toInstructions(input)
			if (processInstructions(instructions, registers)) {
//				println("found i: $i registers: ${registers.joinToString(",")}")
				return i
			}
		}
		return -1
	}

	private fun processInstructions(
		instructions: MutableList<Instruction>,
		registers: IntArray
	): Boolean {
		var output = ""
		var ix = 0
		while (ix in instructions.indices) {
			val instruction = instructions[ix]
			when (instruction.command) {
				"cpy" -> {
					val value = if (instruction.isArg1Int) instruction.arg1asInt else registers[instruction.arg1asIx]
					registers[instruction.arg2asIx] = value
				}

				"inc" -> registers[instruction.arg1asIx] = registers[instruction.arg1asIx] + 1
				"dec" -> registers[instruction.arg1asIx] = registers[instruction.arg1asIx] - 1
				"jnz" -> {
					val value = if (instruction.isArg1Int) instruction.arg1asInt else registers[instruction.arg1asIx]
					if (value != 0) {
						ix += (if (instruction.isArg2Int) instruction.arg2asInt else registers[instruction.arg2asIx]) - 1
					}
				}
				"out" -> {
					val value = if (instruction.isArg1Int) instruction.arg1asInt else registers[instruction.arg1asIx]
					output += value
					if (output.length == 10) {
						return output == "0101010101"
					}
				}
			}
			ix++
		}
		return true
	}

	override fun b(): Any {
		return 0
	}
}