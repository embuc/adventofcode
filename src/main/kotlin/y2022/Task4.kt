package y2022

import Task

//--- Day 4: Camp Cleanup ---
class Task4(val input: List<String>) : Task {
	override fun a(): Int {
		return input.count { line ->
			val (a, b) = line.split(",")
			val (minA, maxA) = a.split("-").map { it.toInt() }
			val (minB, maxB) = b.split("-").map { it.toInt() }
			(minA <= minB && maxA >= maxB) || (minA >= minB && maxA <= maxB)
		}
	}

	override fun b(): Int {
		return input.count { line ->
			val (a, b) = line.split(",")
			val (minA, maxA) = a.split("-").map { it.toInt() }
			val (minB, maxB) = b.split("-").map { it.toInt() }
			val rangeA = minA..maxA
			val rangeB = minB..maxB
			rangeA.contains(rangeB.first) || rangeA.contains(rangeB.last) || rangeB.contains(rangeA.first) || rangeB.contains(rangeA.last)
		}
	}
}