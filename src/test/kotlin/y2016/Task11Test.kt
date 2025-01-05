package y2016

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsString

class Task11Test {

	@Test
	fun a() {
		val testInput = """
            The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.
            The second floor contains a hydrogen generator.
            The third floor contains a lithium generator.
            The fourth floor contains nothing relevant.
        """.trimIndent().lines()
//		assertEquals(11, Task11(testInput).a())
		val input = readInputAsString("~/git/aoc-inputs/2016/2016_11.txt")
		assertEquals(37, Task11(input.lines()).a())
	}

	@Test
	fun b() {
		val input = readInputAsString("~/git/aoc-inputs/2016/2016_11.txt")
		assertEquals(37, Task11(input.lines()).a())
	}
}