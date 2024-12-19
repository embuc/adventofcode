package y2024

import Task

/*--- Day 19: Linen Layout ---*/
class Task19(val input: List<String>) : Task {
	override fun a(): Any {
//		r, wr, b, g, bwu, rb, gb, br
//
//		brwrr
//		bggr
//		gbbr
//		rrbgbr
//		ubwu
//		bwurrg
//		brgr
//		bbrgwb

//		brwrr can be made with a br towel, then a wr towel, and then finally an r towel.
//		bggr can be made with a b towel, two g towels, and then an r towel.
//		gbbr can be made with a gb towel and then a br towel.
//		rrbgbr can be made with r, rb, g, and br.
//		ubwu is impossible.
//		bwurrg can be made with bwu, r, r, and g.
//		brgr can be made with br, g, and r.
//		bbrgwb is impossible.
		val towels = input[0].split(", ")
		val designs = input.drop(2).map { it.trim() }
//		println("towels: $towels")
//		println("designs: $designs")
		var count = 0
		for (d in designs) {
//			println("Design: $d")
			val result = combineTowels(d, towels)
//			println("Result: $result")
			if (result.isNotEmpty()) {
				count++
			}
		}
		return count
	}

	

	fun combineTowels(
		query: String,
		towels: List<String>,
		current: MutableList<String> = mutableListOf(),
		results: MutableList<List<String>> = mutableListOf()
	): MutableList<List<String>> {
		// Convert current MutableList to String and check if it matches the query
		val currentString = current.joinToString("")

		// Base case: if currentString matches query, print and return
		if (currentString == query) {
//			println("Match found: $current")
			results.add(current.toList())
			return results
		}

		// If current string length exceeds query length, return to avoid unnecessary processing
		// Stop if the current string length exceeds the query length
		if (currentString.length > query.length) {
			return results
		}

		// Iterate through each towel allowing reuse of towels
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