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
			if (!wall(grid, player, direction)) {
				val emptySpace = findEmptySpaceToMoveIn(grid, player, direction)
				if (emptySpace.first != -1) {
					player = movePlayerAndBoxes(grid, player, direction, emptySpace)
				}
			}
		}
		return calculateSumOfGpsCoordinates(grid, 'O')
	}

	private fun calculateSumOfGpsCoordinates(grid: List<CharArray>, char: Char): Long {
		var sum = 0L
		grid.forEachIndexed { i, row ->
			row.forEachIndexed { j, c ->
				if (c == char) {
					sum += 100L * i + j
				}
			}
		}
		return sum
	}

	private fun movePlayerAndBoxes(grid: List<CharArray>, player: Pair<Int, Int>, s: String, emptySpace: Pair<Int, Int>): Pair<Int, Int> {
		grid[player.first][player.second] = '.'
		when (s) {
			"v" -> {
				for (i in player.first + 2..emptySpace.first) {
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
				for (i in player.second + 2..emptySpace.second) {
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
		val grid = expand(input.subList(0, input.indexOf("")).map { it.toCharArray() })
		val instructions = input
			.dropWhile { it.isNotEmpty() }
			.drop(1)
			.joinToString("")
			.split("").filter { it.isNotEmpty() }
		var player = grid.mapIndexed { i, row -> Pair(i, row.indexOf('@')) }.filter { it.second != -1 }.first()
		for (direction in instructions) {
			if (!wall(grid, player, direction)) {
				when (direction) {
					"v" -> {
						if (canMoveBoxesUPDown(grid, player.first, player.second, direction)) {
							player = movePlayerAndBoxesUPDown(grid, player.first, player.second, direction)
						}
					}

					"^" -> {
						if (canMoveBoxesUPDown(grid, player.first, player.second, direction)) {
							player = movePlayerAndBoxesUPDown(grid, player.first, player.second, direction)
						}
					}

					">" -> {
						if (canMoveBoxesLeftRight(grid, player, direction)) {
							player = movePlayerAndBoxesLeftRight(grid, player, direction)
						}
					}

					"<" -> {
						if (canMoveBoxesLeftRight(grid, player, direction)) {
							player = movePlayerAndBoxesLeftRight(grid, player, direction)
						}
					}
				}
			}
		}
		return calculateSumOfGpsCoordinates(grid, '[')
	}

	private fun canMoveBoxesLeftRight(grid: List<CharArray>, player: Pair<Int, Int>, direction: String): Boolean {
		val (x, y) = player
		val (dx, dy) = when (direction) {
			">" -> y + 1 until grid[0].size to { j: Int -> Pair(x, j) } // Right

			"<" -> y - 1 downTo 0 to { j: Int -> Pair(x, j) } // Left
			else -> return false
		}

		for (i in dx) {
			val (newX, newY) = dy(i)
			when (grid[newX][newY]) {
				'.' -> return true
				'#' -> return false
			}
		}
		return false
	}

	private fun movePlayerAndBoxesLeftRight(grid: List<CharArray>, player: Pair<Int, Int>, direction: String): Pair<Int, Int> {
		val (x, y) = player
		val (dx, dy) = when (direction) {
			">" -> y + 1 until grid[0].size to { j: Int -> Pair(x, j) } // Right
			"<" -> y - 1 downTo 0 to { j: Int -> Pair(x, j) } // Left
			else -> return player
		}

		var emptySpace = Pair(-1, -1)

		for (i in dx) {
			val (newX, newY) = dy(i)
			when (grid[newX][newY]) {
				'.' -> {
					emptySpace = Pair(newX, newY)
					break
				}
			}
		}
		// fill all tiles with double boxes between the player and the empty space (i.e. player minus one space )
		when (direction) {
			">" -> {
				for (i in y + 2..emptySpace.second step 2) {
					grid[x][i] = '['
					grid[x][i + 1] = ']'
				}
				grid[x][y + 1] = '@'
				grid[x][y] = '.'
				return player.copy(second = y + 1)
			}

			"<" -> {
				for (i in y - 2 downTo emptySpace.second step 2) {
					grid[x][i] = ']'
					grid[x][i - 1] = '['
				}
				grid[x][y - 1] = '@'
				grid[x][y] = '.'
				return player.copy(second = y - 1)
			}

			else -> return player
		}
	}

	private fun canMoveBoxesUPDown(grid: List<CharArray>, x: Int, y: Int, direction: String): Boolean {

		// Get the next position based on direction
		val (dx, dy) = when (direction) {
			"v" -> Pair(1, 0) // Down
			"^" -> Pair(-1, 0) // Up
			else -> return false
		}

		val nextX = x + dx
		val nextY = y + dy

		// Base cases: if next is wall, return false, or if another box is in the way, fire of 2xrecursion

		when (grid[nextX][nextY]) {
			'.' -> return true // Found an empty space, move is possible
			'#' -> return false // Hit a wall
			'[' -> {
				val left = canMoveBoxesUPDown(grid, nextX, nextY, direction)
				val right = canMoveBoxesUPDown(grid, nextX, nextY + 1, direction)
				return left && right
			}

			']' -> {
				val left = canMoveBoxesUPDown(grid, nextX, nextY, direction)
				val right = canMoveBoxesUPDown(grid, nextX, nextY - 1, direction)
				return left && right
			}
		}

		return true
	}

	private fun movePlayerAndBoxesUPDown(grid: List<CharArray>, x: Int, y: Int, direction: String): Pair<Int, Int> {
		val (dx, dy) = when (direction) {
			"v" -> Pair(1, 0) // Down
			"^" -> Pair(-1, 0) // Up
			else -> Pair(0, 0)
		}

		val nextX = x + dx
		val nextY = y + dy

		// Base cases: if next is wall, return false, or if another box is in the way, fire of 2xrecursion
		when (grid[nextX][nextY]) {
			'.' -> {
				// Found an empty space, move is possible
				grid[nextX][nextY] = grid[x][y]
				grid[x][y] = '.'
				return Pair(nextX, nextY)
			}

			'[' -> {
				movePlayerAndBoxesUPDown(grid, nextX, nextY, direction)
				movePlayerAndBoxesUPDown(grid, nextX, nextY + 1, direction)
				grid[nextX][nextY] = grid[x][y]
				grid[x][y] = '.'
			}

			']' -> {
				movePlayerAndBoxesUPDown(grid, nextX, nextY, direction)
				movePlayerAndBoxesUPDown(grid, nextX, nextY - 1, direction)
				grid[nextX][nextY] = grid[x][y]
				grid[x][y] = '.'
			}
		}

		return Pair(nextX, nextY)
	}

	private fun expand(map: List<CharArray>): List<CharArray> {
		val expanded = mutableListOf<CharArray>()
//		these are the rules:
//		If the tile is #, the new map contains ## instead.
//		If the tile is O, the new map contains [] instead.
//		If the tile is ., the new map contains .. instead.
//		If the tile is @, the new map contains @. instead.
		map.forEach { row ->
			val newRow = CharArray(row.size * 2)
			row.forEachIndexed { i, c ->
				when (c) {
					'#' -> {
						newRow[2 * i] = '#'
						newRow[2 * i + 1] = '#'
					}

					'O' -> {
						newRow[2 * i] = '['
						newRow[2 * i + 1] = ']'
					}

					'.' -> {
						newRow[2 * i] = '.'
						newRow[2 * i + 1] = '.'
					}

					'@' -> {
						newRow[2 * i] = '@'
						newRow[2 * i + 1] = '.'
					}
				}
			}
			expanded.add(newRow)
		}
		return expanded
	}
}