package y2024

import Task
import utils.printGrid
import java.util.*

/*--- Day 16: Reindeer Maze ---*/
class Task16(val input:List<String>):Task {

	override fun a(): Any {
		val grid = input.map { it.toCharArray() }
//		printGrid(grid)
		return findShortestPathWithTurnCosts(grid, 0).first
	}

	data class Tile(val cost: Int, val x: Int, val y: Int, val direction: Int)

	fun findShortestPathWithTurnCosts(grid: List<CharArray>, startDirection: Int): Pair<Int, Int> {
		val rows = grid.size
		val cols = grid[0].size
		val directions = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0)) // right, down, left, up
		val turnCost = 1000
		val moveCost = 1

		// Find start ('S') and end ('E') positions in the grid
		val start = grid.mapIndexed { i, row -> Pair(i, row.indexOf('S')) }.first { it.second != -1 }
		val end = grid.mapIndexed { i, row -> Pair(i, row.indexOf('E')) }.first { it.second != -1 }

		// Priority queue for processing nodes, always prioritizing the lowest cost
		val priorityQueue = PriorityQueue<Tile>(compareBy { it.cost })

		// Start with specified initial direction
		priorityQueue.add(Tile(0, start.first, start.second, startDirection))

		// Map to track the minimum cost for each cell in each direction
		val visited = mutableMapOf<Triple<Int, Int, Int>, Int>()

		// Map to reconstruct the path for visualization
		val parent = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>() // current -> parent

		// BFS/Dijkstra loop
		while (priorityQueue.isNotEmpty()) {
			// Get the current tile with the lowest cost
			val (currentCost, x, y, currentDirection) = priorityQueue.poll()

			// If we've reached the end position 'E', stop and reconstruct the path
			if (grid[x][y] == 'E') {
//				printPath(grid, parent, start, end) // Visualize the grid with the path
				return Pair(currentCost, (parent.size)/2 )
			}

			// Check if we've already visited this cell in this direction with a lower cost
			val state = Triple(x, y, currentDirection)
			if (visited.getOrDefault(state, Int.MAX_VALUE) <= currentCost) {
				continue
			}
			visited[state] = currentCost

			// Explore all neighbors
			for (i in directions.indices) {
				val (dx, dy) = directions[i]
				val neighborX = x + dx
				val neighborY = y + dy

				// Check if the neighbor is within bounds and not a wall
				if (neighborX in 0 until rows && neighborY in 0 until cols && grid[neighborX][neighborY] != '#') {
					// Calculate the new cost for moving to this neighbor
					val newCost = currentCost + moveCost + calculateTurnCost(currentDirection, i, turnCost)

					// Add the neighbor to the priority queue
					priorityQueue.add(Tile(newCost, neighborX, neighborY, i))

					// Record the parent for path reconstruction (only update if not already recorded)
					if (Pair(neighborX, neighborY) !in parent) {
						parent[Pair(neighborX, neighborY)] = Pair(x, y)
					}
				}
			}
		}

		// If we exhaust the queue and never reach 'E', return -1 (no path found)
		return -1 to -1
	}

	fun calculateTurnCost(fromDirection: Int, toDirection: Int, turnCost: Int): Int {
		if(fromDirection == -1) { // Start case
			return 0;
		}
		val turnDiff = Math.abs(fromDirection - toDirection)
		return when (turnDiff) {
			0 -> 0
			1, 3 -> turnCost
			2 -> turnCost * 2
			else -> 0
		}
	}

	fun printPath(grid: List<CharArray>, parent: Map<Pair<Int, Int>, Pair<Int, Int>>, start: Pair<Int, Int>, end: Pair<Int, Int>) {
		// Create a mutable copy of the grid to draw the path
		val gridWithPath = grid.map { it.clone() }

		// Trace the path from the end to the start
		var current = end
		while (current != start) {
			val (x, y) = current
			gridWithPath[x][y] = 'P' // Mark the path with 'P'
			current = parent[current] ?: break
		}
		gridWithPath[start.first][start.second] = 'S' // Ensure start is still marked
		gridWithPath[end.first][end.second] = 'E'    // Ensure end is still marked

		// Print the grid
		println("Grid with shortest path:")
		gridWithPath.forEach { println(it.concatToString()) }
	}

	override fun b(): Any {
		val grid = input.map { it.toCharArray() }
		printGrid(grid)
		return findShortestPathWithTurnCosts(grid, 0).second
	}
}