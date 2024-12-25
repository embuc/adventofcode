package y2024

import Task

//--- Day 25: Code Chronicle ---
class Task25(val input: List<String>) : Task {


	override fun a(): Int {
		val (locks, keys) = getLocksAndKeys()
		// find the number of keys that can unlock some locks
		var count = 0
		for (key in keys) {
			for (lock in locks) {
				if (keyUnlocksLock(key, lock)) {
//					println("Key: $key can unlock lock: $lock")
					count++
				}
			}
		}
		return count
	}

	private fun keyUnlocksLock(key: List<Int>, lock: List<Int>): Boolean {
		for (i in key.indices) {
			if (key[i] + lock[i] > 5) {
				return false
			}
		}
		return true
	}

	private fun getLocksAndKeys(): Pair<MutableList<List<Int>>, MutableList<List<Int>>> {
		val keys = mutableListOf<List<Int>>()
		val locks = mutableListOf<List<Int>>()
		//parse schematics to keys and locks
		for (i in input.indices step 8) {
			val schema = input.subList(i, i + 7)
			val grid = schema.map { it.trim().toCharArray().map { if (it == '#') 1 else 0 } }
	//			grid.forEach(::println)
			if (grid[0].sum() == 0) {
				keys.add(sumColumns(grid))
			} else {
				locks.add(sumColumns(grid))
			}
		}
//		keys.forEach(::println)
//		locks.forEach(::println)
		return Pair(locks, keys)
	}

	private fun sumColumns(grid: List<List<Int>>): List<Int> {
		val result = MutableList(grid[0].size) { 0 }
		for (i in grid.indices) {
			for (j in grid[i].indices) {
				result[j] += grid[i][j]
			}
		}
		// remove the 'full row'
		return result.map { it - 1 }
	}

	override fun b(): String {
		return ""
	}
}