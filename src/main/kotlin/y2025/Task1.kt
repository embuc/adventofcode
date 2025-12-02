package y2025

import Task
import kotlin.math.abs

//--- Day 1: Secret Entrance ---
class Task1(val input1: List<String>) : Task {

	override fun a(): Any {
		var start = 50
		var code = 0
		for (line in input1) {
			val dir = line[0]
			val dist = line.substring(1).toInt()
			when (dir) {
				'L' -> start -= dist
				'R' -> start += dist
			}
			start %= 100
			if (start < 0) start += 100
			if (start == 0) code++
		}
		return code
	}

	override fun b(): Any {
		var start = 50
		var code = 0
		for (line in input1) {
			val dir = line[0]
			val dist = line.substring(1).toInt()
			val originalStart = start

			when (dir) {
				'L' -> start -= dist
				'R' -> start += dist
			}
			if (start < 100 && start > 0) {
				// do nothing
			} else if (start < 0) {
				// Calculate how many times we cross 0 (ceiling division)
				val wraps = (abs(start) + 99) / 100
				code += wraps
				// Normalize to 0-99 range
				start = (start % 100 + 100) % 100
				// Adjust for edge cases: leaving from 0 shouldn't count, arriving at 0 should
				if (originalStart == 0) code--
				if (start == 0) code++
			} else if (start >= 100) {
				code += start / 100
				start %= 100
			} else {
				// exactly 0
				code++
			}
		}
		return code
	}

}