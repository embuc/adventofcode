package y2023

import Task
import utils.readInputAsListOfStrings

object Task14 : Task {
	const val EMPTY = 0 // .
	const val ROCK = 1  // O
	const val CUBE = 2  // #


	override fun a(): Any {
		return solveA(readInputAsListOfStrings("2023_14.txt"))
	}

	override fun b(): Any {
		return solveB(readInputAsListOfStrings("2023_14.txt"))
	}

	fun solveA(list: List<String>): Any {
		val grid = parseToGrid(list)
		rollNorth(grid)
		return getLoad(grid)
	}

	fun solveB(lines: List<String>): Int {
		val grid = parseToGrid(lines)
		val oldGrids = mutableListOf(grid.map(IntArray::copyOf))
		for (cycle in 1..1_000_000_000) {
			rollNorth(grid)
			rollWest(grid)
			rollSouth(grid)
			rollEast(grid)
			val i = oldGrids.indexOfFirst { oldGrid -> grid.indices.all { i -> grid[i].contentEquals(oldGrid[i]) } }
			if (i == -1) { oldGrids.add(grid.map(IntArray::copyOf)); continue }
			// We have a cycle, state is the same as when we started
			val cycleLength = cycle - i
			println("Cycle length: $cycleLength")
			val remaining = 1_000_000_000 - cycle
			val offset = remaining % cycleLength
			return getLoad(oldGrids[i + offset])
		}
		return getLoad(grid)
	}


	fun parseToGrid(list: List<String>): List<IntArray> {
		return list.map { line ->
			line.map { char ->
				when (char) {
					'.' -> EMPTY
					'O' -> ROCK
					'#' -> CUBE
					else -> throw IllegalArgumentException("Unknown char: $char")
				}
			}.toIntArray()
		}.toList()
	}

	private fun getLoad(grid: List<IntArray>): Int =
		grid.indices.sumOf { x ->
			(grid.size - x) * grid[x].count { n -> n == ROCK }
		}

	private fun rollNorth(grid: List<IntArray>) {
		for (max in grid.size downTo 1)
			for (i in 1..<max)
				for (j in grid[0].indices) {
					if (grid[i][j] == ROCK && grid[i - 1][j] == EMPTY) {
						grid[i][j] = EMPTY
						grid[i - 1][j] = ROCK
					}
				}
	}

	private fun rollWest(grid: List<IntArray>) {
		for (max in grid[0].size downTo 1)
			for (j in 1..<max)
				for (i in grid.indices) {
					if (grid[i][j] == ROCK && grid[i][j - 1] == EMPTY) {
						grid[i][j] = EMPTY
						grid[i][j - 1] = ROCK
					}
				}
	}

	private fun rollSouth(grid: List<IntArray>) {
		for (min in 0..grid.size - 2)
			for (i in grid.size - 2 downTo min)
				for (j in grid[0].indices) {
					if (grid[i][j] == ROCK && grid[i + 1][j] == EMPTY) {
						grid[i][j] = EMPTY
						grid[i + 1][j] = ROCK
					}
				}
	}

	private fun rollEast(grid: List<IntArray>) {
		for (min in 0..grid[0].size)
			for (j in grid[0].size - 2 downTo min)
				for (i in grid.indices) {
					if (grid[i][j] == ROCK && grid[i][j + 1] == EMPTY) {
						grid[i][j] = EMPTY
						grid[i][j + 1] = ROCK
					}
				}
	}

}