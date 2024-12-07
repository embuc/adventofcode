package y2024

import Task
import com.sun.org.apache.xerces.internal.impl.xs.opti.SchemaDOM.traverse

/* --- Day 6: Guard Gallivant --- */
class Task6(val input: List<String>) : Task {

	private val grid = toTileGrid(input)

	// 0 = up, 1 = right, 2 = down, 3 = left
	override fun a(): Any {
//		println("Grid: ${grid.size} by ${grid[0].size}")
		val startPoint = findStart(grid)
		val startTile = grid[startPoint.first][startPoint.second]
		startTile.markVisited(0) // Mark the starting point as visited from the initial direction (up)
		val startState = TraverseState(1, startTile, 0)
		return traverse(startState).sum
	}

	/**
	 * Represents a single tile in the grid.
	 * Tracks whether it has been visited from any of the four directions (up, right, down, left).
	 */
	private data class Tile(
		val i: Int,
		val j: Int,
		val type: Char, // ('.', '#', '^')
		val visited: BooleanArray = BooleanArray(4), // Tracks visits from directions 0 (up), 1 (right), 2 (down), 3 (left)
		var hasBeenVisited: Boolean = false
	) {
		fun isVisitedFrom(direction: Int): Boolean = visited[direction]
		fun markVisited(direction: Int) {
			visited[direction] = true
			hasBeenVisited = true
		}

		override fun toString(): String = "Tile(i=$i, j=$j, type=$type, visited=${visited.contentToString()}, hasBeenVisited=$hasBeenVisited)"
	}

	private data class TraverseState (
		val sum: Int,
		val currentTile: Tile,
		val direction: Int // Current direction (0 = up, 1 = right, 2 = down, 3 = left)
	)

	private fun traverse(initialState: TraverseState): TraverseState {
		var (sum, currentTile, direction) = initialState

		while (true) {
			val (i, j, type) = currentTile
//			println("Current state: $currentTile, sum: $sum, direction: $direction")

			if (exitingGrid(i, j, direction)) {
//				println("Exiting the grid at $currentTile in direction $direction. Terminating.")
				return TraverseState(sum, currentTile, direction)
			}

			when (direction) {
				0 -> if (i > 0 && grid[i - 1][j].type != '#') { // Move up
					val nextTile = grid[i - 1][j]
					sum = if (!nextTile.hasBeenVisited) sum + 1 else sum
					nextTile.markVisited(0)
					currentTile = nextTile
					continue
				}
				1 -> if (j + 1 < grid[i].size && grid[i][j + 1].type != '#') { // Move right
					val nextTile = grid[i][j + 1]
					sum = if (!nextTile.hasBeenVisited) sum + 1 else sum
					nextTile.markVisited(1)
					currentTile = nextTile
					continue
				}
				2 -> if (i + 1 < grid.size && grid[i + 1][j].type != '#') { // Move down
					val nextTile = grid[i + 1][j]
					sum = if (!nextTile.hasBeenVisited) sum + 1 else sum
					nextTile.markVisited(2)
					currentTile = nextTile
					continue
				}
				3 -> if (j > 0 && grid[i][j - 1].type != '#') { // Move left
					val nextTile = grid[i][j - 1]
					sum = if (!nextTile.hasBeenVisited) sum + 1 else sum
					nextTile.markVisited(3)
					currentTile = nextTile
					continue
				}
			}

			// If the move in the current direction is invalid, turn 90 degrees right
			direction = (direction + 1) % 4
//			println("Turning right to direction $direction")
		}
	}

	private fun exitingGrid(i: Int, j: Int, direction: Int): Boolean {
		// direction + curr pos tells us if we are at the edge of the grid
		return when (direction) {
			0 -> i == 0
			1 -> j == grid[0].size - 1
			2 -> i == grid.size - 1
			3 -> j == 0
			else -> false
		}
	}

	private fun findStart(grid: Array<Array<Tile>>): Pair<Int, Int> {
		for (i in grid.indices) {
			for (j in grid[i].indices) {
				if (grid[i][j].type == '^') {
					return Pair(i, j)
				}
			}
		}
		throw IllegalArgumentException("No starting point ('^') found in the grid.")
	}

	private fun toTileGrid(input: List<String>): Array<Array<Tile>> {
		return Array(input.size) { i ->
			Array(input[i].length) { j ->
				Tile(i, j, input[i][j])
			}
		}
	}

	override fun b(): Any {
		return 0
	}
}
