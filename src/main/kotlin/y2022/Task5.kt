package y2022

import Task

//--- Day 5: Supply Stacks ---
class Task5(val input: List<String>) : Task {
	private data class Stack(val id: Int, var items: MutableList<String>)
	private data class Instruction(val from: Int, val to: Int, val count: Int)

	private fun parseInput(): Pair<List<Stack>, List<Instruction>> {
		// Parse initial stacks
		val stacks = (1..9).map { stackId ->
			val items = (7 downTo 0)
				.mapNotNull { row ->
					val char = input[row][1 + 4 * (stackId - 1)]
					if (char != ' ') char.toString() else null
				}
				.toMutableList()
			Stack(stackId, items)
		}

		// Parse move instructions
		val instructions = input.subList(10, input.size)
			.map { line ->
				line.split(" ").let {
					Instruction(
						from = it[3].toInt(),
						to = it[5].toInt(),
						count = it[1].toInt()
					)
				}
			}

		return Pair(stacks, instructions)
	}

	private fun executeInstructions(
		stacks: List<Stack>,
		instructions: List<Instruction>,
		reverseItems: Boolean
	): String {
		instructions.forEach { instruction ->
			val fromStack = stacks.find { it.id == instruction.from }!!
			val toStack = stacks.find { it.id == instruction.to }!!

			val start = maxOf(fromStack.items.size - instruction.count, 0)
			var itemsToMove = fromStack.items.subList(start, fromStack.items.size)

			toStack.items.addAll(if (reverseItems) itemsToMove.reversed() else itemsToMove)
			fromStack.items = fromStack.items.subList(0, start).toMutableList()
		}

		return stacks.joinToString("") { it.items.lastOrNull()?.toString() ?: "" }
	}

	override fun a(): Any {
		val (stacks, instructions) = parseInput()
		return executeInstructions(stacks, instructions, reverseItems = true)
	}

	override fun b(): Any {
		val (stacks, instructions) = parseInput()
		return executeInstructions(stacks, instructions, reverseItems = false)
	}
}