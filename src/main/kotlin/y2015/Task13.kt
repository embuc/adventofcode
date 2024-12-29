package y2015

import Task

//--- Day 13: Knights of the Dinner Table ---
class Task13(val input: List<String>) : Task {

	override fun a(): Any {
		val happinessMap = mutableMapOf<Pair<String, String>, Int>()
		val people = mutableSetOf<String>()

		for (line in input) {
			val parts = line.split(" ")
			val person1 = parts[0].trim()
			val person2 = parts[10].removeSuffix(".").trim()
			val happinessChange = if (parts[2] == "gain") parts[3].toInt() else -parts[3].toInt()

			happinessMap[person1 to person2] = happinessChange
			people.add(person1)
		}

		val maxHappiness = generateCircularPermutations(people.toList())
			.maxOf { calculateHappiness(it, happinessMap) }

		return maxHappiness
	}

	private fun calculateHappiness(arrangement: List<String>, happinessMap: Map<Pair<String, String>, Int>): Int {
		return arrangement.indices.sumOf { i ->
			val person1 = arrangement[i]
			val person2 = arrangement[(i + 1) % arrangement.size]
			(happinessMap[person1 to person2] ?: 0) + (happinessMap[person2 to person1] ?: 0)
		}
	}

	private fun generateCircularPermutations(people: List<String>): List<List<String>> {
		if (people.isEmpty()) return emptyList()

		val fixedPerson = people.first()
		val permutations = mutableListOf<List<String>>()

		generatePermutationsRecursive(people.drop(1), mutableListOf(fixedPerson), permutations)
		return permutations
	}

	private fun generatePermutationsRecursive(remaining: List<String>,current: MutableList<String>,result: MutableList<List<String>>) {
		if (remaining.isEmpty()) {
			result.add(current.toList())
			return
		}

		for (i in remaining.indices) {
			val nextPerson = remaining[i]
			val newRemaining = remaining.toMutableList().apply { removeAt(i) }
			current.add(nextPerson)
			generatePermutationsRecursive(newRemaining, current, result)
			// Backtrack: Remove the last added person to try other options
			current.removeAt(current.size - 1)
		}
	}

	override fun b(): Any {
		val happinessMap = mutableMapOf<Pair<String, String>, Int>()
		val people = mutableSetOf<String>()

		for (line in input) {
			val parts = line.split(" ")
			val person1 = parts[0].trim()
			val person2 = parts[10].removeSuffix(".").trim()
			val happinessChange = if (parts[2] == "gain") parts[3].toInt() else -parts[3].toInt()

			happinessMap[person1 to person2] = happinessChange
			people.add(person1)
		}

		for (person in people) {
			happinessMap.putIfAbsent(Pair("self", person), 0)
			happinessMap.putIfAbsent(Pair(person, "self"), 0)
		}
		people.add("self")

		val maxHappiness = generateCircularPermutations(people.toList())
			.maxOf { calculateHappiness(it, happinessMap) }

		return maxHappiness
	}

}