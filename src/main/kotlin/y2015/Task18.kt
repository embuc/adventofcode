package y2015

import Task
import utils.toGrid

//--- Day 18: Like a GIF For Your Yard ---
class Task18(val input: List<String>, val steps: Int = 4) : Task {


	override fun a(): Any {
		val grid = toGrid(input)
		for (i in 0 until steps) {
			val newGrid = Array(grid.size) { i ->
				Array(grid[i].size) { j ->
					val neighbors = mutableListOf<Char>()
					for (x in -1..1) {
						for (y in -1..1) {
							// Skip the current cell
							if (x == 0 && y == 0) continue
							// Skip out of bounds
							if (i + x < 0 || i + x >= grid.size || j + y < 0 || j + y >= grid[i].size) continue
							// Add the neighbor
							neighbors.add(grid[i + x][j + y].third)
						}
					}
					val count = neighbors.count { it == '#' }
					if (grid[i][j].third == '#') {
						if (count == 2 || count == 3) {
							Triple(i, j, '#')
						} else {
							Triple(i, j, '.')
						}
					} else {
						if (count == 3) {
							Triple(i, j, '#')
						} else {
							Triple(i, j, '.')
						}
					}
				}
			}
			for (i in grid.indices) {
				for (j in grid[i].indices) {
					grid[i][j] = newGrid[i][j]
				}
			}
		}
		return grid.sumOf { row -> row.count { it.third == '#' } }
	}

	override fun b(): Any {
		val grid = toGrid(input)
//		println("Initial state:")
//		printGrid(grid)
		for (i in 0 until steps) {
			val newGrid = Array(grid.size) { i ->
				Array(grid[i].size) { j ->
					//skip corners
					if ((i == 0 || i == grid.size - 1) && (j == 0 || j == grid[i].size - 1)) {
						Triple(i, j, '#')
					} else {
						val neighbors = mutableListOf<Char>()
						for (x in -1..1) {
							for (y in -1..1) {
								// Skip the current cell
								if (x == 0 && y == 0) continue
								// Skip out of bounds
								if (i + x < 0 || i + x >= grid.size || j + y < 0 || j + y >= grid[i].size) continue
								// Add the neighbor
								neighbors.add(grid[i + x][j + y].third)
							}
						}
						val count = neighbors.count { it == '#' }
						if (grid[i][j].third == '#') {
							if (count == 2 || count == 3) {
								Triple(i, j, '#')
							} else {
								Triple(i, j, '.')
							}
						} else {
							if (count == 3) {
								Triple(i, j, '#')
							} else {
								Triple(i, j, '.')
							}
						}
					}
				}
			}
			for (i in grid.indices) {
				for (j in grid[i].indices) {
					grid[i][j] = newGrid[i][j]
				}
			}
//			println("After ${i+1} steps:")
//			printGrid(grid)
		}
		return grid.sumOf { row -> row.count { it.third == '#' } }
	}
}