package y2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task13Test {

	@Test
	fun a() {
		assertEquals(24, Task13(listOf("0: 3", "1: 2", "4: 4", "6: 4")).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2017/2017_13.txt")
		assertEquals(1528, Task13(input).a())
	}

	@Test
	fun b() {
	}
}