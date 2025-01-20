package y2017

import Task
import utils.Point2D
import utils.toGrid

//--- Day 22: Sporifica Virus ---
class Task22(val input: List<String>) : Task {

	// Fast coordinate storage for realistic grid sizes
	private class CoordSet {
		private val coords = HashSet<Int>()

		// Fast additive hash: hash = x + (y * 10001). 10001 is prime, large enough for our movement, and efficient.
		// This 'ensures' no collisions.
		fun add(x: Int, y: Int) = coords.add(x + y * 10001)
		fun remove(x: Int, y: Int) = coords.remove(x + y * 10001)
		fun contains(x: Int, y: Int) = coords.contains(x + y * 10001)
	}

	override fun a(): Any {
		val (grid, point) = initializeGridAndPoint()
		val infected = CoordSet()
		initializeInfectedSet(grid, infected)
		var countNewInfections = 0

		repeat(10_000) {
			if (infected.contains(point.x, point.y)) {
				point.turn(Point2D.Turn.RIGHT)
				infected.remove(point.x, point.y)
			} else {
				point.turn(Point2D.Turn.LEFT)
				infected.add(point.x, point.y)
				countNewInfections++
			}
			point.move(1)
		}

		return countNewInfections
	}

	override fun b(): Any {
		val (grid, point) = initializeGridAndPoint()
		val infected = CoordSet()
		val weakened = CoordSet()
		val flagged = CoordSet()
		initializeInfectedSet(grid, infected)
		var countNewInfections = 0

		repeat(10_000_000) {
			when {
				infected.contains(point.x, point.y) -> {
					point.turn(Point2D.Turn.RIGHT)
					infected.remove(point.x, point.y)
					flagged.add(point.x, point.y)
				}

				weakened.contains(point.x, point.y) -> {
					weakened.remove(point.x, point.y)
					infected.add(point.x, point.y)
					countNewInfections++
				}

				flagged.contains(point.x, point.y) -> {
					point.turn(Point2D.Turn.AROUND)
					flagged.remove(point.x, point.y)
				}

				else -> {
					point.turn(Point2D.Turn.LEFT)
					weakened.add(point.x, point.y)
				}
			}
			point.move(1)
		}

		return countNewInfections
	}

	private fun initializeGridAndPoint(): Pair<Array<Array<Triple<Int, Int, Char>>>, Point2D> {
		val grid = toGrid(input)
		val point = Point2D(grid.size / 2, grid[0].size / 2, Point2D.Direction.NORTH)
		return Pair(grid, point)
	}

	private fun initializeInfectedSet(grid: Array<Array<Triple<Int, Int, Char>>>, infected: CoordSet) {
		grid.forEachIndexed { x, row ->
			row.forEachIndexed { y, node ->
				if (node.third == '#') {
					infected.add(x, y)
				}
			}
		}
	}
}