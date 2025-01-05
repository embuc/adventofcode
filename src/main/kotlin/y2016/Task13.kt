package y2016

import Task
import templates.MinimalBFS

//--- Day 13: A Maze of Twisty Little Cubicles ---
class Task13(val input: Int, private val targetX: Int, private val targetY: Int) : Task {

	override fun a(): Any {
		val initX = 42
		val initY = 42
		val grid = Array(initX) { CharArray(initY) }
		for (x in 0 until initX) {
			for (y in 0 until initY) {
				val sum = x * x + 3 * x + 2 * x * y + y + y * y + input
				val binary = Integer.toBinaryString(sum)
				val count = binary.count { it == '1' }
				grid[y][x] = if (count % 2 == 0) '.' else '#'
			}
		}
		grid[targetX][targetY] = 'E'
		grid[1][1] = 'S'
		val findShortestPath = MinimalBFS.findShortestPath(grid)
		return findShortestPath.size - 1
	}

	override fun b(): Any {
		val initX = 50
		val initY = 50
		val grid = Array(initX) { CharArray(initY) }
		for (x in 0 until initX) {
			for (y in 0 until initY) {
				val sum = x * x + 3 * x + 2 * x * y + y + y * y + input
				val binary = Integer.toBinaryString(sum)
				val count = binary.count { it == '1' }
				grid[y][x] = if (count % 2 == 0) '.' else '#'
			}
		}
		return MinimalBFS.findReachableWithinSteps(1,1,50,grid).size
	}
}