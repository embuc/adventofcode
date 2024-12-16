package y2024

import Task
import java.util.*
import kotlin.math.abs

/*--- Day 16: Reindeer Maze ---*/
class Task16(val input: List<String>) : Task {

	private val debugPrint = false

	override fun a(): Any {
		val grid = input.map { it.toCharArray() }
		return findAllShortestPaths(grid).first
	}

	data class PathTile(val cost: Int, val x: Int, val y: Int, val direction: Int, val path: List<Pair<Int, Int>>)

	private fun printPath(grid: List<CharArray>, path: MutableSet<Pair<Int, Int>>, start: Pair<Int, Int>, end: Pair<Int, Int>) {
		val gridWithPath = grid.map { it.clone() }
		for (p in path) {
			val (x, y) = p
			gridWithPath[x][y] = 'P'
		}
		gridWithPath[start.first][start.second] = 'S'
		gridWithPath[end.first][end.second] = 'E'

		println("Grid with shortest path:")
		gridWithPath.forEach { println(it.concatToString()) }
	}

	private fun findAllShortestPaths(grid: List<CharArray>): Pair<Int, Int> {
		val rows = grid.size
		val cols = grid[0].size
		val directions = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0)) // right, down, left, up
		val turnCost = 1000
		val moveCost = 1

		// Find start ('S') and end ('E') positions in the grid
		val start = grid.mapIndexed { i, row -> Pair(i, row.indexOf('S')) }.first { it.second != -1 }
		val end = grid.mapIndexed { i, row -> Pair(i, row.indexOf('E')) }.first { it.second != -1 }

		// Priority queue for processing nodes, always prioritizing the lowest cost
		val priorityQueue = PriorityQueue<PathTile>(compareBy { it.cost })

		val startDirection = 0
		// Start with specified initial direction
		priorityQueue.add(PathTile(0, start.first, start.second, startDirection, listOf(start)))

		// Map to track the minimum cost for each cell in each direction
		val visited = mutableMapOf<Triple<Int, Int, Int>, Int>()

		val allPaths = mutableListOf<List<Pair<Int, Int>>>()
		var shortestPathCost = -1

		// BFS/Dijkstra loop
		while (priorityQueue.isNotEmpty()) {
			// Get the current tile with the lowest cost
			val (currentCost, x, y, currentDirection, currentPath) = priorityQueue.poll()

			// Check if we've already visited this cell in this direction with a lower cost
			val state = Triple(x, y, currentDirection)
			if (visited.getOrDefault(state, Int.MAX_VALUE) < currentCost) {
				continue
			}
			visited[state] = currentCost

			// If we've reached the end position 'E', add the current path to the list
			if (grid[x][y] == 'E') {
				if (shortestPathCost == -1) { //this will only run the first time
					shortestPathCost = currentCost
					allPaths.add(currentPath)
				} else if (currentCost == shortestPathCost) {
					allPaths.add(currentPath)
				} else if (currentCost > shortestPathCost) {
					continue; // We've found shorter paths already
				}
				continue;
			}

			// Explore all neighbors
			for (i in directions.indices) {
				val (dx, dy) = directions[i]
				val neighborX = x + dx
				val neighborY = y + dy

				// Check if the neighbor is within bounds and not a wall
				if (neighborX in 0 until rows && neighborY in 0 until cols && grid[neighborX][neighborY] != '#') {
					// Calculate the new cost for moving to this neighbor
					val newCost = currentCost + moveCost + calculateTurnCost(currentDirection, i, turnCost)

					// Create a new path with this neighbor and add to priority queue
					val newPath = currentPath + Pair(neighborX, neighborY)
					priorityQueue.add(PathTile(newCost, neighborX, neighborY, i, newPath))
				}
			}
		}

		val uniqueTiles = mutableSetOf<Pair<Int, Int>>()
		allPaths.forEach { path ->
			uniqueTiles.addAll(path)
		}

		if (debugPrint) printPath(grid, uniqueTiles, start, end)
		return Pair(shortestPathCost, uniqueTiles.size)
	}

	fun calculateTurnCost(fromDirection: Int, toDirection: Int, turnCost: Int): Int {
		if (fromDirection == -1) { // Start case
			return 0
		}
		val turnDiff = abs(fromDirection - toDirection)
		return when (turnDiff) {
			0 -> 0
			1, 3 -> turnCost
			2 -> turnCost * 2
			else -> 0
		}
	}

	override fun b(): Any {
		val grid = input.map { it.toCharArray() }
		return findAllShortestPaths(grid).second
	}
}