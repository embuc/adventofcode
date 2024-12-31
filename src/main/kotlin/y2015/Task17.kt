package y2015

import Task

//--- Day 17: No Such Thing as Too Much ---
class Task17(val input: List<String>, val target: Int) : Task {

	override fun a(): Any {
		val containers = input.map { it.trim().toInt() }
		val combinations = mutableListOf<List<Int>>()
		fillEggnog(containers, target, 0, combinations)
		return combinations.size
	}

	private fun fillEggnog(
		containers: List<Int>,
		target: Int,
		index: Int = 0,
		found: MutableList<List<Int>>,
		current: MutableList<Int> = mutableListOf()
	) {
		// Base case
		if (index == containers.size || current.sum() >= target) {
//			println(current)
			if (current.sum() == target) {
				found.add(current.toList())
			}
			return
		}

		// Include
		current.add(containers[index])
		fillEggnog(containers, target, index + 1, found, current)

		// Exclude (backtrack)
		current.removeAt(current.size - 1)
		fillEggnog(containers, target, index + 1, found, current)
	}

	override fun b(): Any {
		TODO("Not yet implemented")
	}
}