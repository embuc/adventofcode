package y2015

import Task

//--- Day 13: Knights of the Dinner Table ---
class Task13(val input: List<String>) : Task {

	override fun a(): Any {
		val dict = mutableMapOf<Pair<String, String>, Int>()
		val persons = mutableSetOf<String>()
		for (line in input) {
			val strings = line.split(" ")
			val person1 = strings[0].trim()
			val person2 = strings[10].substring(0, strings[10].length - 1).trim()
			val happiness = if (strings[2] == "gain") strings[3].toInt() else -strings[3].toInt()
			dict[person1 to person2] = happiness
			persons.add(person1)
		}
		var max = Int.MIN_VALUE
		generateCircularPermutations(persons.toList()).forEach {
			val happiness = checkHappiness(it, dict)
			if (happiness > max) {
				max = happiness
			}
		}
		return max
	}

	private fun checkHappiness(it: List<String>, dict: MutableMap<Pair<String, String>, Int>): Int {
		var happiness = 0
		for (i in it.indices) {
			val person1 = it[i]
			val person2 = it[(i + 1) % it.size]
			happiness += dict[person1 to person2]!!
			happiness += dict[person2 to person1]!!
		}
		return happiness
	}

	fun generateCircularPermutations(input: List<String>): List<List<String>> {
		if (input.isEmpty()) return emptyList()

		val result = mutableListOf<List<String>>()
		// Fix the first person in one position
		val fixedPerson = input[0]
		val remainingPeople = input.subList(1, input.size)

		// Generate permutations of the remaining people
		generatePermutationsRecursive(remainingPeople.toMutableList(), mutableListOf(fixedPerson), result)

		return result
	}

	private fun generatePermutationsRecursive(
		remaining: MutableList<String>,
		current: MutableList<String>,
		result: MutableList<List<String>>
	) {
		if (remaining.isEmpty()) {
			result.add(current.toList())
			return
		}

		for (i in remaining.indices) {
			// Create a new list excluding the current element
			val newRemaining = remaining.toMutableList().apply { removeAt(i) }
			// Add the current element to the current permutation
			generatePermutationsRecursive(newRemaining, current.apply { add(remaining[i]) }, result)
			// Backtrack: remove the last added element
			current.removeAt(current.size - 1)
		}
	}

	override fun b(): Any {
		return 0
	}

}