package y2017

import Task

//--- Day 14: Disk Defragmentation ---
class Task14 (val input:String):Task {
	override fun a(): Any {
		val grid = Array(128) { BooleanArray(128) }
		for (i in 0..127) {
			val row = "$input-$i"
			val hash = knotHash(row)
			for (j in 0..31) {
				val hashByte = hash[j].toString().toInt(16)
				grid[i][j * 4 + 0] = (hashByte and 8) != 0
				grid[i][j * 4 + 1] = (hashByte and 4) != 0
				grid[i][j * 4 + 2] = (hashByte and 2) != 0
				grid[i][j * 4 + 3] = (hashByte and 1) != 0
			}
		}

		return grid.sumOf { it.count { it } }
	}

	override fun b(): Any {
		return 0
	}
}