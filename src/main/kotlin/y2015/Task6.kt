package y2015

import Task
import jdk.internal.org.jline.terminal.TerminalBuilder.SystemOutput

/*--- Day 6: Probably a Fire Hazard ---*/
class Task6(val input:List<String>):Task {
	//	turn on 489,959 through 759,964
//	turn off 820,516 through 871,914


	override fun a(): Any {
		val grid = Array(1000) { IntArray(1000) }

		for(line in input) {
			val	(range1, range2) = Regex("\\s\\d+,\\d+").findAll(line).map { it.value }.toList()
			val (i1, j1) = range1.split(",").map { it.trim().toInt() }.toList()
			val (i2, j2) = range2.split(",").map { it.trim().toInt() }.toList()
			if (line.startsWith("turn on")) {
				for (i in i1..i2) {
					for (j in j1..j2 ) {
						grid[i][j] = 1
					}
				}
			} else if (line.startsWith("turn off")) {
				for (i in i1..i2) {
					for (j in j1..j2 ) {
						grid[i][j] = 0
					}
				}
			} else if (line.startsWith("toggle")) {
				for (i in i1..i2) {
					for (j in j1..j2 ) {
						grid[i][j] = grid[i][j] xor 1
					}
				}
			}
		}
		return countGrid(grid)
	}

	private fun countGrid(grid: Array<IntArray>): Any {
		return grid.flatMap { it.asIterable() }.sum()
	}

	override fun b(): Any {
		val grid = Array(1000) { IntArray(1000) }

		for(line in input) {
			val	(range1, range2) = Regex("\\s\\d+,\\d+").findAll(line).map { it.value }.toList()
			val (i1, j1) = range1.split(",").map { it.trim().toInt() }.toList()
			val (i2, j2) = range2.split(",").map { it.trim().toInt() }.toList()
			if (line.startsWith("turn on")) {
				for (i in i1..i2) {
					for (j in j1..j2 ) {
						grid[i][j] = grid[i][j] + 1
					}
				}
			} else if (line.startsWith("turn off")) {
				for (i in i1..i2) {
					for (j in j1..j2 ) {
						grid[i][j] = grid[i][j] - 1
						if (grid[i][j] < 0) {
							grid[i][j] = 0
						}
					}
				}
			} else if (line.startsWith("toggle")) {
				for (i in i1..i2) {
					for (j in j1..j2 ) {
						grid[i][j] = grid[i][j] + 2
					}
				}
			}
		}
		return countGrid(grid)
	}
}