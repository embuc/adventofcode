package y2016

import org.junit.jupiter.api.Test
import utils.readInputAsString
import kotlin.test.assertEquals

class Task1Test {

	@Test
	fun a() {
		val input = readInputAsString("~/git/aoc-inputs/2016/2016_1.txt")
		assertEquals(241, Task1(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsString("~/git/aoc-inputs/2016/2016_1.txt")
		assertEquals(116, Task1(input).b())
	}
}