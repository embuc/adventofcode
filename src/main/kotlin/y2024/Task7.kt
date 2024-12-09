package y2024

/* --- Day 7: Bridge Repair ---
* Sort of brute force solution here. */
import Task

class Task7(val input: List<String>) : Task {

	override fun a(): Any {
		return input.parallelStream().mapToLong { line ->
			val (expectedResult, terms) = parseLine(line)
			val operators = listOf("+", "*")
			// Directly evaluate while generating combinations
			if (combineAndEvaluate(terms, operators, expectedResult)) expectedResult else 0L
		}.sum()
	}

	override fun b(): Any {
		val operators = listOf("+", "*")
		val operatorsExt = listOf("+", "*", "||")

		return input.parallelStream().mapToLong { line ->
			val (expectedResult, terms) = parseLine(line)
			if (combineAndEvaluate(terms, operators, expectedResult) ||
				combineAndEvaluate(terms, operatorsExt, expectedResult)
			) {
				expectedResult
			} else {
				0L
			}
		}.sum()
	}

	/**
	 * Evaluates combinations of terms and operators directly while generating them.
	 * This avoids fully generating all combinations upfront (speeds up a bit).
	 */
	fun combineAndEvaluate(terms: List<String>, operators: List<String>, expectedResult: Long): Boolean {
		val gaps = terms.size - 1

		// Backtracking to generate and evaluate combinations
		fun generateCombinations(current: MutableList<String>): Boolean {
			if (current.size == gaps) {
				val combined = mutableListOf<String>()
				for (i in terms.indices) {
					combined.add(terms[i])
					if (i < current.size) {
						combined.add(current[i])
					}
				}
				// Evaluate directly during combination generation
				if (evaluateTokensLeftToRight(expectedResult, combined) == expectedResult) {
					return true // Short-circuit if a match is found
				}
				return false
			}

			for (op in operators) {
				current.add(op) // Choose
				if (generateCombinations(current)) {
					return true // Propagate early exit
				}
				current.removeAt(current.size - 1) // Backtrack
			}
			return false
		}

		return generateCombinations(mutableListOf())
	}

	fun evaluateTokensLeftToRight(expectedResult: Long, tokens: List<String>): Long {
		var result = tokens[0].toLong()

		for (i in 1 until tokens.size step 2) {
			val operator = tokens[i]
			val nextOperand = tokens[i + 1].toLong()

			// Optimization: Early exit if the result exceeds expectedResult
			when (operator) {
				"*" -> {
					if (result > expectedResult / nextOperand) return 0L // Stop early
					result *= nextOperand
				}

				"+" -> {
					result += nextOperand
				}

				"||" -> {
					val concatenated = result.toString() + nextOperand.toString()
					if (concatenated.length > expectedResult.toString().length) return 0L // Stop early
					result = concatenated.toLong()
				}

				else -> throw IllegalArgumentException("Unsupported operator: $operator")
			}

			if (result > expectedResult) return 0L // Stop further computation
		}

		return result
	}

	private fun parseLine(line: String): Pair<Long, List<String>> {
		val parts = line.split(":")
		val result = parts[0].toLong()
		val terms = parts[1].trim().split(" ")
		return Pair(result, terms)
	}
}
