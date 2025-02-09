package y2022

import Task
import templates.MinimalBFS
import templates.MinimalBFS.findChar
import utils.toCharGrid
import java.util.*

//--- Day 12: Hill Climbing Algorithm ---
class Task12(val input: List<String>) : Task {

	override fun a(): Any {
		val grid = toCharGrid(input)
			Array<CharArray>(input.size) { CharArray(input[0].length) }
		val start = findChar(grid, 'S')
		val end = findChar(grid, 'E')
		val path = findShortestPath(grid, start, end)
		return path.size - 1
	}

	override fun b(): Any {
		val grid = toCharGrid(input)
		Array<CharArray>(input.size) { CharArray(input[0].length) }
		val start = findChar(grid, 'E')
		val path = findShortestPathToAnyEnd(grid, start, 'a')
		return path.size - 1
	}

	fun findShortestPath(gridIncoming: Array<CharArray>, start: IntArray, end: IntArray): Array<IntArray> {
		val grid = Array<CharArray>(gridIncoming.size) { CharArray(gridIncoming[0].size) }
		System.arraycopy(gridIncoming, 0, grid, 0, gridIncoming.size)
		val rows = grid.size
		val cols = grid[0].size

		val queue: Queue<MinimalBFS.Node> = LinkedList<MinimalBFS.Node>()
		// Adding initial node to the queue
		queue.add(MinimalBFS.Node(start[0], start[1]))
		val visited = Array<BooleanArray>(rows) { BooleanArray(cols) }
		visited[start[0]][start[1]] = true
		var endNode: MinimalBFS.Node? = null

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
				// Check for valid positions and if we can move there
				val insideBounds = newX >= 0 && newX < rows && newY >= 0 && newY < cols
				val validNeighbor = insideBounds && canMoveThere(grid[current.x][current.y], grid[newX][newY])
				if (validNeighbor && !visited[newX][newY]) {
					// Enqueue neighbor
					queue.add(MinimalBFS.Node(newX, newY, current))
					visited[newX][newY] = true
				}
			}
		}

		if (endNode == null) {
			return emptyArray() // No path found
		}
		// Reconstruct path
		val path = LinkedList<IntArray>()
		var current: MinimalBFS.Node? = endNode
		while (current != null) {
			path.addFirst(intArrayOf(current.x, current.y)) // Add current to front
			current = current.parent // Get its parent
		}

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
			return newCh == 'a'
		}
		if(newCh == 'E') {
			return ch == 'z'
		}
		//allow going down freely
		if(ch.code > newCh.code) {
			return true
		}
		return newCh.code - ch.code == 1
	}
	private fun canMoveThereB(ch: Char, newCh: Char): Boolean {
		if(ch == newCh) {
			return true
		}
		if(ch == 'E'){
			return newCh == 'z'
		}
		//allow going 'up' freely - not really as we are going backwards
		if(ch.code < newCh.code) {
			return true
		}
		return newCh.code - ch.code == -1
	}

	fun findShortestPathToAnyEnd(gridIncoming: Array<CharArray>, start: IntArray, endChar: Char): Array<IntArray> {
		val grid = Array<CharArray>(gridIncoming.size) { CharArray(gridIncoming[0].size) }
		System.arraycopy(gridIncoming, 0, grid, 0, gridIncoming.size)
		val rows = grid.size
		val cols = grid[0].size

		val queue: Queue<MinimalBFS.Node> = LinkedList<MinimalBFS.Node>()
		queue.add(MinimalBFS.Node(start[0], start[1]))
		val visited = Array<BooleanArray>(rows) { BooleanArray(cols) }
		visited[start[0]][start[1]] = true
		var endNode: MinimalBFS.Node? = null

		while (!queue.isEmpty()) {
			val current = queue.poll()

			// Goal Check: Check if current node is the endpoint character
			if (grid[current.x][current.y] == endChar) {
				endNode = current // We have reached one of the ends
				break
			}

			// Explore neighbors (up, down, left, right)
			val directions = arrayOf<IntArray>(intArrayOf(0, 1), intArrayOf(1, 0), intArrayOf(0, -1), intArrayOf(-1, 0))
			for (direction in directions) {
				val newX = current.x + direction[0]
				val newY = current.y + direction[1]
				// Check for valid positions and if we can move there
				val insideBounds = newX >= 0 && newX < rows && newY >= 0 && newY < cols
				val validNeighbor = insideBounds && canMoveThereB(grid[current.x][current.y], grid[newX][newY])
				if (validNeighbor && !visited[newX][newY]) {
					// Enqueue neighbor
					queue.add(MinimalBFS.Node(newX, newY, current))
					visited[newX][newY] = true
				}
			}
		}

		if (endNode == null) {
			return emptyArray() // No path found to any end point
		}

		// Reconstruct path
		val path = LinkedList<IntArray>()
		var current: MinimalBFS.Node? = endNode
		while (current != null) {
			path.addFirst(intArrayOf(current.x, current.y)) // Add current to front
			current = current.parent // Get its parent
		}

		val shortestPath = Array<IntArray>(path.size) { IntArray(2) }
		for (i in path.indices) {
			shortestPath[i] = path.get(i)
		}

		return shortestPath
	}
}