package y2024

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import utils.readInputAsListOfStrings

class Task2Test {

	val testInput = "7 6 4 2 1\n" +
					 "1 2 7 8 9\n" +
					 "9 7 6 2 1\n" +
					 "1 3 2 4 5\n" +
					 "8 6 4 4 1\n" +
					 "1 3 6 7 9";

	val testInput2 = "1 6 4 2 1\n" +
			          "6 1 4 2 1\n" +
			    	  "5 1 2 3 4\n" +
					  "1 5 2 3 4";

	@Test
	fun a_small_test() {
		val input = testInput.split("\n")
		val task = Task2(input)
		assertEquals(2, task.a())
	}

	@Test
	fun a() {
		val input= readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_2.txt")
		val task = Task2(input)
		assertEquals(516, task.a())
	}

	@Test
	fun b_small_test() {
		val input = testInput.split("\n")
		val task = Task2(input)
		assertEquals(4, task.b())
	}

	@Test
	fun b_edge_cases() {
		val input = testInput2.split("\n")
		val task = Task2(input)
		assertEquals(4, task.b())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_2.txt")
		val task = Task2(input)
		assertEquals(561, task.b())
	}
}