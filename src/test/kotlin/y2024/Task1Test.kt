package y2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task1Test {

	@Test
	fun a_smallInput() {
		val inputStr = "3   4\n" +
						"4   3\n" +
						"2   5\n" +
						"1   3\n" +
						"3   9\n" +
						"3   3"
		val input = inputStr.split("\n")
		val task = Task1(input)
		assertEquals(11, task.a())
	}

	@Test
	fun a() {
		val lines = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_1.txt")
		val task = Task1(lines)
		assertEquals(1530215, task.a())
	}

	@Test
	fun b_smallInput() {
		val inputStr = "3   4\n" +
						"4   3\n" +
						"2   5\n" +
						"1   3\n" +
						"3   9\n" +
						"3   3"
		val input = inputStr.split("\n")
		val task = Task1(input)
		assertEquals(31, task.b())
	}

	@Test
	fun b() {
		val lines = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_1.txt")
		val task = Task1(lines)
		assertEquals(26800609, task.b())
	}
}