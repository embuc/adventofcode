package y2015

import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import kotlin.test.assertEquals

class Task22Test {

	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_22.txt")
		assertEquals(1269, Task22(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_22.txt")
		assertEquals(1309, Task22(input).b())
	}
}