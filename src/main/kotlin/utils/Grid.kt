package utils

import kotlin.math.abs


//my grid stuff
fun toGrid(input: List<String>): Array<Array<Triple<Int, Int, Char>>> {
	return Array(input.size) { i ->
		Array(input[i].length) { j ->
			Triple(i, j, input[i][j])
		}
	}
}

fun toTripleGrid(input: List<String>): Array<Array<Triple<Int, Int, Char>>> {
	return Array(input.size) { i ->
		Array(input[i].length) { j ->
			Triple(i, j, input[i][j])
		}
	}
}

fun toCharGrid(input: List<String>): Array<CharArray> {
	return Array(input.size) { i ->
		CharArray(input[i].length) { j ->
			input[i][j]
		}
	}
}

fun manhattanDistance(source: Pair<Int, Int>, target: Pair<Int, Int>): Int {
	return abs(source.first - target.first) + abs(source.second - target.second)
}

fun printGrid(grid: Array<Array<Triple<Int, Int, Char>>>) {
	for (i in grid.indices) {
		for (j in grid[i].indices) {
			print(grid[i][j].third)
		}
		println()
	}
}

fun printGridColor(grid: Array<Array<Triple<Int, Int, Char>>>, dimmed:List<Char> = listOf(), accent:List<Char> = listOf(), current: Pair<Int, Int> = Pair(-1, -1)) {
	for (i in grid.indices) {
		for (j in grid[i].indices) {
			if (i == current.first && j == current.second) {
				ConsoleColors.printRed('@'.toString())
//				ConsoleColors.printYellowBold(grid[i][j].third.toString())
			}else if (grid[i][j].third in dimmed) {
				ConsoleColors.printGray(grid[i][j].third.toString())
			} else if (grid[i][j].third in accent) {
				ConsoleColors.printGreen(grid[i][j].third.toString())
			} else {
				print(grid[i][j].third)
			}
		}
		println()
	}
}

fun printGrid(grid: List<CharArray>) {
	grid.forEach {
		println(it)
	}
}
fun printGrid(grid: Array<CharArray>) {
	println("Grid size x: ${grid.size} y: ${grid[0].size}")
	grid.forEach {
		println(it)
	}
}
fun printGridColor(grid: Array<CharArray>) {
	println("Grid size x: ${grid.size} y: ${grid[0].size}")
	grid.forEach { it ->
		it.forEach {
			when (it) {
				'#' -> {
					ConsoleColors.printGray(it.toString())
				}
				'.' -> {
					ConsoleColors.printLightGray(it.toString())
				}
				else -> {
					ConsoleColors.printGreen(it.toString())
				}
			}
		}
		println()
	}
}

fun printPathInGridColor(grid: Array<CharArray>, path: Array<IntArray>) {
	println("Grid size x: ${grid.size} y: ${grid[0].size}")
	grid.forEachIndexed { x, row ->
		row.forEachIndexed { y, char ->
			val isInPath = path.any { it[0] == x && it[1] == y }
			when {
				isInPath -> ConsoleColors.printGreen(char.toString())
				char == '#' -> ConsoleColors.printGray(char.toString())
				else -> ConsoleColors.printLightGray(char.toString())
			}
		}
		println()
	}
}

fun resetGrid(grid: Array<Array<Triple<Int, Int, Char>>>) {
	for (i in grid.indices) {
		for (j in grid[i].indices) {
			grid[i][j] = Triple(i, j, '.')
		}
	}
}

fun countChar(grid: Array<Array<Triple<Int, Int, Char>>>, c: Char): Int {
	var count = 0
	for (i in grid.indices) {
		for (j in grid[i].indices) {
			if (grid[i][j].third == c) count++
		}
	}
	return count
}

fun countChar(grid: Array<Array<Triple<Int, Int, Char>>>, condition: (Char) -> Boolean): Int {
	var count = 0
	for (i in grid.indices) {
		for (j in grid[i].indices) {
			if (condition(grid[i][j].third)) count++
		}
	}
	return count
}

fun countChar(grid: List<String>, char: Char): Int {
	var count = 0
	for (i in grid.indices) {
		for (j in grid[i].indices) {
			if (grid[i][j] == char) count++
		}
	}
	return count
}

fun isInsideGrid(
	grid: Array<Array<Triple<Int, Int, Char>>>,
	x: Int,
	y: Int
) = x in grid.indices && y in grid[0].indices
