package y2016

import Task

//--- Day 3: Squares With Three Sides ---
class Task3(val input: List<String>) : Task {

	override fun a(): Any {
		var sum = 0
		for (line in input) {
			val sides = line.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
			if (sides[0] + sides[1] > sides[2] && sides[0] + sides[2] > sides[1] && sides[1] + sides[2] > sides[0]) {
				sum++
			}
		}
		return sum
	}

	override fun b(): Any {
		var sum = 0
		for (i in input.indices step 3) {
			val line1 = input[i]
			val line2 = input[i + 1]
			val line3 = input[i + 2]
			val sides1 = line1.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
			val sides2 = line2.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
			val sides3 = line3.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }

			if (sides1[0] + sides2[0] > sides3[0] && sides1[0] + sides3[0] > sides2[0] && sides2[0] + sides3[0] > sides1[0]) {
				sum++
			}
			if (sides1[1] + sides2[1] > sides3[1] && sides1[1] + sides3[1] > sides2[1] && sides2[1] + sides3[1] > sides1[1]) {
				sum++
			}
			if (sides1[2] + sides2[2] > sides3[2] && sides1[2] + sides3[2] > sides2[2] && sides2[2] + sides3[2] > sides1[2]) {
				sum++
			}
		}
		return sum
	}
}