package y2024

import Task

/* --- Day 4: Ceres Search --- */
class Task4(val input: List<String>) : Task {

	override fun a(): Any {
		val grid = to2dArray(input)
		var sum = 0
		for (i in grid.indices) {
			for (j in grid[i].indices) {
				if (grid[i][j] == 'X') {
					sum += countXmases(grid, i, j)
				}
			}
		}
		return sum
	}

	override fun b(): Any {
		val grid = to2dArray(input)
		var sum = 0
		for (i in grid.indices) {
			for (j in grid[i].indices) {
				if (grid[i][j] == 'A') {
					sum += countX_mas_es(grid, i, j)
				}
			}
		}
		return sum
	}

	private fun countX_mas_es(grid: Array<Array<Char>>, i: Int, j: Int): Int {
		// check s:es them m:s mind the borders - this time its distance 2 not 3
		// if the distance is less than 2, we can't have a valid X-mas
		var sum = 0
		if (i - 1 >= 0 && j - 1 >= 0 && i < grid.size - 1 && j < grid.size - 1) {
			// now, we are inside
			// we can either have two ss or two ms on any given side, and the other two on the other side
			//start with both ss on the left
			if (grid[i - 1][j - 1] == 'S' && grid[i + 1][j - 1] == 'S') {
				if (grid[i - 1][j + 1] == 'M' && grid[i + 1][j + 1] == 'M') {
					sum++
				}
			}
			//start with both ms on the left
			if (grid[i - 1][j - 1] == 'M' && grid[i + 1][j - 1] == 'M') {
				if (grid[i - 1][j + 1] == 'S' && grid[i + 1][j + 1] == 'S') {
					sum++
				}
			}
			//start with both ss on the upper side
			if (grid[i - 1][j - 1] == 'S' && grid[i - 1][j + 1] == 'S') {
				if (grid[i + 1][j - 1] == 'M' && grid[i + 1][j + 1] == 'M') {
					sum++
				}
			}
			//start with both ms on the upper side
			if (grid[i - 1][j - 1] == 'M' && grid[i - 1][j + 1] == 'M') {
				if (grid[i + 1][j - 1] == 'S' && grid[i + 1][j + 1] == 'S') {
					sum++
				}
			}
		}
		return sum
	}

	private fun countXmases(grid: Array<Array<Char>>, i: Int, j: Int): Int {
		//check every direction but mind the distance to the borders
		//if the distance is less than 3, we can't have a valid Xmas
		//check up
		var sum = 0
		if (i - 3 >= 0) {
			if (grid[i - 1][j] == 'M' && grid[i - 2][j] == 'A' && grid[i - 3][j] == 'S') {
				sum++
			}
		}
		//check down
		if (i + 3 < grid.size) {
			if (grid[i + 1][j] == 'M' && grid[i + 2][j] == 'A' && grid[i + 3][j] == 'S') {
				sum++
			}
		}
		//check left
		if (j - 3 >= 0) {
			if (grid[i][j - 1] == 'M' && grid[i][j - 2] == 'A' && grid[i][j - 3] == 'S') {
				sum++
			}
		}
		//check right
		if (j + 3 < grid.size) {
			if (grid[i][j + 1] == 'M' && grid[i][j + 2] == 'A' && grid[i][j + 3] == 'S') {
				sum++
			}
		}
		//check up-left
		if (i - 3 >= 0 && j - 3 >= 0) {
			if (grid[i - 1][j - 1] == 'M' && grid[i - 2][j - 2] == 'A' && grid[i - 3][j - 3] == 'S') {
				sum++
			}
		}
		//check up-right
		if (i - 3 >= 0 && j + 3 < grid.size) {
			if (grid[i - 1][j + 1] == 'M' && grid[i - 2][j + 2] == 'A' && grid[i - 3][j + 3] == 'S') {
				sum++
			}
		}
		//check down-left
		if (i + 3 < grid.size && j - 3 >= 0) {
			if (grid[i + 1][j - 1] == 'M' && grid[i + 2][j - 2] == 'A' && grid[i + 3][j - 3] == 'S') {
				sum++
			}
		}
		//check down-right
		if (i + 3 < grid.size && j + 3 < grid.size) {
			if (grid[i + 1][j + 1] == 'M' && grid[i + 2][j + 2] == 'A' && grid[i + 3][j + 3] == 'S') {
				sum++
			}
		}

		return sum
	}

	private fun to2dArray(input: List<String>): Array<Array<Char>> {
		val array = Array(input.size) { Array(input.size) { ' ' } }
		for (i in input.indices) {
			for (j in input[i].indices) {
				array[i][j] = input[i][j]
			}
		}
		return array
	}
}