package y2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task22Test {

	@Test
	fun a_test() {
		val input = listOf(
			"..#",
			"#..",
			"..."
		)
		assertEquals(5587, Task22(input).a())
	}

	@Test
	fun b_test() {
		val input = listOf(
			"..#",
			"#..",
			"..."
		)
		assertEquals(2511944, Task22(input).b())
	}

	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2017/2017_22.txt")
		assertEquals(5447, Task22(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2017/2017_22.txt")
		assertEquals(5447, Task22(input).b())
	}
}