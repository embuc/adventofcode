// @author embuc
package utils

import java.io.File
import java.io.FileInputStream

fun readInputAsString(task: String): String {
//	val inputStream = File("src/main/resources/$task").inputStream()
	return getFileInputStream(task).bufferedReader().use { it.readText() }.trimIndent()
}

fun getFileInputStream(relativePath: String): FileInputStream {
	val homeDirectory = System.getProperty("user.home")
	val path = relativePath.replaceFirst("~", homeDirectory)
	return FileInputStream(path)
}

fun readInputAsListOfStrings(fileName: String): List<String> {
	val inputStream = getFileInputStream(fileName)
	return inputStream.bufferedReader().use { it.readLines() }
}

fun readTestInputAsListOfStrings(fileName: String): List<String> {
	val inputStream = Thread.currentThread().contextClassLoader.getResourceAsStream(fileName)
		?: throw IllegalArgumentException("File not found")
	return inputStream.bufferedReader().use { it.readLines() }.map { it.trim() }
}

fun <T> Collection<T>.allEquals(value: T): Boolean {
	for (i in this) if (i != value) return false
	return true
}

fun <T> Collection<T>.forEachPair(action: (T, T) -> Unit) {
	val list = this.toList()
	for (i in list.indices) {
		for (j in i + 1 until list.size) {
			action(list[i], list[j])
		}
	}
}

//positive modulus of an integer.
infix fun Int.pm(other: Int): Int {
	val mod = this % other
	return if (mod < 0) mod + other else mod
}

fun Collection<Long>.lcm() = reduce { a, b -> lcm(a, b) }

fun lcm(a: Long, b: Long): Long = (a * b) / gcd(a, b)

fun gcd(a: Long, b: Long): Long = if (a == 0L) b else gcd(b % a, a)

fun IntRange.size() = last - start + 1

fun die(): Nothing = throw RuntimeException("boom crash bang")

operator fun <T> List<T>.component6(): T {
	return this[5]
}

fun solveLinearEquations2x2(
	a1: Double, b1: Double, c1: Double,
	a2: Double, b2: Double, c2: Double
): Pair<Long, Long>? {
	// Calculate the determinant of the coefficient matrix
	val det = a1 * b2 - a2 * b1

	// If the determinant is zero, the system has no unique solution
	if (det == 0.0) return null

	val detX = c1 * b2 - c2 * b1
	val detY = a1 * c2 - a2 * c1

	val x = detX / det
	val y = detY / det
	// Check if x and y are positive integers
	if (x > 0 && y > 0 && x % 1 == 0.0 && y % 1 == 0.0) {
		return x.toLong() to y.toLong()
	}

	// If x or y is not a positive integer, return no solution
	return null
}

//my grid stuff
fun toGrid(input: List<String>): Array<Array<Triple<Int, Int, Char>>> {
	return Array(input.size) { i ->
			Array(input[i].length) { j ->
				Triple(i, j, input[i][j])
		}
	}
}

fun printGrid(grid: Array<Array<Triple<Int, Int, Char>>>) {
	for (i in grid.indices) {
		for (j in grid[i].indices) {
			print(grid[i][j].third)
		}
		println()
	}
}

fun printGrid(grid: List<CharArray>) {
	grid.forEach {
		println(it)
	}
}
fun printGrid(grid: Array<CharArray>) {
	grid.forEach {
		println(it)
	}
}

fun resetGrid(grid: Array<Array<Triple<Int, Int, Char>>>) {
	for (i in grid.indices) {
		for (j in grid[i].indices) {
			grid[i][j] = Triple(i, j, '.')
		}
	}
}

fun countChar(grid: Array<Array<Triple<Int, Int, Char>>>, c: Char): Int {
	var count = 0
	for (i in grid.indices) {
		for (j in grid[i].indices) {
			if (grid[i][j].third == c) count++
		}
	}
	return count
}

fun countChar(grid: Array<Array<Triple<Int, Int, Char>>>, condition: (Char) -> Boolean): Int {
	var count = 0
	for (i in grid.indices) {
		for (j in grid[i].indices) {
			if (condition(grid[i][j].third)) count++
		}
	}
	return count
}

fun isInsideGrid(
	grid: Array<Array<Triple<Int, Int, Char>>>,
	x: Int,
	y: Int
) = x in grid.indices && y in grid[0].indices


