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
		val visited = Array(128) { BooleanArray(128) }

		var countGroups = 0
		for (i in 0..127) {
			for (j in 0..127) {
				if (grid[i][j] && !visited[i][j]) {
					fillRegion(grid, i, j, visited)
					countGroups++
				}
			}
		}

		return countGroups
	}

	private fun fillRegion(grid: Array<BooleanArray>, i: Int, j: Int, visited: Array<BooleanArray>) {
		if (i < 0 || i >= 128 || j < 0 || j >= 128 || !grid[i][j] || visited[i][j]) {
			return
		}
		visited[i][j] = true
		fillRegion(grid, i - 1, j, visited)
		fillRegion(grid, i + 1, j, visited)
		fillRegion(grid, i, j - 1, visited)
		fillRegion(grid, i, j + 1, visited)
	}
}