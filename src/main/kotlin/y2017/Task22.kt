package y2017

import Task
import utils.Point2D
import utils.toGrid

//--- Day 22: Sporifica Virus ---
class Task22(val input: List<String>) : Task {
	override fun a(): Any {
		val grid = toGrid(input)
		val center = Pair(grid.size / 2, grid[0].size / 2)
		val point = Point2D(center.first, center.second, Point2D.Direction.NORTH)
		val infected = mutableSetOf<Pair<Int, Int>>()
		var countNewInfections = 0
		grid.mapIndexed { x, row ->
			row.mapIndexed { y, node ->
				if (node.third == '#') {
					infected.add(Pair(x, y))
				}
			}
		}
		repeat(10000) {
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
		val grid = toGrid(input)
		val center = Pair(grid.size / 2, grid[0].size / 2)
		val point = Point2D(center.first, center.second, Point2D.Direction.NORTH)
		val infected = mutableSetOf<Pair<Int, Int>>()
		val weakened = mutableSetOf<Pair<Int, Int>>()
		val flagged = mutableSetOf<Pair<Int, Int>>()
		var countNewInfections = 0
		grid.mapIndexed { x, row ->
			row.mapIndexed { y, node ->
				if (node.third == '#') {
					infected.add(Pair(x, y))
				}
			}
		}
		repeat(10_000_000) {
			if (point.toPair() in infected) {
				point.turn(Point2D.Turn.RIGHT)
				infected.remove(point.toPair())
				flagged.add(point.toPair())
			} else if (point.toPair() in weakened) {
				weakened.remove(point.toPair())
				infected.add(point.toPair())
				countNewInfections++
			} else if (point.toPair() in flagged) {
				point.turn(Point2D.Turn.AROUND)
				flagged.remove(point.toPair())
			} else {
				point.turn(Point2D.Turn.LEFT)
				weakened.add(point.toPair())
			}
			point.move(1)
		}
		return countNewInfections
	}
}