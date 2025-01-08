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
		val currRow = input.toCharArray()
		val newRow = CharArray(currRow.size)
		count += currRow.count { it == '.' }

		repeat(rows - 1) {
			for (i in currRow.indices) {
				val c1 = if (i == 0) '.' else currRow[i - 1]
				val c2 = currRow[i]
				val c3 = if (i == currRow.size - 1) '.' else currRow[i + 1]
				newRow[i] = getTrap(c1, c2, c3)
				if (newRow[i] == '.') count++
			}
			System.arraycopy(newRow, 0, currRow, 0, newRow.size)
		}
		return count
	}
}