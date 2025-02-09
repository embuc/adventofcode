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
		val start = findChar(grid, 'S')
		val end = findChar(grid, 'E')
		val path = findShortestPath(grid, start, end, ::canMoveThere)
		return path.size - 1
	}

	override fun b(): Any {
		val grid = toCharGrid(input)
		val start = findChar(grid, 'E')
		val path = findShortestPath(grid, start, 'a', ::canMoveThereReverse)
		return path.size - 1
	}

	private fun findShortestPath(
		grid: Array<CharArray>,
		start: IntArray,
		end: Any, // Can be IntArray (for coordinates) or Char (for endpoint character)
		moveCheck: (Char, Char) -> Boolean
	): Array<IntArray> {
		val rows = grid.size
		val cols = grid[0].size

		val queue: Queue<MinimalBFS.Node> = LinkedList<MinimalBFS.Node>().apply { add(MinimalBFS.Node(start[0], start[1])) }
		val visited = Array(rows) { BooleanArray(cols) }.apply { this[start[0]][start[1]] = true }
		var endNode: MinimalBFS.Node? = null

		while (queue.isNotEmpty()) {
			val current = queue.poll()

			// Goal Check
			when (end) {
				is IntArray -> if (current.x == end[0] && current.y == end[1]) endNode = current
				is Char -> if (grid[current.x][current.y] == end) endNode = current
			}
			if (endNode != null) break

			// Explore neighbors
			for ((dx, dy) in directions) {
				val newX = current.x + dx
				val newY = current.y + dy

				if (newX in 0 until rows && newY in 0 until cols &&
					moveCheck(grid[current.x][current.y], grid[newX][newY]) &&
					!visited[newX][newY]
				) {
					queue.add(MinimalBFS.Node(newX, newY, current))
					visited[newX][newY] = true
				}
			}
		}

		return endNode?.reconstructPath() ?: emptyArray()
	}

	private fun MinimalBFS.Node.reconstructPath(): Array<IntArray> {
		val path = LinkedList<IntArray>()
		var current: MinimalBFS.Node? = this
		while (current != null) {
			path.addFirst(intArrayOf(current.x, current.y))
			current = current.parent
		}
		return path.toTypedArray()
	}

	private fun canMoveThere(ch: Char, newCh: Char): Boolean {
		return when {
			ch == newCh -> true
			ch == 'S' -> newCh == 'a'
			newCh == 'E' -> ch == 'z'
			ch.code > newCh.code -> true
			else -> newCh.code - ch.code == 1
		}
	}

	private fun canMoveThereReverse(ch: Char, newCh: Char): Boolean {
		return when {
			ch == newCh -> true
			ch == 'E' -> newCh == 'z'
			ch.code < newCh.code -> true
			else -> newCh.code - ch.code == -1
		}
	}

	companion object {
		private val directions = arrayOf(0 to 1, 1 to 0, 0 to -1, -1 to 0)
	}
}