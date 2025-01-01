package y2015

import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import kotlin.test.assertEquals

class Task24Test {

	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_24.txt")
		assertEquals(10723906903L, Task24(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_24.txt")
		assertEquals(74850409L, Task24(input).b())
	}
}