package y2023

import Task
import utils.readInputAsString

// --- Day 15: Lens Library ---
object Task15 : Task {

	override fun a(): Any {
		val strings = readInputAsString("2023_15.txt").split(",")
		return strings.sumOf {s-> calcHash(s) }
	}

	override fun b(): Any {
		return solvePart2(readInputAsString("2023_15.txt").split(","))
	}

	/**
	 * Calculates a hash value for a given string.
	 * The hash is calculated by taking the ASCII value of each character, adding to the current value,
	 * multiplying the result by 17, and taking the remainder when divided by 256.
	 */
	fun calcHash(s: String): Int =
		s.fold(0) { acc, char -> (acc + char.code) * 17 % 256 }

	// this one was also replaced by a more efficient solution from github.com/ClouddJR
	fun solvePart2(steps: List<String>): Int {
		val boxes = List(256) { mutableMapOf<String, Int>() }

		steps.forEach { step ->
			when {
				"-" in step -> {
					val label = step.substringBefore("-")
					boxes[calcHash(label)].remove(label)
				}

				"=" in step -> {
					val label = step.substringBefore("=")
					val length = step.substringAfter("=").toInt()
					boxes[calcHash(label)][label] = length
				}
			}
		}

		return boxes.withIndex().sumOf { (i, box) ->
			box.values.withIndex().sumOf { (j, focalLength) -> (i + 1) * (j + 1) * focalLength }
		}
	}
}