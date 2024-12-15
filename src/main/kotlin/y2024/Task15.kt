package y2024

import Task

/*--- Day 15: Warehouse Woes ---*/
class Task15(val input: List<String>) : Task {

	override fun a(): Any {
		val grid = input.subList(0, input.indexOf("")).map { it.toCharArray() }
		val instructions = input
			.dropWhile { it.isNotEmpty() }
			.drop(1)
			.joinToString("") 
			.split("").filter { it.isNotEmpty() }
		var player = grid.mapIndexed { i, row -> Pair(i, row.indexOf('@')) }.filter { it.second != -1 }.first()
		for (direction in instructions) {
//			println("\nBefore instruction: $i \n")
//			printGrid(grid)
			if (!wall(grid, player, direction)) {
				val emptySpace = findEmptySpaceToMoveIn(grid, player, direction)
				if (emptySpace.first != -1) {
					player = movePlayerAndBoxes(grid, player, direction, emptySpace)
				}
			}
//			println("\nAfter instruction: $i \n")
//			printGrid(grid)
		}
		return calculateSumOfGpsCoordinates(grid)
	}

	private fun calculateSumOfGpsCoordinates(grid: List<CharArray>): Long {
		var sum = 0L
		grid.forEachIndexed { i, row ->
			row.forEachIndexed { j, c ->
				if (c == 'O') {
					sum += 100L*i + j
				}
			}
		}
		return sum
	}

	private fun movePlayerAndBoxes(grid: List<CharArray>, player: Pair<Int, Int>, s: String, emptySpace:Pair<Int, Int>): Pair<Int, Int> {
		grid[player.first][player.second] = '.'
		when (s) {
			"v" -> {
				for (i in player.first + 2 .. emptySpace.first) {
					grid[i][player.second] = 'O'
				}
				grid[player.first + 1][player.second] = '@'
				return player.copy(first = player.first + 1)
			}
			"^" -> {
				for (i in player.first - 2 downTo emptySpace.first) {
					grid[i][player.second] = 'O'
				}
				grid[player.first - 1][player.second] = '@'
				return player.copy(first = player.first - 1)
			}
			">" -> {
				for (i in player.second + 2 .. emptySpace.second) {
					grid[player.first][i] = 'O'
				}
				grid[player.first][player.second + 1] = '@'
				return player.copy(second = player.second + 1)
			}
			"<" -> {
				for (i in player.second - 2 downTo emptySpace.second) {
					grid[player.first][i] = 'O'
				}
				grid[player.first][player.second - 1] = '@'
				return player.copy(second = player.second - 1)
			}
			else -> return player
		}
	}

	private fun findEmptySpaceToMoveIn(grid: List<CharArray>, player: Pair<Int, Int>, s: String): Pair<Int, Int> {
		val (row, col) = player
		val directions = when (s) {
			"v" -> row + 1 until grid.size to { i: Int -> Pair(i, col) }
			"^" -> row - 1 downTo 0 to { i: Int -> Pair(i, col) }
			">" -> col + 1 until grid[row].size to { i: Int -> Pair(row, i) }
			"<" -> col - 1 downTo 0 to { i: Int -> Pair(row, i) }
			else -> return Pair(-1, -1)
		}

		for (i in directions.first) {
			val (newRow, newCol) = directions.second(i)
			when (grid[newRow][newCol]) {
				'.' -> return Pair(newRow, newCol)
				'O' -> continue
				'#' -> break
			}
		}
		return Pair(-1, -1)
	}

	private fun wall(grid: List<CharArray>, player: Pair<Int, Int>, direction: String): Boolean {
		val (x, y) = player
		return when (direction) {
			"v" -> grid[x + 1][y] == '#'
			"^" -> grid[x - 1][y] == '#'
			">" -> grid[x][y + 1] == '#'
			"<" -> grid[x][y - 1] == '#'
			else -> false
		}
	}

	private fun printGrid(grid: List<CharArray>) {
		grid.forEach {
			println(it)
		}
	}

	override fun b(): Any {
		return 0
	}
}