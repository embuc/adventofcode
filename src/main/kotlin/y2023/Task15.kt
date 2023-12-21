package y2023

import Task
import utils.readInputAsString

object Task15 : Task {

	override fun a(): Any {
		val strings = readInputAsString("2023_15.txt").split(",")
		return strings.sumOf {s-> calcHash(s) }
	}

	override fun b(): Any {
		return solvePart2(readInputAsString("2023_15.txt").split(","))
	}

	//	Determine the ASCII code for the current character of the string.
	//	Increase the current value by the ASCII code you just determined.
	//	Set the current value to itself multiplied by 17.
	//	Set the current value to the remainder of dividing itself by 256.
	fun calcHash(s:String): Int =
		s.toList().fold(0) { prevResult, currChar -> ((prevResult + currChar.code) * 17) % 256 }

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