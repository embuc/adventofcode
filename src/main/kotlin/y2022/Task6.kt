package y2022

import Task

//--- Day 6: Tuning Trouble ---
class Task6(val input: String) : Task {
	override fun a(): Any {
		return findUniqueSequence(4)
	}

	override fun b(): Any {
		return findUniqueSequence(14)
	}

	private fun findUniqueSequence(length: Int): Int {
		val input = input.toCharArray()
		for (i in length - 1 until input.size) {
			if (input.slice(i - length + 1..i).toSet().size == length) {
				return i + 1
			}
		}
		return 1
	}
}