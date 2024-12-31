package y2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task14Test {

	val testInput = """
        Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
        Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
    """.trimIndent().lines().map { it.trim() }

	@Test
	fun a() {
		assertEquals(1120, Task14(testInput, 1000).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_14.txt")
		assertEquals(2660, Task14(input, 2503).a())
	}

	@Test
	fun b() {
		assertEquals(689, Task14(testInput, 1000).b())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_14.txt")
		assertEquals(1256, Task14(input, 2503).b())
	}
}