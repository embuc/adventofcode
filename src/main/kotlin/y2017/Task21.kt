package y2017

import Task
import utils.countChar

//--- Day 21: Fractal Art ---
class Task21(val input: List<String>) : Task {
	private val patternCache = mutableMapOf<String, List<String>>()

	override fun a(): Any {
		val rules = input.associate { it.split(" => ")[0] to it.split(" => ")[1] }
		val rotRules = prepareRotatedRules(rules)
		val grid = enhance(rotRules, 5)
		return countChar(grid, '#')
	}

	override fun b(): Any {
		val rules = input.associate { it.split(" => ")[0] to it.split(" => ")[1] }
		val rotRules = prepareRotatedRules(rules)
		val grid = enhance(rotRules, 18)
		return countChar(grid, '#')
	}

	private fun prepareRotatedRules(rules: Map<String, String>): Map<String, String> {
		return rules.entries.flatMap { (key, value) ->
			getOrCreatePatternVariations(key).map { it to value }
		}.toMap()
	}

	private fun getOrCreatePatternVariations(pattern: String): List<String> {
		return patternCache.getOrPut(pattern) {
			val variations = mutableListOf<String>()
			var rotated = pattern
			repeat(4) {
				variations.add(rotated)
				rotated = rotateGrid(rotated)
			}
			var flipped = flipGrid(pattern)
			repeat(4) {
				variations.add(flipped)
				flipped = rotateGrid(flipped)
			}
			variations
		}
	}

	private fun rotateGrid(grid: String): String {
		val rows = grid.split("/")
		val size = rows.size
		val result = Array(size) { CharArray(size) }

		for (i in 0 until size) {
			for (j in 0 until size) {
				result[j][size - 1 - i] = rows[i][j]
			}
		}

		return result.joinToString("/") { it.concatToString() }
	}

	private fun flipGrid(grid: String): String {
		return grid.split("/").reversed().joinToString("/")
	}

	private fun enhance(rotatedRules: Map<String, String>, iterations: Int): List<String> {
		var grid = listOf(".#.", "..#", "###")

		val patternBuilder = StringBuilder(16)

		repeat(iterations) { _ ->
			val size = if (grid.size % 2 == 0) 2 else 3
			val newGrid = mutableListOf<String>()

			for (j in grid.indices step size) {
				val row = mutableListOf<String>()
				for (k in grid.indices step size) {
					// Build pattern string efficiently
					patternBuilder.clear()
					for (m in 0 until size) {
						if (m > 0) patternBuilder.append('/')
						patternBuilder.append(grid[j + m].substring(k, k + size))
					}

					val enhanced = rotatedRules[patternBuilder.toString()]!!
					row.add(enhanced)
				}

				val newRows = Array(row[0].split("/").size) { StringBuilder() }
				for (r in row) {
					val split = r.split("/")
					for (l in split.indices) {
						newRows[l].append(split[l])
					}
				}
				newGrid.addAll(newRows.map { it.toString() })
			}
			grid = newGrid
		}
		return grid
	}
}