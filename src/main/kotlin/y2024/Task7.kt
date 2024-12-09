package y2024

import Task

/* --- Day 7: Bridge Repair ---
* Sort of brute force solutioon here. */
class Task7(val input:List<String>) : Task {

	override fun a(): Any {
		return input.sumOf { line ->
			val (expectedResult, terms) = parseLine(line)
			val operators = listOf("+", "*")
			if (combineLists(terms, operators).any { evaluateTokensLeftToRight(expectedResult, it) == expectedResult }) expectedResult else 0L
		}
	}

	override fun b(): Any {
		val operators = listOf("+", "*")
		val operatorsExt = listOf("+", "*", "||")
		return input.sumOf { line ->
			val (expectedResult, terms) = parseLine(line)
			if (combineLists(terms, operators).any { evaluateTokensLeftToRight(expectedResult, it) == expectedResult }) {
				expectedResult
			} else {
				if (combineLists(terms, operatorsExt).any { evaluateTokensLeftToRight(expectedResult, it) == expectedResult }) expectedResult else 0L
			}
		}
	}

	fun evaluateTokensLeftToRight(expectedResult: Long, tokens: List<String>): Long {
		// Start with the first number
		var result = tokens[0].toLong()

		for (i in 1 until tokens.size step 2) {
			val operator = tokens[i] // Current operator (e.g., * or + or ||)
			val nextOperand = tokens[i + 1] // Next number
			if (result>expectedResult) return 0L // Optimization

			when (operator) {
				"*" -> result *= nextOperand.toLong()
				"+" -> result += nextOperand.toLong()
				"||" -> result = (result.toString() + nextOperand).toLong()
				else -> throw IllegalArgumentException("Unsupported operator: $operator")
			}
		}

		return result
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