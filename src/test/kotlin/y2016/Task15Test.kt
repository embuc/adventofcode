package y2016

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task15Test {
	val testInput = """
	Disc #1 has 5 positions; at time=0, it is at position 4.
	Disc #2 has 2 positions; at time=0, it is at position 1.
	""".trim().lines().map { it.trim() }

	@Test
	fun a() {
		assertEquals(5, Task15(testInput).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2016/2016_15.txt")
		assertEquals(16824, Task15(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2016/2016_15.txt")
		assertEquals(3543984, Task15(input).b())
	}
}