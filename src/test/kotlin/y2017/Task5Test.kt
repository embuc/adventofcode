package y2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsString

class Task5Test {

	@Test
	fun a() {
		val testInput = "0\n3\n0\n1\n-3"
		assertEquals(5, Task5(testInput).a())
        val input = readInputAsString("~/git/aoc-inputs/2017/2017_5.txt")
		assertEquals(325922, Task5(input).a())

	}

	@Test
	fun b() {
	}
}