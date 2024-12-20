package y2024

import Task
import other.MinimalBFS.findChar


/*--- Day 20: Race Condition ---*/
class Task20(val input: List<String>, val limit: Int) : Task {

	// Base idea, find walls with at least 2 neighbors on the original path, then check what would shorting this wall
	// result in the path reduction
	override fun a(): Any {
		val grid = input.map { it.toCharArray() }.toTypedArray()
		val start = findChar(grid, 'S').let { Point(it[0], it[1]) }
		val end = findChar(grid, 'E').let { Point(it[0], it[1]) }

		val basePath = findBasePath(grid, start, end)

		val cheatWalls = findCheatWalls(grid, basePath)

		return calculateCheats(basePath, cheatWalls, limit)
	}

	data class Point(val x: Int, val y: Int) {
		fun neighbors(): List<Point> = listOf(
			Point(x - 1, y),
			Point(x + 1, y),
			Point(x, y - 1),
			Point(x, y + 1)
		)
	}

	fun findBasePath(grid: Array<CharArray>, start: Point, end: Point): List<Point> {
		val rows = grid.size
		val cols = grid[0].size
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

	fun findCheatWalls(grid: Array<CharArray>, path: List<Point>): List<Point> {
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

	fun calculateCheats(path: List<Point>, cheatWalls: List<Point>, limit: Int): Int {
		val pathIndex = path.withIndex().associate { it.value to it.index }
		val processedWalls = mutableSetOf<Point>()  // Track processed walls (duplicates not interesting)
		var validCheatCount = 0

		for (wall in cheatWalls) {
			if (wall in processedWalls) continue
			processedWalls.add(wall)

			val wallNeighbors = wall.neighbors().filter { it in pathIndex }


			val (pointA, pointB) = wallNeighbors
			val indexA = pathIndex[pointA]!!
			val indexB = pathIndex[pointB]!!
			val segmentLength = kotlin.math.abs(indexA - indexB)

			// Crossing the wall takes 2 steps (1 to cross, 1 to rejoin)
			if (segmentLength > 2) {
				val timeSaved = segmentLength - 2  // Time saved by crossing the wall
				if (timeSaved >= limit) {
					validCheatCount++
				}
			}
		}

		return validCheatCount
	}

	// The Basic idea here is to calculate the Manhattan distance (t) and path distance (dt) between each pair of points
	// on the original path, and if the Manhattan distance is less than or equal to the maxCheatLength and the
	// shortening of path is greater than limit we have a valid cheat. This solution works even for part I, but it's not
	// as efficient as the solution checking neighbors
	override fun b(): Any {
		val grid = input.map { it.toCharArray() }.toTypedArray()
		val start = findChar(grid, 'S').let { Point(it[0], it[1]) }
		val end = findChar(grid, 'E').let { Point(it[0], it[1]) }

		val basePath = findBasePath(grid, start, end)

		val maxCheatLength = 20
		return findMoveCheatsB(basePath, maxCheatLength, limit)
	}

	fun findMoveCheatsB(path: List<Point>, maxCheatLength: Int, limit: Int): Int {
		var validCheatCount = 0

		for (i in path.indices) {
			for (j in i + 1 until path.size) {
				val pointA = path[i]
				val pointB = path[j]

				// Calculate Manhattan distance (t) and path distance (dt)
				val t = kotlin.math.abs(pointB.x - pointA.x) + kotlin.math.abs(pointB.y - pointA.y)
				val dt = j - i

				if (t <= maxCheatLength && dt > t) {
					val timeSaved = dt - t + 1
					if (timeSaved >= limit) {
						validCheatCount++
					}
				}
			}
		}

		return validCheatCount
	}
}