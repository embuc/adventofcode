package y2024

import Task
import other.MinimalBFS
import other.MinimalBFS.findChar
import java.util.*


/*--- Day 20: Race Condition ---*/
class Task20(val input: List<String>, val limit:Int) : Task {

	override fun a(): Any {
		val grid2 = input.map { it.toCharArray() }.toTypedArray()
		val grid = input
		val start = findChar(grid2, 'S').let { Point(it[0], it[1]) }
		val end = findChar(grid2, 'E').let { Point(it[0], it[1]) }

		val basePath = findBasePath(grid, start, end)

		val cheatWalls = findCheatWalls(grid, basePath)
//		println("Number of cheat walls: ${cheatWalls.size}")
//		println("Cheat walls: $cheatWalls")

		val result = calculateCheats(grid, basePath, cheatWalls, limit)
//		println("Number of valid cheats that save at least $limit picoseconds: $result")
		return result
	}

	data class Point(val x: Int, val y: Int) {
		fun neighbors(): List<Point> = listOf(
			Point(x - 1, y),
			Point(x + 1, y),
			Point(x, y - 1),
			Point(x, y + 1)
		)
	}

	fun findBasePath(grid: List<String>, start: Point, end: Point): List<Point> {
		val rows = grid.size
		val cols = grid[0].length
		val visited = mutableSetOf<Point>()
		val path = mutableListOf<Point>()
		val stack = mutableListOf(start)

		while (stack.isNotEmpty()) {
			val current = stack.removeAt(stack.size - 1)
			if (current in visited) continue
			visited.add(current)
			path.add(current)

			if (current == end) break

			for (neighbor in current.neighbors()) {
				if (neighbor.x in 0 until rows && neighbor.y in 0 until cols &&
					grid[neighbor.x][neighbor.y] != '#' && neighbor !in visited
				) {
					stack.add(neighbor)
				}
			}
		}

		return path
	}

	fun findCheatWalls(grid: List<String>, path: List<Point>): List<Point> {
		val pathSet = path.toSet()
		val walls = mutableListOf<Point>()

		for (point in path) {
			for (neighbor in point.neighbors()) {
				if (grid[neighbor.x][neighbor.y] == '#') {
					val wall = neighbor
					val wallNeighbors = wall.neighbors().filter { it in pathSet }
					if (wallNeighbors.size >= 2) {  // Allow walls with at least two path neighbors
						walls.add(wall)
					}
				}
			}
		}

		return walls
	}

	fun calculateCheats(grid: List<String>, path: List<Point>, cheatWalls: List<Point>, limit: Int): Int {
		val pathIndex = path.withIndex().associate { it.value to it.index }
		val processedWalls = mutableSetOf<Point>()  // Track processed walls
		var validCheatCount = 0

		for (wall in cheatWalls) {
			if (wall in processedWalls) continue  // Skip if the wall has already been processed
			processedWalls.add(wall)  // Mark the wall as processed

			val wallNeighbors = wall.neighbors().filter { it in pathIndex }

			if (wallNeighbors.size < 2) continue  // Ensure the wall connects at least two path points

			val (pointA, pointB) = wallNeighbors
			val indexA = pathIndex[pointA]!!
			val indexB = pathIndex[pointB]!!
			val segmentLength = kotlin.math.abs(indexA - indexB)

			// Crossing the wall takes 2 steps (1 to cross, 1 to rejoin)
			if (segmentLength > 2) {
				val timeSaved = segmentLength - 2  // Time saved by crossing the wall
				if (timeSaved >= limit) {
//					println("Valid cheat: $wall connects $pointA and $pointB, saves $timeSaved")
					validCheatCount++
				}
			}
		}

		return validCheatCount
	}

	override fun b(): Any {
		return 2
	}

}