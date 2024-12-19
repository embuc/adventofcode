package y2024

import Task

/*--- Day 19: Linen Layout ---*/
class Task19(val input: List<String>) : Task {
	val towels = input[0].split(", ")
	val designs = input.drop(2).map { it.trim() }

	override fun a(): Any {
		return designs.count { canFormQuery(it, towels) }
	}

	/* dp */
	private fun canFormQuery(query: String, towels: List<String>): Boolean {
		val dp = BooleanArray(query.length + 1)
		dp[0] = true  // base case: empty string can always be formed

		for (i in 1..query.length) {
			for (towel in towels) {
				if (i >= towel.length && dp[i - towel.length]) {
					if (query.substring(i - towel.length, i) == towel) {
						dp[i] = true
						break
					}
				}
			}
		}
		return dp[query.length]
	}

	/* backtracking, works nicely on smaller inputs but fails miserably on larger inputs */
	fun combineTowels(
		query: String,
		towels: List<String>,
		current: MutableList<String> = mutableListOf(),
		results: MutableList<List<String>> = mutableListOf()
	): MutableList<List<String>> {
		val currentString = current.joinToString("")

		// Base case: If the current string matches the query, add it to the results
		if (currentString == query) {
			results.add(current.toList())
			return results
		}

		// Cutoff: Stop if the current string length exceeds the query length
		if (currentString.length > query.length) {
			return results
		}

		// Iterate through each towel allowing *reuse* of towels
		for (towel in towels) {
			// Include the current towel and recurse
			current.add(towel)
			combineTowels(query, towels, current, results)

			// Backtrack by removing the last added towel
			current.removeAt(current.size - 1)
		}
		return results
	}

	override fun b(): Any {
		var count = 0L
		for (design in designs) {
			val combinations = countCombinations(design, towels)
			count+= combinations
		}
		return count
	}

	fun countCombinations(query: String, towels: List<String>): Long {
		// DP array to store counts of ways to form each substring of 'query'
		val dp = LongArray(query.length + 1)
		dp[0] = 1L  // There is one way to form the empty string: by using no towels

		for (i in 1..query.length) {
			for (towel in towels) {
				val start = i - towel.length
				if (start >= 0 && query.substring(start, i) == towel) {
					dp[i] += dp[start]  // Increment count by the number of ways to form the string ending just before this towel
				}
			}
		}

		return dp[query.length]  // The total count of ways to form the entire query string
	}

	fun findAllCombinations(query: String, towels: List<String>): List<List<String>> {
		// DP array where each element is a mutable list of mutable lists of strings
		val dp = Array<MutableList<MutableList<String>>>(query.length + 1) { mutableListOf() }
		dp[0].add(mutableListOf())  // Base case: one way to form the empty string

		for (i in 1..query.length) {
			for (towel in towels) {
				val start = i - towel.length
				if (start >= 0 && query.substring(start, i) == towel) {
					for (combination in dp[start]) {
						val newCombination = combination.toMutableList()
						newCombination.add(towel)
						dp[i].add(newCombination)
					}
				}
			}
		}

		return dp[query.length]
	}

}