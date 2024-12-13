package y2024

import Task
import utils.printGrid
import utils.toGrid

/* --- Day 10: Hoof It --- */
class Task10(val input: List<String>) : Task {

	override fun a(): Any {
		val grid = Array(input.size) { i -> input[i].map { it.toChar() }.toTypedArray() }
		val paths = findAllPaths(grid)
		val filtered = prunePaths(paths)
//		visualizePaths(grid, paths)
		return  filtered.size
	}

	fun prunePaths(paths: List<List<Point>>): List<List<Point>> {
		val uniquePaths = mutableListOf<List<Point>>()
		val usedStartEndPairs = mutableSetOf<Pair<Point, Point>>() // Track unique start-to-end pairs

		for (path in paths) {
			if (path.isNotEmpty()) {
				val start = path.first()
				val end = path.last()
				val pair = Pair(start, end)

				// Only add the path if the start-to-end pair is unique
				if (pair !in usedStartEndPairs) {
					uniquePaths.add(path)
					usedStartEndPairs.add(pair)
				}
			}
		}

		return uniquePaths
	}

	private fun visualizePaths(grid: Array<Array<Char>>, paths: List<List<Point>>) {
		val visualGrid = grid.map { it.copyOf() }.toTypedArray()
		for (path in paths) {
			for (point in path) {
				visualGrid[point.x][point.y] = 'X'
			}
		}
		printGrid(visualGrid)
	}

	fun dfsAllPaths(
		grid: Array<Array<Char>>,
		cur: Point,
		nextNumber: Char,
		path: MutableList<Point>,
		seen: MutableSet<Point>,
		allPaths: MutableList<List<Point>>
	) {
		// Base case: if we reach '9', save the current path
		if (grid[cur.x][cur.y] == '9') {
			path.add(cur) // Add the final point to the path
			allPaths.add(ArrayList(path)) // Save a copy of the current path
			path.removeAt(path.size - 1) // Backtrack
			return
		}

		// Add the current cell to the path and mark it as seen
		path.add(cur)
		seen.add(cur)

		// Explore all possible neighbors (up, down, left, right)
		val directions = listOf(
			Pair(-1, 0), // Up
			Pair(1, 0),  // Down
			Pair(0, -1), // Left
			Pair(0, 1)   // Right
		)
		for ((dx, dy) in directions) {
			val nx = cur.x + dx
			val ny = cur.y + dy

			// Check if the neighbor is within bounds
			if (nx in grid.indices && ny in grid[0].indices) {
				val neighbor = Point(nx, ny)

				// Check if the neighbor matches the next number in the sequence and hasn't been visited
				if (grid[nx][ny] == nextNumber && neighbor !in seen) {
					// Recurse to explore this neighbor
					dfsAllPaths(grid, neighbor, nextNumber + 1, path, seen, allPaths)
				}
			}
		}

		// Backtrack: remove the current cell from the path and seen set
		path.removeAt(path.size - 1)
		seen.remove(cur)
	}

	fun findAllPaths(grid: Array<Array<Char>>): List<List<Point>> {
		val allPaths = mutableListOf<List<Point>>() // List of all valid paths

		// Find all starting points ('1')
		for (i in grid.indices) {
			for (j in grid[0].indices) {
				if (grid[i][j] == '0') {
					val start = Point(i, j)
					val path = mutableListOf<Point>()
					val seen = mutableSetOf<Point>()

					// Perform DFS from this starting point
					dfsAllPaths(grid, start, '1', path, seen, allPaths)
				}
			}
		}

		return allPaths
	}

	private fun printGrid(grid: Array<Array<Char>>) {
		for (i in grid.indices) {
			for (j in grid[i].indices) {
				print(grid[i][j])
			}
			println()
		}
	}

	data class Point(val x: Int, val y: Int)

	override fun b(): Any {
		val grid = Array(input.size) { i -> input[i].map { it.toChar() }.toTypedArray() }
		val paths = findAllPaths(grid)
//		visualizePaths(grid, paths)
		return  paths.size
	}
}