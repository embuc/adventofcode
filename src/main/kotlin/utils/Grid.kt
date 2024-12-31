package utils


//my grid stuff
fun toGrid(input: List<String>): Array<Array<Triple<Int, Int, Char>>> {
	return Array(input.size) { i ->
		Array(input[i].length) { j ->
			Triple(i, j, input[i][j])
		}
	}
}

fun printGrid(grid: Array<Array<Triple<Int, Int, Char>>>) {
	for (i in grid.indices) {
		for (j in grid[i].indices) {
			print(grid[i][j].third)
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
	grid.forEach {
		println(it)
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

fun isInsideGrid(
	grid: Array<Array<Triple<Int, Int, Char>>>,
	x: Int,
	y: Int
) = x in grid.indices && y in grid[0].indices
