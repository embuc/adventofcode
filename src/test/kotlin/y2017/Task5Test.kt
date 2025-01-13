package y2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsString

class Task5Test {

	@Test
	fun a() {
        val input = readInputAsString("~/git/aoc-inputs/2017/2017_5.txt")
		assertEquals(325922, Task5(input.split("\n").map { it.toInt() }.toIntArray()).a())

	}

	@Test
	fun b() {
		val input = readInputAsString("~/git/aoc-inputs/2017/2017_5.txt")
		assertEquals(24490906, Task5(input.split("\n").map { it.toInt() }.toIntArray()).b())
	}
}