package y2017

import Task
import utils.Point2D

//--- Day 3: Spiral Memory ---
class Task3(val input: Int) : Task {

	override fun a(): Any {
		var level = 1
		var odd = 1
		var oddSquareCorner = 0
		while (oddSquareCorner < input * 2) {
			oddSquareCorner = odd * odd
			if (oddSquareCorner >= input) {
				val diff = oddSquareCorner - input
				val half = odd / 2 // this is right 'over' 1
				val halfDiff = diff % half // compensate 'around' towards the edges
				val steps = odd - 1 - halfDiff
				return steps
			}
			level++
			odd += 2
		}
		return 0
	}

	override fun b(): Any {
		val grid = Array(200) { IntArray(200) }
		var curr = 0
		val point = Point2D(100, 100, Point2D.Direction.SOUTH)
		grid[point.x][point.y] = 1
		while (curr <= input) {
			// move in spiral form adding the sum of all neighbors
			// check if can move left, it not move 1 step forward
			val leftNeighbour = point.getRelativeLeftNeighbor().toXY()
			if (grid[leftNeighbour.x][leftNeighbour.y] == 0) {
				point.turn(Point2D.Turn.LEFT)
			}
			point.move(1)
			curr = point.getAllNeighbors().sumOf { grid[it.x][it.y] }
			grid[point.x][point.y] = curr
			if (curr > input) {
				return curr
			}
		}
		return 0
	}
}
