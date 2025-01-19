package y2017

import Task
import utils.Point2D
import utils.toGrid

//--- Day 19: A Series of Tubes ---
class Task19 (val input:List<String>) :Task {

	override fun a(): Any {
		return traverseGrid { letters, _ -> letters }
	}

	override fun b(): Any {
		return traverseGrid { _, steps -> steps }
	}

	private fun traverseGrid(resultExtractor: (String, Int) -> Any): Any {
		val grid = toGrid(input)
		val point = Point2D()

		// Find the starting point
		for (i in grid[0].indices) {
			if (grid[0][i].third == '|') {
				point.x = 0
				point.y = i
				point.direction = Point2D.Direction.SOUTH
			}
		}

		var letters = ""
		var steps = 0

		while (true) {
			steps++
			point.move(1)

			if (point.x !in grid.indices || point.y !in grid[0].indices) {
				break
			}

			val c = grid[point.x][point.y].third
			if (c == ' ') {
				break
			}

			if (c in ('A'..'Z')) {
				letters += c
			}

			if (c == '+') {
				val neighbors = point.getCardinalNeighbors()
				for (n in neighbors) {
					// Check if neighbor is valid and not the one we came from (opposite direction)
					if (n.x in grid.indices && n.y in grid[0].indices &&
						grid[n.x][n.y].third != ' ' &&
						n != point.getPreviousOpositeDirection()
					) {
						point.face(point.getRelativeDirection(n))
						break
					}
				}
			}
		}

		return resultExtractor(letters, steps)
	}
}