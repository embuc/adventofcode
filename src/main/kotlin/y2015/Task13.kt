package y2015

import Task

//--- Day 13: Knights of the Dinner Table ---
class Task13(val input: List<String>) : Task {

	override fun a(): Any {
		val happinessMap = mutableMapOf<Pair<String, String>, Int>()
		val people = mutableSetOf<String>()

		// Parse input and build the happiness map
		for (line in input) {
			val parts = line.split(" ")
			val person1 = parts[0].trim()
			val person2 = parts[10].removeSuffix(".").trim()
			val happinessChange = if (parts[2] == "gain") parts[3].toInt() else -parts[3].toInt()

			happinessMap[person1 to person2] = happinessChange
			people.add(person1)
		}

		// Find the maximum happiness for all circular seating arrangements
		val maxHappiness = generateCircularPermutations(people.toList())
			.maxOf { calculateHappiness(it, happinessMap) }

		return maxHappiness
	}

	// Calculate the total happiness for a given seating arrangement
	private fun calculateHappiness(arrangement: List<String>, happinessMap: Map<Pair<String, String>, Int>): Int {
		return arrangement.indices.sumOf { i ->
			val person1 = arrangement[i]
			val person2 = arrangement[(i + 1) % arrangement.size]
			(happinessMap[person1 to person2] ?: 0) + (happinessMap[person2 to person1] ?: 0)
		}
	}

	// Generate all circular permutations of the input list
	private fun generateCircularPermutations(people: List<String>): List<List<String>> {
		if (people.isEmpty()) return emptyList()

		val fixedPerson = people.first()
		val permutations = mutableListOf<List<String>>()

		generatePermutationsRecursive(people.drop(1), mutableListOf(fixedPerson), permutations)
		return permutations
	}

	// Recursive helper function to generate permutations
	private fun generatePermutationsRecursive(
		remaining: List<String>,
		current: MutableList<String>,
		result: MutableList<List<String>>
	) {
		if (remaining.isEmpty()) {
			result.add(current.toList())
			return
		}

		for (i in remaining.indices) {
			val nextPerson = remaining[i]
			generatePermutationsRecursive(
				remaining.filterIndexed { index, _ -> index != i },
				current.apply { add(nextPerson) },
				result
			)
			current.removeAt(current.lastIndex) // Backtrack
		}
	}

	override fun b(): Any {
		return 0
	}

}