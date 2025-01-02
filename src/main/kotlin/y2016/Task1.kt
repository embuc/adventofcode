package y2016

import Task
import utils.Point2D

//--- Day 1: No Time for a Taxicab ---
class Task1(val input: String) : Task {

	override fun a(): Any {
		val instructions = input.split(",").map { it.trim() }
		return processInstructions(instructions, Point2D(0, 0))
	}

	override fun b(): Any {
		val instructions = input.split(",").map { it.trim() }
		return processInstructions(instructions, Point2D(0, 0), true)
	}

	private fun processInstructions(
		instructions: List<String>,
		currPoint: Point2D,
		tractVisited: Boolean = false,
	): Int {
		val visited = mutableSetOf<Point2D>()
		instructions.forEach { instruction ->
			val (char, steps) = instruction[0] to instruction.substring(1).toInt()
			when (char) {
				'L' -> currPoint.turn(Point2D.Turn.LEFT)
				'R' -> currPoint.turn(Point2D.Turn.RIGHT)
			}

			repeat(steps) {
				currPoint.move(1)
				if (tractVisited) {
					if (currPoint.toXY() in visited) {
						return currPoint.manhattanDistance(Point2D())
					}
					visited.add(currPoint.toXY())
				}
			}

		}
		return currPoint.manhattanDistance(Point2D())
	}
}