package y2015

import Task

//--- Day 11: Corporate Policy ---
class Task11(val input: String) : Task {

	override fun a(): Any {
		return getNextCorporatePassword(input)
	}

	private fun getNextCorporatePassword(input: String): String {
		val builder = input.toCharArray()
		do {
			incrementPassword(builder)
		} while (!isValid(builder))
		return builder.joinToString("")
	}

	private fun isValid(builder: CharArray): Boolean {
		return hasIncreasingStraight(builder) && !hasForbiddenLetters(builder) && hasTwoPairs(builder)
	}

	private fun hasTwoPairs(builder: CharArray): Boolean {
		var pairs = 0
		var i = 0
		while (i < builder.size - 1) {
			if (builder[i] == builder[i + 1]) {
				pairs++
				i += 2
			} else {
				i++
			}
		}
		return pairs >= 2
	}

	private fun hasForbiddenLetters(builder: CharArray): Boolean {
		return builder.any { it == 'i' || it == 'o' || it == 'l' }
	}

	private fun hasIncreasingStraight(builder: CharArray): Boolean {
		for (i in 0 until builder.size - 2) {
			if (builder[i] + 1 == builder[i + 1] && builder[i + 1] + 1 == builder[i + 2]) {
				return true
			}
		}
		return false
	}

	private fun incrementPassword(builder: CharArray) {
		var i = builder.size - 1
		while (i >= 0) {
			if (builder[i] == 'z') {
				builder[i] = 'a'
				i--
			} else {
				builder[i]++
				break
			}
		}
	}

	override fun b(): Any {
		// not needed
		return 0
	}

}