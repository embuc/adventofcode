package y2017

import Task
import utils.Point2D
import utils.toGrid

//--- Day 22: Sporifica Virus ---
class Task22(val input: List<String>) : Task {

	override fun a(): Any {
		val (grid, point) = initializeGridAndPoint()
		val infected = initializeInfectedSet(grid)
		var countNewInfections = 0

		repeat(10_000) {
			if (point.toPair() in infected) {
				point.turn(Point2D.Turn.RIGHT)
				infected.remove(point.toPair())
			} else {
				point.turn(Point2D.Turn.LEFT)
				infected.add(point.toPair())
				countNewInfections++
			}
			point.move(1)
		}

		return countNewInfections
	}

	override fun b(): Any {
		val (grid, point) = initializeGridAndPoint()
		val infected = initializeInfectedSet(grid)
		val weakened = mutableSetOf<Pair<Int, Int>>()
		val flagged = mutableSetOf<Pair<Int, Int>>()
		var countNewInfections = 0

		repeat(10_000_000) {
			when (point.toPair()) {
				in infected -> {
					point.turn(Point2D.Turn.RIGHT)
					infected.remove(point.toPair())
					flagged.add(point.toPair())
				}
				in weakened -> {
					weakened.remove(point.toPair())
					infected.add(point.toPair())
					countNewInfections++
				}
				in flagged -> {
					point.turn(Point2D.Turn.AROUND)
					flagged.remove(point.toPair())
				}
				else -> {
					point.turn(Point2D.Turn.LEFT)
					weakened.add(point.toPair())
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

	private fun initializeInfectedSet(grid: Array<Array<Triple<Int, Int, Char>>>): MutableSet<Pair<Int, Int>> {
		val infected = mutableSetOf<Pair<Int, Int>>()
		grid.forEachIndexed { x, row ->
			row.forEachIndexed { y, node ->
				if (node.third == '#') {
					infected.add(Pair(x, y))
				}
			}
		}
		return infected
	}
}