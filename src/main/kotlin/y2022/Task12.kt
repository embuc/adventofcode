package y2022

import Task
import templates.MinimalBFS
import templates.MinimalBFS.findChar
import utils.printPathInGridColor
import utils.toCharGrid
import java.util.*

//--- Day 12: Hill Climbing Algorithm ---
class Task12(val input: List<String>) : Task {

	// ix map from 97-122 ascii to 0-25
	val alphabet =
		charArrayOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')

	override fun a(): Any {
		val grid = toCharGrid(input)
			Array<CharArray>(input.size) { CharArray(input[0].length) }
//		printGrid(grid)
		val start = findChar(grid, 'S')
		val end = findChar(grid, 'E')
		val path = findShortestPath(grid, start, end)
//		printPathInGridColor(grid, path)
		return path.size - 1
	}

	private fun neighbors(ch: Char): MutableList<Char> {
		val ix = ch - 'a'
		val neighbors = mutableListOf<Char>()
		if (ix > 0) {
			neighbors.add(alphabet[ix - 1])
		}
		if (ix < 25) {
			neighbors.add(alphabet[ix + 1])
		}
		return neighbors
	}

	override fun b(): Any {
		return 0
	}

	fun findShortestPath(gridIncoming: Array<CharArray>, start: IntArray, end: IntArray): Array<IntArray> {
		val grid = Array<CharArray>(gridIncoming.size) { CharArray(gridIncoming[0].size) }
		System.arraycopy(gridIncoming, 0, grid, 0, gridIncoming.size)
		val rows = grid.size
		val cols = grid[0].size

		// Queue for BFS
		val queue: Queue<MinimalBFS.Node> = LinkedList<MinimalBFS.Node>()
		// Adding initial node to the queue
		queue.add(MinimalBFS.Node(start[0], start[1]))
		// Map to check whether a location has already been visited or not
		val visited = Array<BooleanArray>(rows) { BooleanArray(cols) }
		visited[start[0]][start[1]] = true
		var endNode: MinimalBFS.Node? = null

		// BFS loop
		while (!queue.isEmpty()) {
			val current = queue.poll()

			// Goal Check
			if (current.x == end[0] && current.y == end[1]) {
				endNode = current // We have reached our end
				break
			}

			// Explore neighbors (up, down, left, right)
			val directions = arrayOf<IntArray>(intArrayOf(0, 1), intArrayOf(1, 0), intArrayOf(0, -1), intArrayOf(-1, 0))
			for (direction in directions) {
				val newX = current.x + direction[0]
				val newY = current.y + direction[1]
//				println("current $current grid ${grid[current.x][current.y]}")
				// Check for valid positions and if we can move there
				val insideBounds = newX >= 0 && newX < rows && newY >= 0 && newY < cols
				val validNeighbor = insideBounds && canMoveThere(grid[current.x][current.y], grid[newX][newY])
				if (validNeighbor && !visited[newX][newY]) {
//					println("Visiting $newX $newY  ${grid[newX][newY]}")
					// Enqueue neighbor
					queue.add(MinimalBFS.Node(newX, newY, current))
					visited[newX][newY] = true
				}
			}
		}

		// Reconstruct path
		if (endNode == null) {
			val intVisited = mutableListOf<IntArray>()

			for (i in visited.indices) {
				for (j in visited[i].indices) {
					if (visited[i][j]) {
						intVisited.add(intArrayOf(i, j))
					}
				}
			}
			printPathInGridColor(grid, intVisited.toTypedArray())
			return emptyArray() // No path found
		}
		// Reconstruct path
		val path = LinkedList<IntArray>()
		var current: MinimalBFS.Node? = endNode
		while (current != null) {
			path.addFirst(intArrayOf(current.x, current.y)) // Add current to front
			current = current.parent // Get its parent
		}

		// Convert to int[][]
		val shortestPath = Array<IntArray>(path.size) { IntArray(2) }
		for (i in path.indices) {
			shortestPath[i] = path.get(i)
		}

		return shortestPath
	}

	private fun canMoveThere(ch: Char, newCh: Char): Boolean {
		if(ch == newCh) {
			return true
		}
		if(ch == 'S'){
			if(newCh == 'a') {
				return true
			}
			else return false
		}
		if(newCh == 'E') {
			if(ch == 'z') {
				return true
			}
			else return false
		}
		//allow going down freely
		if(ch.code > newCh.code) {
			return true
		}
		val neighbors = neighbors(ch)
		return neighbors.contains(newCh)
	}
}