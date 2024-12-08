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
		val startState = TraverseState(1, startTile, 0)
		return traverse(startState).size
	}

	override fun b(): Any {
		val startPoint = findStart(grid)
		val startTile = grid[startPoint.first][startPoint.second]
		startTile.markVisited(0)
		val startState = TraverseState(0, startTile, 0)
		val path = traverse(startState)
		resetGrid()
		var counter = 0

		for ((i, j) in path) {
			if (grid[i][j].type == '^') continue

			grid[i][j].type = '#'
			resetGrid()
			if (traverseForLoop(startState, grid)) counter++
			grid[i][j].type = '.'
		}
		return counter
	}

	private fun traverse(initialState: TraverseState): MutableSet<Pair<Int, Int>> {
		var (sum, currentTile, direction) = initialState

		val path = mutableSetOf<Pair<Int, Int>>()
		path.add(Pair(currentTile.i, currentTile.j))

		while (true) {
			val (i, j, type) = currentTile
			if (exitingGrid(i, j, direction)) {
				path.add(Pair(i, j))
				return path
			}
			when (direction) {
				0 -> if (i > 0 && grid[i - 1][j].type != '#') { // Move up
					val nextTile = grid[i - 1][j]
					sum = if (!nextTile.hasBeenVisited) sum + 1 else sum
					nextTile.markVisited(0)
					currentTile = nextTile
					path.add(Pair(i, j))
					continue
				}
				1 -> if (j + 1 < grid[i].size && grid[i][j + 1].type != '#') { // Move right
					val nextTile = grid[i][j + 1]
					sum = if (!nextTile.hasBeenVisited) sum + 1 else sum
					nextTile.markVisited(1)
					currentTile = nextTile
					path.add(Pair(i, j))
					continue
				}
				2 -> if (i + 1 < grid.size && grid[i + 1][j].type != '#') { // Move down
					val nextTile = grid[i + 1][j]
					sum = if (!nextTile.hasBeenVisited) sum + 1 else sum
					nextTile.markVisited(2)
					currentTile = nextTile
					path.add(Pair(i, j))
					continue
				}
				3 -> if (j > 0 && grid[i][j - 1].type != '#') { // Move left
					val nextTile = grid[i][j - 1]
					sum = if (!nextTile.hasBeenVisited) sum + 1 else sum
					nextTile.markVisited(3)
					currentTile = nextTile
					path.add(Pair(i, j))
					continue
				}
			}
			// If the move in the current direction is invalid, turn 90 degrees right
			direction = (direction + 1) % 4
		}
	}


	private fun traverseForLoop(initialState: TraverseState, grid: Array<Array<Tile>>): Boolean {
		var (_, currentTile, direction) = initialState
		currentTile.markVisited(direction)
		while (true) {
			val (i, j, _) = currentTile
			if (exitingGrid(i, j, direction)) {
				return false
			}
			when (direction) {
				0 -> if (i > 0 && grid[i - 1][j].type != '#') { // Move up
					val nextTile = grid[i - 1][j]
					if (nextTile.isVisitedFrom(direction)) {
						return true
					}
					nextTile.markVisited(0)
					currentTile = nextTile
					continue
				}
				1 -> if (j + 1 < grid[i].size && grid[i][j + 1].type != '#') { // Move right
					val nextTile = grid[i][j + 1]
					if (nextTile.isVisitedFrom(direction)) {
						return true
					}
					nextTile.markVisited(1)
					currentTile = nextTile
					continue
				}
				2 -> if (i + 1 < grid.size && grid[i + 1][j].type != '#') { // Move down
					val nextTile = grid[i + 1][j]
					if (nextTile.isVisitedFrom(direction)) {
						return true
					}
					nextTile.markVisited(2)
					currentTile = nextTile
					continue
				}
				3 -> if (j > 0 && grid[i][j - 1].type != '#') { // Move left
					val nextTile = grid[i][j - 1]
					if (nextTile.isVisitedFrom(direction)) {
						return true
					}
					nextTile.markVisited(3)
					currentTile = nextTile
					continue
				}
			}
			direction = (direction + 1) % 4
			currentTile.markVisited(direction)
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

		override fun toString(): String = "Tile(i=$i, j=$j, type=$type, visited=${visited.contentToString()}, hasBeenVisited=$hasBeenVisited)"
	}

	private data class TraverseState (
		val sum: Int,
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
