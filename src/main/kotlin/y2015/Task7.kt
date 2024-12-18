package y2015

import Task

/*--- Day 7: Some Assembly Required --- */
class Task7(val input: List<String>) : Task {

	override fun a(): Any {
		return processInstructions(input, "a")
	}

	interface Instruction {
		val wire: String
		fun evaluate(wires: Map<String, Int>): Int
	}

	data class ConstantInstruction(val value: Int, override val wire: String) : Instruction {
		override fun evaluate(wires: Map<String, Int>): Int {
			return value
		}
	}

	data class DirectAssignInstruction(val value: String, override val wire: String) : Instruction {
		override fun evaluate(wires: Map<String, Int>): Int {
			return wires[value] ?: 0
		}
	}

	data class OneOpInstruction(val op: String, val arg1: String, override val wire: String) : Instruction {
		override fun evaluate(wires: Map<String, Int>): Int {
			val arg1Value = wires[arg1] ?: arg1.toIntOrNull() ?: 0
			return when (op) {
				"NOT" -> 65535 - arg1Value
				else -> 0
			}
		}
	}

	data class TwoOpInstruction(val arg1: String, val op: String, val arg2: String, override val wire: String) :
		Instruction {
		override fun evaluate(wires: Map<String, Int>): Int {
			val arg1Value = wires[arg1] ?: arg1.toIntOrNull() ?: 0
			val arg2Value = wires[arg2] ?: arg2.toIntOrNull() ?: 0
			return when (op) {
				"AND" -> arg1Value.and(arg2Value)
				"OR" -> arg1Value.or(arg2Value)
				"LSHIFT" -> arg1Value.shl(arg2Value)
				"RSHIFT" -> arg1Value.shr(arg2Value)
				else -> 0
			}
		}
	}

	fun processInstructions(input: List<String>, queryString: String): Int {
		val wires = mutableMapOf<String, Int>()
		val dependencies = mutableMapOf<String, Set<String>>()
		var unresolved = mutableListOf<Instruction>()

		for (line in input) {
			val (instruction, wire) = line.split(" -> ").map { it.trim() }
			val parts = instruction.split(" ")
			if (parts.size == 1) {
				if (parts[0].toIntOrNull() != null) {
					unresolved.add(ConstantInstruction(parts[0].toInt(), wire))
				} else {
					unresolved.add(DirectAssignInstruction(parts[0], wire))
					dependencies[wire] = setOf(parts[0])
				}
			} else if (parts.size == 2) {
				dependencies[wire] = setOf(parts[1])
				unresolved.add(OneOpInstruction(parts[0], parts[1], wire))
			} else {
				dependencies[wire] = setOf(parts[0], parts[2])
				unresolved.add(TwoOpInstruction(parts[0], parts[1], parts[2], wire))
			}
		}
		while (unresolved.isNotEmpty()) {
			val readyToProcess = mutableListOf<Instruction>()
			for (instruction in unresolved) {
				val deps = when (instruction) {
					is ConstantInstruction -> emptySet()
					is OneOpInstruction -> setOf(instruction.arg1)
					is TwoOpInstruction -> setOf(instruction.arg1, instruction.arg2)
					is DirectAssignInstruction -> setOf(instruction.value)
					else -> emptySet()
				}
				if (deps.all { it.toIntOrNull() != null || wires.containsKey(it) }) {
					readyToProcess.add(instruction)
				}
			}
			for (instruction in readyToProcess) {
				val value = instruction.evaluate(wires)
				wires[instruction.wire] = value
				unresolved.remove(instruction)
			}
		}
		return wires[queryString] ?: 0
	}

	override fun b(): Int {
		var modifiedInput = input.map {
			if (it.endsWith("-> b")) {
				"956 -> b"
			} else {
				it
			}
		}

		return processInstructions(modifiedInput, "a")
	}

}