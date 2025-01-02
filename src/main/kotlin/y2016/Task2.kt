package y2016

import Task
import utils.Point2D

//--- Day 2: Bathroom Security ---
class Task2(val input: String) : Task {

	override fun a(): Any {
		val instructions = input.lines().map { it.trim() }
		val grid = arrayOf(
			arrayOf(1, 2, 3),
			arrayOf(4, 5, 6),
			arrayOf(7, 8, 9)
		)
		val builder = StringBuilder()
		val point = Point2D(1, 1)// start at 5
		for (i in instructions) {
			for (j in i) {
				when (j) {
					'U' -> point.face(Point2D.Direction.NORTH)
					'D' -> point.face(Point2D.Direction.SOUTH)
					'L' -> point.face(Point2D.Direction.WEST)
					'R' -> point.face(Point2D.Direction.EAST)
				}
				point.move(1)
				if (!point.isInside(grid.size, grid[0].size)) {
					point.move(-1)
				}
			}
			builder.append(grid[point.x][point.y])
		}
		return builder.toString()
	}

	override fun b(): Any {
		val instructions = input.lines().map { it.trim() }
		val grid = arrayOf(
			arrayOf('#', '#', '1', '#', '#'),
			arrayOf('#', '2', '3', '4', '#'),
			arrayOf('5', '6', '7', '8', '9'),
			arrayOf('#', 'A', 'B', 'C', '#'),
			arrayOf('#', '#', 'D', '#', '#')
		)
		val builder = StringBuilder()
		val point = Point2D(2, 0)// start at 5
		for (i in instructions) {
			for (j in i) {
				when (j) {
					'U' -> point.face(Point2D.Direction.NORTH)
					'D' -> point.face(Point2D.Direction.SOUTH)
					'L' -> point.face(Point2D.Direction.WEST)
					'R' -> point.face(Point2D.Direction.EAST)
				}
				point.move(1)
				if (!point.isInside(grid.size, grid[0].size) || grid[point.x][point.y] == '#') {
					point.move(-1)
				}
			}
			builder.append(grid[point.x][point.y])
		}
		return builder.toString()
	}
}