package y2017

import Task

//--- Day 4: High-Entropy Passphrases ---
class Task4(val input:List<String>) : Task {
	override fun a(): Any {
		return input.count { line ->
			val words = line.split(" ")
			words.size == words.distinct().size
		}
	}

	override fun b(): Any {
		return input.count { line ->
			val words = line.split(" ")
			val anagrams = words.map { it.toCharArray().sorted().toString() }.toList()
			words.size == anagrams.distinct().size
		}
	}
}