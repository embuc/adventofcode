package y2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsString

class Task25Test {

	@Test
	fun a() {
		val input = readInputAsString("~/git/aoc-inputs/2015/2015_25.txt")
	    assertEquals(2650453L, Task25(input).a())
	}

	@Test
	fun b() {
	}
}