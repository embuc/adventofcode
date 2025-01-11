package y2016

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task25Test {

	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2016/2016_25.txt")
		assertEquals(0, Task25(input).a())
	}

	@Test
	fun b() {
	}
}