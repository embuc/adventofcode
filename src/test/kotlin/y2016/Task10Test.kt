package y2016

import utils.readInputAsString
import kotlin.test.Test
import kotlin.test.assertEquals

class Task10Test {

	val testInput = """
		value 5 goes to bot 2
		bot 2 gives low to bot 1 and high to bot 0
		value 3 goes to bot 1
		bot 1 gives low to output 1 and high to bot 0
		bot 0 gives low to output 2 and high to output 0
		value 2 goes to bot 2
			""".trim().lines().map { it.trim() }

	@Test
	fun a() {
//		assertEquals("2", Task10(testInput,2,5).a())
		val input = readInputAsString("~/git/aoc-inputs/2016/2016_10.txt")
		assertEquals(157, Task10(input.lines(), 17,61).a())
	}

	@Test
	fun b() {
		val input = readInputAsString("~/git/aoc-inputs/2016/2016_10.txt")
		assertEquals(157, Task10(input.lines(), 17,61).b())
	}
}