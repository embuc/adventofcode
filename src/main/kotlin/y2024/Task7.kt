package y2024

import Task

/* --- Day 7: Bridge Repair --- */
class Task7(val input:List<String>) : Task {

	val operators = listOf("+", "*")

	override fun a(): Any {
		var sum = 0L
		for (line in input) {
			val (result, terms) = parseLine(line);
			for (list in combineLists(terms, operators)) {
				val res = evaluateTokensLeftToRight(list)
				if(res == result) {
					sum += result
					break
				}
			}
		}
		return sum
	}

	fun evaluateTokensLeftToRight(tokens: List<String>): Long {
		// Start with the first number
		var result = tokens[0].toLong()

		// Iterate over the tokens
		for (i in 1 until tokens.size step 2) {
			val operator = tokens[i] // Current operator (e.g., * or +)
			val nextOperand = tokens[i + 1] // Next number

			// Apply the operator
			when (operator) {
				"*" -> result *= nextOperand.toLong() // Multiply
				"+" -> result += nextOperand.toLong() // Add
				else -> throw IllegalArgumentException("Unsupported operator: $operator")
			}
		}

		return result
	}

	override fun b(): Any {
		return 2
	}

	fun combineLists(list1: List<String>, list2: List<String>): List<List<String>> {
		val gaps = list1.size - 1
		val gapCombinations = mutableListOf<List<String>>()

		// Backtracking to generate all combinations of `list2` for `gaps` positions
		fun generateCombinations(current: MutableList<String>) {
			if (current.size == gaps) {
				gapCombinations.add(current.toList()) // Add a copy of the current combination
				return
			}

			for (item in list2) {
				current.add(item) // Choose
				generateCombinations(current) // Explore
				current.removeAt(current.size - 1) // Backtrack
			}
		}

		generateCombinations(mutableListOf())

		val results = mutableListOf<List<String>>()
		for (combination in gapCombinations) {
			val result = mutableListOf<String>()
			for (i in list1.indices) {
				result.add(list1[i])
				if (i < combination.size) {
					result.add(combination[i])
				}
			}
			results.add(result)
		}
		return results
	}


	private fun parseLine(line: String): Pair<Long, List<String>> {
		val parts = line.split(":")
		val result = parts[0].toLong()
		val terms = parts[1].trim().split(" ")
		return Pair(result, terms)
	}
}