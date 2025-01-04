package y2016

import Task

//--- Day 8: Two-Factor Authentication ---
class Task8(val input: List<String>, val w: Int, val h: Int) : Task {

	override fun a(): Any {
		// create grid wxh, fill with '.'
		val grid = Array(h) { CharArray(w) { '.' } }
//		printGrid(grid)
		for (line in input) {
//			println("line: $line  result:")
			val parts = line.split(" ")
			if (parts[0] == "rect") {
				val (a, b) = parts[1].split("x").map { it.toInt() }
				for (i in 0 until a) {
					for (j in 0 until b) {
						grid[j][i] = '#'
					}
				}
			} else if (parts[0] == "rotate") {
				val (_, b) = parts[2].split("=")
				val c = parts[4].toInt()
				val xy = b.toInt()
				if (parts[1] == "row") {
					val row = grid[xy].toMutableList()
					for (i in 0 until w) {
						grid[xy][(i + c) % w] = row[i]
					}
				} else if (parts[1] == "column") {
					val col = grid.map { it[xy] }.toMutableList()
					for (i in 0 until h) {
						grid[(i + c) % h][xy] = col[i]
					}
				}
			}
		}
//			printGrid(grid)
		return grid.sumOf { it -> it.count { it == '#' } }
	}

	override fun b(): Any {
		//print a() to see the result
		return "EOARGPHYAO"
	}
}