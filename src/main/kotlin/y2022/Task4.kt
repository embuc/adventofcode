package y2022

import Task

//--- Day 4: Camp Cleanup ---
class Task4(val input: List<String>) : Task {
	override fun a(): Int {
		var count = 0
		for (line in input) {
			val (a, b) = line.split(",")
			val (minA, maxA) = a.split("-")
			val (minB, maxB) = b.split("-")
			val minAInt = minA.toInt()
			val maxAInt = maxA.toInt()
			val minBInt = minB.toInt()
			val maxBInt = maxB.toInt()
			if ((minAInt <= minBInt) && (maxAInt >= maxBInt) || (minAInt >= minBInt) && (maxAInt <= maxBInt)) {
				count++
			}
		}
		return count
	}

	override fun b(): Int {
		var count = 0
		for (line in input) {
			val (a, b) = line.split(",")
			val (minA, maxA) = a.split("-")
			val (minB, maxB) = b.split("-")
			val rangeA = minA.toInt()..maxA.toInt()
			val rangeB = minB.toInt()..maxB.toInt()
			if (rangeA.contains(rangeB.first) || rangeA.contains(rangeB.last) || rangeB.contains(rangeA.first) || rangeB.contains(rangeA.last)) {
				count++
			}
		}
		return count
	}
}