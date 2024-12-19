package y2024

import Task

/*--- Day 19: Linen Layout ---*/
class Task19(val input: List<String>) : Task {
	override fun a(): Any {
		val towels = input[0].split(", ")
		val designs = input.drop(2).map { it.trim() }
		println("towels: ${towels.size}")
		println("designs: ${designs.size}")
		var count = 0
		var i = 0
		for (d in designs) {
			println("Design: $d  $i")
			if (canFormQuery(d, towels)) {
				count++
			}
		}
		return count
	}

	/* dp */
	fun canFormQuery(query: String, towels: List<String>): Boolean {
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
		return 0
	}
}