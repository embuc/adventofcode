package y2016

import Task
import y2016.Task12.Instruction

//--- Day 23: Safe Cracking ---
class Task23(val input:List<String>) :Task{

	override fun a(): Any {
		val instructions = Task12(input).toInstructions(input)
		return processInstructions(	instructions, intArrayOf(7, 0, 0, 0))
	}

	private fun processInstructions(
		instructions: MutableList<Instruction>,
		registers: IntArray
	): Int {
		var ix = 0
		while (ix in instructions.indices) {
			val instruction = instructions[ix]
			when (instruction.command) {
				"mlt" -> {
					registers[instruction.arg2asIx] = registers[instruction.arg1asIx] * registers[instruction.arg2asIx]
				}
				"cpy" -> {
					if (instruction.isArg1Int && instruction.isArg2Int) {
						continue
					}
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
				"tgl" -> {
					val value = if (instruction.isArg1Int) instruction.arg1asInt else registers[instruction.arg1asIx]
					val targetIx = ix + value
					if (targetIx in instructions.indices) {
						val target = instructions[targetIx]
						when (target.command) {
							"inc" -> target.command = "dec"
							"dec" -> target.command = "inc"
							"tgl" -> target.command = "inc"
							"jnz" -> target.command = "cpy"
							"cpy" -> target.command = "jnz"
						}
					}
				}
			}
			ix++
		}
		return registers[0]
	}

	override fun b(): Any {
		val instructions = Task12(input).toInstructions(input)
		return processInstructions(	instructions, intArrayOf(12, 0, 0, 0))
	}
}