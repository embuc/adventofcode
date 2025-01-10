package y2016

import Task

//--- Day 12: Leonardo's Monorail ---
class Task12(val input: List<String>) : Task {

	data class Instruction(
		var command: String, val isArg1Int:Boolean, val arg1letter:Char, val arg1asInt: Int, val arg1asIx:Int,
		val isArg2Int:Boolean, val arg2letter:Char, val arg2asInt: Int, val arg2asIx:Int

	){
		override fun toString() : String {
			return "$command ${if(isArg1Int) arg1asInt else arg1letter} ${if(isArg2Int) arg2asInt else arg2letter}"
		}
	}

	override fun a(): Any {
		//"a" to 0, "b" to 0, "c" to 1, "d" to 0)
		val registers = IntArray(4)
		val instructions = toInstructions(input)
		return processInstructions(instructions, registers)
	}

	private fun processInstructions(
		instructions: MutableList<Instruction>,
		registers: IntArray
	): Int {
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
						ix += if (instruction.isArg2Int) instruction.arg2asInt else registers[instruction.arg2asIx] - 1
//						ix += instruction.arg2asInt - 1
					}
				}
			}
			ix++
		}
		return registers[0]
	}

	fun toInstructions(input: List<String>): MutableList<Instruction> {
		val instructions = mutableListOf<Instruction>()
		for (i in input) {
			val parts = i.split(" ")
			val command = parts[0]
			val isArg1Int = parts[1].toIntOrNull() != null
			val arg1asInt = parts[1].toIntOrNull() ?: 0
			var arg1asIx = -1
			var arg1letter = ' '
			var arg2letter = ' '
			if(!isArg1Int) {
				arg1asIx = when (parts[1]) {
					"a" -> 0
					"b" -> 1
					"c" -> 2
					"d" -> 3
					else -> -1
				}
				arg1letter = parts[1][0]
			}
			val isArg2Int = parts.getOrNull(2)?.toIntOrNull() != null
			val arg2asInt = parts.getOrNull(2)?.toIntOrNull() ?: 0
			var arg2asIx = -1
			if(!isArg2Int && parts.getOrNull(2) != null) {
				arg2asIx = when (parts[2]) {
					"a" -> 0
					"b" -> 1
					"c" -> 2
					"d" -> 3
					else -> -1
				}
				arg2letter = parts[2][0]
			}
			instructions.add(Instruction(command, isArg1Int, arg1letter, arg1asInt, arg1asIx, isArg2Int, arg2letter, arg2asInt, arg2asIx))
		}
		return instructions
	}

	override fun b(): Int {
		//"a" to 0, "b" to 0, "c" to 1, "d" to 0)
		val registers = IntArray(4)
		registers[2] = 1 //c = 1
		val instructions = toInstructions(input)
		return processInstructions(instructions, registers)
	}
}