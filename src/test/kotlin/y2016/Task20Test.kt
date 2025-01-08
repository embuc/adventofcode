package y2016

import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import kotlin.test.assertEquals

class Task20Test {

	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2016/2016_20.txt")
		assertEquals(31053880L, Task20(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2016/2016_20.txt")
		assertEquals(117L, Task20(input).b())
	}
}