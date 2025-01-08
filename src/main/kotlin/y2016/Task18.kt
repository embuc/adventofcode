package y2016

import Task
import utils.countChar

//--- Day 18: Like a Rogue ---
class Task18(val input: String, val rows: Int) : Task {

	override fun a(): Any {
		val grid = mutableListOf(input)
		var currRow = input
		repeat(rows - 1) {
			val newRow = StringBuilder()
			for (i in currRow.indices) {
				val c1 = if (i == 0) '.' else currRow[i - 1]
				val c2 = currRow[i]
				val c3 = if (i == currRow.length - 1) '.' else currRow[i + 1]
				newRow.append(getTrap(c1, c2, c3))
			}
			currRow = newRow.toString()
			grid.add(currRow)
		}
		return countChar(grid, '.')
	}

	private fun getTrap(c1: Char, c2: Char, c3: Char): Char {
		return if ((c1 == '^' && c2 == '^' && c3 == '.') ||
			(c1 == '.' && c2 == '^' && c3 == '^') ||
			(c1 == '^' && c2 == '.' && c3 == '.') ||
			(c1 == '.' && c2 == '.' && c3 == '^')
		) {
			'^'
		} else {
			'.'
		}
	}

	override fun b(): Any {
		var count = 0L
		val currRow = StringBuilder(input)
		count += input.sumOf { if (it == '.') 1L else 0L }
		val newRow = StringBuilder()
		repeat(rows - 1) {
			for (i in currRow.indices) {
				val c1 = if (i == 0) '.' else currRow[i - 1]
				val c2 = currRow[i]
				val c3 = if (i == currRow.length - 1) '.' else currRow[i + 1]
				newRow.append(getTrap(c1, c2, c3))
			}
			currRow.clear()
			currRow.append(newRow)
			count += newRow.sumOf { if (it == '.') 1L else 0L }
			newRow.clear()
		}
		return count
	}
}