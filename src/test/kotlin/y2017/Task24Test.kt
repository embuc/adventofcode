package y2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task24Test {

	val testInput =
		"""
		0/2
        2/2
        2/3
        3/4
        3/5
        0/1
        10/1
        9/10
		""".trim().lines().map{it.trim()}

	@Test
	fun a() {
		assertEquals(31, Task24(testInput).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2017/2017_24.txt")
		//158 too low
		assertEquals(1868, Task24(input).a())

	}

	@Test
	fun b() {
	}
}