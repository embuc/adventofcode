package y2023

import Task
import utils.readInputAsListOfStrings

object Task3:Task {

	override fun a(): Any {
		val lines = readInputAsListOfStrings("2023_3.txt")
		val matrix = lines.map { it.toCharArray().toTypedArray() }.toTypedArray()

		val validNumbers = findValidNumbers(matrix)

		println("Valid Numbers: $validNumbers")
		return validNumbers.sumOf { it.toInt() }
	}

	override fun b(): Any {
		val matrix = readInputAsListOfStrings("2023_3.txt").map { it.toCharArray().toTypedArray() }.toTypedArray()
		val products = findProducts(matrix)
		return products.sum()
	}

	fun findProducts(matrix: Array<Array<Char>>): MutableList<Long> {
		val products = mutableListOf<Long>()
		val usedNumbers = mutableSetOf<Pair<Int, Int>>() // Track used numbers by their starting coordinates

		for (row in matrix.indices) {
			for (col in matrix[row].indices) {
				if (matrix[row][col] == '*') {
					val product = getProductOfAdjacentNumbers(matrix, row, col, usedNumbers)
					if (product > 0) {
						products.add(product)
					}
				}
			}
		}
		return products
	}

	fun findValidNumbers(matrix: Array<Array<Char>>): List<String> {
		val validNumbers = mutableListOf<String>()

		for (row in matrix.indices) {
			var col = 0
			while (col < matrix[row].size) {
				if (matrix[row][col].isDigit()) {
					val (number, nextCol) = extractNumber(matrix, row, col)
					if (number.isNotBlank() && isNumberValid(matrix, row, col, nextCol)) {
						validNumbers.add(number)
					}
					col = nextCol
				} else {
					col++
				}
			}
		}
		return validNumbers
	}

	private fun isNumberValid(matrix: Array<Array<Char>>, row: Int, startCol: Int, endCol: Int): Boolean {
		for (col in startCol until endCol) {
			if (isAdjacentToSymbol(matrix, row, col)) {
				return true
			}
		}
		return false
	}


	private fun getProductOfAdjacentNumbers(matrix: Array<Array<Char>>, starRow: Int, starCol: Int, usedNumbers: MutableSet<Pair<Int, Int>>): Long {
		val directions = arrayOf(
			-1 to 0, 1 to 0, 0 to -1, 0 to 1,  // Up, Down, Left, Right
			-1 to -1, -1 to 1, 1 to -1, 1 to 1 // Diagonals
		)
		var product: Long = 1
		var numbersFound = 0

		for ((dr, dc) in directions) {
			val newRow = starRow + dr
			val newCol = starCol + dc
			if (newRow in matrix.indices && newCol in matrix[0].indices && matrix[newRow][newCol].isDigit()) {
				val startCoords = getStartCoordinates(matrix, newRow, newCol)
				if (startCoords !in usedNumbers) {
					val (number, _) = extractNumber(matrix, newRow, newCol)
					if (number.isNotBlank()) {
						if(numbersFound == 1) {
							println("Found $product and $number at $startCoords which produce product: ${product * number.toLong()}") // Debug
						}
						product *= number.toLong()
						numbersFound++
						usedNumbers.add(startCoords)
					}
				}
			}
		}

		return if (numbersFound > 1) product else 0 // Return product only if more than one number is found
	}

	fun isAdjacentToSymbol(matrix: Array<Array<Char>>, row: Int, col: Int): Boolean {
		val directions = arrayOf(
			-1 to 0, 1 to 0, 0 to -1, 0 to 1,  // Up, Down, Left, Right
			-1 to -1, -1 to 1, 1 to -1, 1 to 1 // Diagonals
		)
		val validChars = charArrayOf('.', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '\n').toList()

		for ((dr, dc) in directions) {
			val newRow = row + dr
			val newCol = col + dc
			if (newRow in matrix.indices && newCol in matrix[0].indices) {
				val adjacentChar = matrix[newRow][newCol]
				if (adjacentChar !in validChars) {
					return true
				}
			}
		}
		return false
	}

	fun extractNumber(matrix: Array<Array<Char>>, row: Int, col: Int): Pair<String, Int> {
		var startCol = col

		// Move left to the start of the number
		while (startCol > 0 && matrix[row][startCol - 1].isDigit()) {
			startCol--
		}

		val sb = StringBuilder()
		var currentCol = startCol

		// Extract the entire number moving right
		while (currentCol < matrix[row].size && matrix[row][currentCol].isDigit()) {
			sb.append(matrix[row][currentCol])
			currentCol++
		}

		return sb.toString() to currentCol // Return the number and the column index after the last digit
	}

	fun getStartCoordinates(matrix: Array<Array<Char>>, row: Int, col: Int): Pair<Int, Int> {
		var currentCol = col
		while (currentCol > 0 && matrix[row][currentCol - 1].isDigit()) {
			currentCol--
		}
		return row to currentCol
	}
}