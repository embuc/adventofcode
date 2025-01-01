package y2023

import Task
import utils.readInputAsString
import java.util.*

// --- Day 17: Clumsy Crucible ---
object Task17:Task {

	override fun a(): Any {
		val grid = readInputAsString("~/git/aoc-inputs/2023/2023_17.txt").lines().map { s -> s.map { it.digitToInt() }.toList() }.toList()
		return findMinHeatLoss(grid, minSteps = 0, maxSteps = 3)
	}

	override fun b(): Any {
		val grid = readInputAsString("~/git/aoc-inputs/2023/2023_17.txt").lines().map { s -> s.map { it.digitToInt() }.toList() }.toList()

		return findMinHeatLoss(grid, minSteps = 4, maxSteps = 10)
	}

	// Direction represented as (row, col) changes
	data class Direction(val row: Int, val col: Int) {
		companion object {
			val RIGHT = Direction(0, 1)
			val DOWN = Direction(1, 0)
			val LEFT = Direction(0, -1)
			val UP = Direction(-1, 0)

			val ALL = listOf(RIGHT, DOWN, LEFT, UP)
		}

		fun turnLeft(): Direction = ALL[(ALL.indexOf(this) + 1) % 4]
		fun turnRight(): Direction = ALL[(ALL.indexOf(this) + 3) % 4]
	}

	// State for the priority queue
	data class State(
		val row: Int,
		val col: Int,
		val direction: Direction,
		val steps: Int,
		val heatLoss: Int
	): Comparable<State> {
		override fun compareTo(other: State): Int = heatLoss.compareTo(other.heatLoss)
	}

	fun findMinHeatLoss(grid: List<List<Int>>, minSteps: Int, maxSteps: Int): Int {
		val rows = grid.size
		val cols = grid[0].size

		// Priority queue for Dijkstra's algorithm
		val queue = PriorityQueue<State>().apply {
			// Start by going right or down
			add(State(0, 0, Direction.RIGHT, 0, 0))
			add(State(0, 0, Direction.DOWN, 0, 0))
		}

		// Keep track of visited states
		val seen = mutableSetOf<Triple<Pair<Int, Int>, Direction, Int>>()

		while (queue.isNotEmpty()) {
			val current = queue.poll()
			val pos = current.row to current.col

			// Create state identifier for seen check
			val stateId = Triple(pos, current.direction, current.steps)
			if (stateId in seen) continue
			seen.add(stateId)

			// Check if we've reached the end with minimum steps requirement
			if (current.row == rows - 1 && current.col == cols - 1 && current.steps >= minSteps) {
				return current.heatLoss
			}

			// Determine possible next moves
			val possibleMoves = mutableListOf<Direction>()

			// Can continue straight if under max steps
			if (current.steps < maxSteps) {
				possibleMoves.add(current.direction)
			}

			// Can turn if we've met minimum steps or just starting
			if (current.steps >= minSteps || (current.row == 0 && current.col == 0)) {
				possibleMoves.add(current.direction.turnLeft())
				possibleMoves.add(current.direction.turnRight())
			}

			// Try each possible move
			for (newDir in possibleMoves) {
				val newRow = current.row + newDir.row
				val newCol = current.col + newDir.col

				// Check if new position is valid
				if (newRow in 0 until rows && newCol in 0 until cols) {
					val newSteps = if (newDir == current.direction) current.steps + 1 else 1
					val newHeat = current.heatLoss + grid[newRow][newCol]

					queue.add(State(newRow, newCol, newDir, newSteps, newHeat))
				}
			}
		}

		error("No path found")
	}
}