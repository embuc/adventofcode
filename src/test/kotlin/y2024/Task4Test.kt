package y2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task4Test {

	val smallInput =
		"""
		MMMSXXMASM
		MSAMXMSMSA
		AMXSXMAAMM
		MSAMASMSMX
		XMASAMXAMM
		XXAMMXXAMA
		SMSMSASXSS
		SAXAMASAAA
		MAMMMXMMMM
		MXMXAXMASX
		"""

	@Test
	fun a_smallInput() {
		val input = smallInput.split("\n")
		val task = Task4(input)
		assertEquals(18, task.a())
	}

	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_4.txt")
		val task = Task4(input)
		assertEquals(2583, task.a())
	}

	@Test
	fun b_smallInput() {
		val input = smallInput.split("\n")
		val task = Task4(input)
		assertEquals(9, task.b())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_4.txt")
		val task = Task4(input)
		assertEquals(1978, task.b())
	}
}