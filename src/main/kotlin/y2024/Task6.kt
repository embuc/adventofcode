package y2024

import Task

/* --- Day 6: Guard Gallivant --- */
class Task6(val input: List<String>) : Task {

	private val grid = toTileGrid(input)

	// 0 = up, 1 = right, 2 = down, 3 = left
	override fun a(): Any {
		val startPoint = findStart(grid)
		val startTile = grid[startPoint.first][startPoint.second]
		startTile.markVisited(0) // Mark the starting point as visited from the initial direction (up)
		val startState = TraverseState(startTile, 0)
		return traverse(startState).size
	}

	override fun b(): Any {
		val startPoint = findStart(grid)
		val startTile = grid[startPoint.first][startPoint.second]
		startTile.markVisited(0)
		val startState = TraverseState(startTile, 0)
		val path = traverse(startState)
		resetGrid()
		var counter = 0

		for ((i, j) in path) {
			if (grid[i][j].type == '^') continue
			resetGrid()
			grid[i][j].type = '#'
			if (traverseForLoop(startState, grid)) counter++
			grid[i][j].type = '.'
		}
		return counter
	}

	private fun traverse(initialState: TraverseState): MutableSet<Pair<Int, Int>> {
		var (currentTile, direction) = initialState
		val path = mutableSetOf(Pair(currentTile.i, currentTile.j))

		while (true) {
			val (i, j) = currentTile
			if (exitingGrid(i, j, direction)) {
				path.add(Pair(i, j))
				return path
			}
			val nextTile = when (direction) {
				0 -> if (i > 0 && grid[i - 1][j].type != '#') grid[i - 1][j] else null // Move up
				1 -> if (j + 1 < grid[i].size && grid[i][j + 1].type != '#') grid[i][j + 1] else null // Move right
				2 -> if (i + 1 < grid.size && grid[i + 1][j].type != '#') grid[i + 1][j] else null // Move down
				3 -> if (j > 0 && grid[i][j - 1].type != '#') grid[i][j - 1] else null // Move left
				else -> null
			}
			if (nextTile != null) {
				nextTile.markVisited(direction)
				currentTile = nextTile
				path.add(Pair(i, j))
			} else {
				direction = (direction + 1) % 4
			}
		}
	}

	private fun traverseForLoop(initialState: TraverseState, grid: Array<Array<Tile>>): Boolean {
		var (currentTile, direction) = initialState
		while (true) {
			val (i, j) = currentTile
			if (exitingGrid(i, j, direction)) return false

			val nextTile = when (direction) {
				0 -> if (i > 0 && grid[i - 1][j].type != '#') grid[i - 1][j] else null // Move up
				1 -> if (j + 1 < grid[i].size && grid[i][j + 1].type != '#') grid[i][j + 1] else null // Move right
				2 -> if (i + 1 < grid.size && grid[i + 1][j].type != '#') grid[i + 1][j] else null // Move down
				3 -> if (j > 0 && grid[i][j - 1].type != '#') grid[i][j - 1] else null // Move left
				else -> null
			}

			if (nextTile != null) {
				if (nextTile.isVisitedFrom(direction)) return true
				nextTile.markVisited(direction)
				currentTile = nextTile
			} else {
				direction = (direction + 1) % 4
			}
		}
	}

	private data class Tile(
		val i: Int,
		val j: Int,
		var type: Char, // ('.', '#', '^')
		val visited: BooleanArray = BooleanArray(4), // Tracks visits from directions 0 (up), 1 (right), 2 (down), 3 (left)
		var hasBeenVisited: Boolean = false
	) {
		fun isVisitedFrom(direction: Int): Boolean = visited[direction]
		fun markVisited(direction: Int) {
			visited[direction] = true
			hasBeenVisited = true
		}

		override fun toString(): String =
			"Tile(i=$i, j=$j, type=$type, visited=${visited.contentToString()}, hasBeenVisited=$hasBeenVisited)"
	}

	private data class TraverseState(
		val currentTile: Tile,
		val direction: Int // Current direction (0 = up, 1 = right, 2 = down, 3 = left)
	)

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

	private fun resetGrid() {
		for (row in grid) {
			for (tile in row) {
				tile.visited.fill(false)
				tile.hasBeenVisited = false
			}
		}
	}

	fun printGrid() {
		for (row in grid) {
			for (tile in row) {
				print(tile.type)
			}
			println()
		}
	}
}
