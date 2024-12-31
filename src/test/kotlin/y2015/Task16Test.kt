package y2015

import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import kotlin.test.assertEquals

class Task16Test {

	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_16.txt")
		assertEquals(40, Task16(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_16.txt")
		assertEquals(241, Task16(input).b())
	}
}