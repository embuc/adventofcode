package y2015

import Task

/*--- Day 6: Probably a Fire Hazard ---*/
class Task6(private val input: List<String>) : Task {

	override fun a(): Int = processGrid { line, grid, i1, j1, i2, j2 ->
		when {
			line.startsWith("turn on") -> grid.fill(1, i1, j1, i2, j2)
			line.startsWith("turn off") -> grid.fill(0, i1, j1, i2, j2)
			line.startsWith("toggle") -> grid.toggle(i1, j1, i2, j2)
		}
	}

	override fun b(): Int = processGrid { line, grid, i1, j1, i2, j2 ->
		when {
			line.startsWith("turn on") -> grid.brighten(1, i1, j1, i2, j2)
			line.startsWith("turn off") -> grid.dim(1, i1, j1, i2, j2)
			line.startsWith("toggle") -> grid.brighten(2, i1, j1, i2, j2)
		}
	}

	private fun processGrid(action: (String, Array<IntArray>, Int, Int, Int, Int) -> Unit): Int {
		val grid = Array(1000) { IntArray(1000) }

		input.forEach { line ->
			val (range1, range2) = Regex("\\s\\d+,\\d+").findAll(line)
				.map { it.value }
				.toList()

			val (i1, j1) = range1.split(",").map { it.trim().toInt() }
			val (i2, j2) = range2.split(",").map { it.trim().toInt() }

			action(line, grid, i1, j1, i2, j2)
		}

		return grid.sumOf { it.sum() }
	}

	// Extension functions to simplify grid manipulation
	private fun Array<IntArray>.fill(value: Int, i1: Int, j1: Int, i2: Int, j2: Int) {
		for (i in i1..i2) {
			for (j in j1..j2) {
				this[i][j] = value
			}
		}
	}

	private fun Array<IntArray>.toggle(i1: Int, j1: Int, i2: Int, j2: Int) {
		for (i in i1..i2) {
			for (j in j1..j2) {
				this[i][j] = this[i][j] xor 1
			}
		}
	}

	private fun Array<IntArray>.brighten(amount: Int, i1: Int, j1: Int, i2: Int, j2: Int) {
		for (i in i1..i2) {
			for (j in j1..j2) {
				this[i][j] += amount
			}
		}
	}

	private fun Array<IntArray>.dim(amount: Int, i1: Int, j1: Int, i2: Int, j2: Int) {
		for (i in i1..i2) {
			for (j in j1..j2) {
				this[i][j] = maxOf(0, this[i][j] - amount)
			}
		}
	}
}