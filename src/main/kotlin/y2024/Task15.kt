package y2024

import Task

/*--- Day 15: Warehouse Woes ---*/
class Task15(val input: List<String>) : Task {

	override fun a(): Any {
		// parse input to grid and instructions
		val grid = input.subList(0, input.indexOf("")).map { it.toCharArray() }
//		printGrid(grid)
		val instructions = input
			.dropWhile { it.isNotEmpty() } // Skip all lines until the first empty line
			.drop(1) // Skip the empty line itself
			.joinToString("") // Combine everything after into a single continuous string
			.split("").filter { it.isNotEmpty() }
		var player = grid.mapIndexed { i, row -> Pair(i, row.indexOf('@')) }.filter { it.second != -1 }.first()
		for (i in instructions) {
//			println("Before instruction: $i")
//			println()
//			printGrid(grid)
//			println()
			when (i) {
				"v" -> {
					if (!wall(grid, player, "v")) {
						val emptySpace = findEmptySpaceToMoveIn(grid, player, "v")
						if (emptySpace.first != -1) {
							player = movePlayerAndBoxes(grid, player, "v", emptySpace)
						}
					}
				}
				"^" -> {
					if (!wall(grid, player, "^")) {
						val emptySpace = findEmptySpaceToMoveIn(grid, player, "^")
						if (emptySpace.first != -1){
							player = movePlayerAndBoxes(grid, player, "^", emptySpace)
						}
					}
				}
				">" -> {
					if (!wall(grid, player, ">")) {
						val emptySpace = findEmptySpaceToMoveIn(grid, player, ">")
						if (emptySpace.first != -1) {
							player = movePlayerAndBoxes(grid, player, ">", emptySpace)
						}
					}
				}
				"<" -> {
					if (!wall(grid, player, "<")){
						val emptySpace = findEmptySpaceToMoveIn(grid, player, "<")
						if (emptySpace.first != -1) {
							player = movePlayerAndBoxes(grid, player, "<", emptySpace)
						}
					}
				}
			}
//			println("After instruction: $i")
//			println()
//			printGrid(grid)
//			println()
		}
//		println(player)
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
		when (s) {
			"v" -> {
				grid[player.first][player.second] = '.'
				for (i in player.first + 2 .. emptySpace.first) {
					grid[i][player.second] = 'O'
				}
				grid[player.first + 1][player.second] = '@'
				return player.copy(first = player.first + 1)
			}
			"^" -> {
				grid[player.first][player.second] = '.'
				for (i in player.first - 2 downTo emptySpace.first) {
					grid[i][player.second] = 'O'
				}
				grid[player.first - 1][player.second] = '@'
				return player.copy(first = player.first - 1)
			}
			">" -> {
				grid[player.first][player.second] = '.'
				for (i in player.second + 2 .. emptySpace.second) {
					grid[player.first][i] = 'O'
				}
				grid[player.first][player.second + 1] = '@'
				return player.copy(second = player.second + 1)
			}
			"<" -> {
				grid[player.first][player.second] = '.'
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
		return when (s) {
			"v" -> {
				for (i in player.first + 1 until grid.size) {
					when (grid[i][player.second]) {
						'.' -> return Pair(i, player.second)
						'O' -> continue
						'#' -> break
					}
				}
				Pair(-1, -1)
			}
			"^" -> {
				for (i in player.first - 1 downTo 0) {
					when (grid[i][player.second]) {
						'.' -> return Pair(i, player.second)
						'O' -> continue
						'#' -> break
					}
				}
				Pair(-1, -1)
			}
			">" -> {
				for (i in player.second + 1 until grid.size) {
					when (grid[player.first][i]) {
						'.' -> return Pair(player.first, i)
						'O' -> continue
						'#' -> break
					}
				}
				Pair(-1, -1)
			}
			"<" -> {
				for (i in player.second - 1 downTo 0) {
					when (grid[player.first][i]) {
						'.' -> return Pair(player.first, i)
						'O' -> continue
						'#' -> break
					}
				}
				Pair(-1, -1)
			}
			else -> Pair(-1, -1)
		}
	}

	private fun wall(grid: List<CharArray>, player: Pair<Int, Int>, s: String): Boolean {
		return when (s) {
			"v" -> grid[player.first + 1][player.second] == '#'
			"^" -> grid[player.first - 1][player.second] == '#'
			">" -> grid[player.first][player.second + 1] == '#'
			"<" -> grid[player.first][player.second - 1] == '#'
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