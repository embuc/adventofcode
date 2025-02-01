package y2022

import Task

//--- Day 1: Calorie Counting ---
class Task1(val input: List<String>) : Task {
	override fun a(): Any {
		var maxScore = 0
		var score = 0
		for (line in input) {
			if (!line.isEmpty()) {
				score += line.toInt()
			} else {
				if (score > maxScore) {
					maxScore = score
				}
				score = 0
			}
		}
		return maxScore
	}

	override fun b(): Any {
		val scores = mutableListOf<Int>()
		var score = 0
		for (line in input) {
			if (!line.isEmpty()) {
				score += line.toInt()
			} else {
				scores.add(score)
				score = 0
			}
		}
		return scores.sorted().subList(scores.size - 3, scores.size).reduce { a, b -> a + b }
	}
}