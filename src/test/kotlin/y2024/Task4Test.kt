package y2024

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import utils.readInputAsListOfStrings

class Task4Test {

	val small_input =
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
		""";

	@Test
	fun a_small_input() {
		val input = small_input.split("\n")
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
	fun b_small_input() {
		val input = small_input.split("\n")
		val task = Task4(input)
		assertEquals(9, task.b())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_4.txt")
		val task = Task4(input)
		//1936 not correct :(
		assertEquals(0, task.b())
	}
}