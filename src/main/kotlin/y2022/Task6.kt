package y2022

import Task

//--- Day 6: Tuning Trouble ---
class Task6(val input: String) : Task {
	override fun a(): Any {
		val input = input.toCharArray()
		for (i in input.indices) {
			if (i < 3) {
				continue
			}
			if (input[i] != input[i - 1] && input[i] != input[i - 2] && input[i] != input[i - 3] &&
				input[i - 1] != input[i - 2] && input[i - 1] != input[i - 3] &&
				input[i - 2] != input[i - 3]) {
				return i + 1
			}
		}
		return 1
	}

	override fun b(): Any {
		val input = input.toCharArray()
		for (i in input.indices) {
			if (i < 14) {
				continue
			}
			val set = mutableSetOf<Char>()
			for (j in 0..<14) {
				set.add(input[i - j])
			}
			if (set.size == 14) {
				return i + 1
			}
		}
		return 1
	}
}