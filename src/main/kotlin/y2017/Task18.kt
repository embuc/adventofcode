package y2017

import Task
import utils.isOnlyLetters

//--- Day 18: Duet ---
class Task18(val input: List<String>) : Task {
	private data class Register(val name: String, var value: Long = 0)
	private data class Instruction(val type: String, val x: String, val y: String)

	override fun a(): Any {
		val registers = mutableMapOf<String, Register>()
		val instructions = mutableListOf<Instruction>()
		var lastSound = 0
		for (line in input) {
			val parts = line.split(" ")
			val type = parts[0]
			val x = parts[1]
			val y = if (parts.size > 2) parts[2] else ""
			if (x !in registers) registers[x] = Register(x)
			if (y !in registers && y.isOnlyLetters()) registers[y] = Register(y)
			instructions.add(Instruction(type, x, y))
		}
		var i = 0
		while (i in instructions.indices) {
			val instruction = instructions[i]
			val x = instruction.x
			val y = instruction.y
			var registerX = registers[x]
			if (registerX == null) {
				registerX = Register(x)
				registers[x] = registerX
			}
			val valueY = if (y.toLongOrNull() != null) y.toLong() else registers[y]!!.value
			when (instruction.type) {
				"set" -> registerX.value = valueY
				"add" -> registerX.value += valueY
				"mul" -> registerX.value *= valueY
				"mod" -> registerX.value %= valueY
				"snd" -> lastSound = registerX.value.toInt()
				"rcv" -> if (registerX.value != 0L) return lastSound
				"jgz" -> if (registerX.value > 0) i += valueY.toInt() - 1
			}
			i++
		}

		return 0
	}

	private fun getValue(registers: Map<String, Register>, value: String): Long {
		return value.toLongOrNull() ?: registers[value]!!.value
	}

	override fun b(): Any {
		val registers0 = mutableMapOf<String, Register>()
		val registers1 = mutableMapOf<String, Register>()
		val instructions = mutableListOf<Instruction>()

		for (line in input) {
			val parts = line.split(" ")
			val type = parts[0]
			val x = parts[1]
			val y = if (parts.size > 2) parts[2] else "0"
			if (x !in registers0 && x.isOnlyLetters()) {
				registers0[x] = Register(x)
			}
			if (y !in registers0 && y.isOnlyLetters()) registers0[y] = Register(y)
			if (x !in registers1 && x.isOnlyLetters()) {
				registers1[x] = Register(x)
			}
			if (y !in registers1 && y.isOnlyLetters()) registers1[y] = Register(y)

			instructions.add(Instruction(type.trim(), x.trim(), y.trim()))
		}

		var sends = 0
		val queue0 = mutableListOf<Long>()
		val queue1 = mutableListOf<Long>()
		registers0["p"]!!.value = 0
		registers1["p"]!!.value = 1
		var i0 = 0
		var i1 = 0
		var waiting0 = false
		var waiting1 = false

		fun processInstruction(programId: Int): Boolean {
			val i = if (programId == 0) i0 else i1
			if (i !in instructions.indices) return false

			val registers = if (programId == 0) registers0 else registers1
			val instruction = instructions[i]
			val x = instruction.x
			val y = instruction.y

			val valueY = if (y.toLongOrNull() != null) y.toLong() else registers[y]!!.value

			when (instruction.type) {
				"set" -> registers[x]!!.value = valueY
				"add" -> registers[x]!!.value += valueY
				"mul" -> registers[x]!!.value *= valueY
				"mod" -> registers[x]!!.value %= valueY
				"snd" -> {
					if (programId == 0) {
						queue1.add(registers[x]!!.value)
					} else {
						queue0.add(registers[x]!!.value)
						sends++
					}
				}
				"rcv" -> {
					val queue = if (programId == 0) queue0 else queue1
					if (queue.isEmpty()) {
						if (programId == 0) waiting0 = true else waiting1 = true
						return false
					} else {
						if (programId == 0) waiting0 = false else waiting1 = false
						registers[x]!!.value = queue.removeAt(0)
					}
				}
				"jgz" -> {
					val valueX = getValue(registers, x)
					val valueY = getValue(registers, y)
					if (valueX > 0) {
						if (programId == 0) i0 += valueY.toInt() - 1
						else i1 += valueY.toInt() - 1
					}
				}
			}

			if (programId == 0) i0++ else i1++
			return true
		}

		while (true) {
			var couldProcessInstruction = false

			if (!waiting0) {
				couldProcessInstruction = processInstruction(0) || couldProcessInstruction
			}
			if (!waiting1) {
				couldProcessInstruction = processInstruction(1) || couldProcessInstruction
			}

			// Reset waiting flags if any data in queues
			if (waiting0 && queue0.isNotEmpty()) waiting0 = false
			if (waiting1 && queue1.isNotEmpty()) waiting1 = false

			// deadlock? termination?
			if (!couldProcessInstruction && queue0.isEmpty() && queue1.isEmpty() &&
				(waiting0 || i0 !in instructions.indices) &&
				(waiting1 || i1 !in instructions.indices)) {
				return sends
			}
		}
	}
}