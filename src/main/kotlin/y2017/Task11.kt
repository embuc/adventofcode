package y2017

import Task
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sign

//--- Day 11: Hex Ed ---
class Task11(val input: String) : Task {
	override fun a(): Any {
		val directions = input.split(",")
		var x = 0
		var y = 0
		for (direction in directions) {
			when (direction) {
				"n" -> x--
				"s" -> x++
				"ne" -> {
					x--
					y++
				}

				"nw" -> {
					y--
				}

				"se" -> {
					y++
				}

				"sw" -> {
					x++
					y--
				}
			}
		}
		return hexaManhattanDistance(0, 0, x, y)
	}

	private fun hexaManhattanDistance(x0: Int, y0: Int, x1: Int, y1: Int): Int {
		val dx = x1 - x0
		val dy = y1 - y0
		val distance = if (sign(dx.toDouble()) == sign(dy.toDouble()))
			abs(dx) + abs(dy)
		else
			max(abs(dx), abs(dy))

		return distance
	}

	override fun b(): Any {
		val directions = input.split(",")
		var x = 0
		var y = 0
		var longest = 0
		for (direction in directions) {
			when (direction) {
				"n" -> x--
				"s" -> x++
				"ne" -> {
					x--
					y++
				}

				"nw" -> {
					y--
				}

				"se" -> {
					y++
				}

				"sw" -> {
					x++
					y--
				}
			}
			longest = max(longest, hexaManhattanDistance(0, 0, x, y))
		}
		return longest
	}
}