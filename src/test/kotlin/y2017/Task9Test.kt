package y2017

import org.junit.jupiter.api.Test
import utils.readInputAsString
import kotlin.test.assertEquals

class Task9Test {

	@Test
	fun a() {
		val input = readInputAsString("~/git/aoc-inputs/2017/2017_9.txt")
		assertEquals(14421, Task9(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsString("~/git/aoc-inputs/2017/2017_9.txt")
		assertEquals(6817, Task9(input).b())
	}
}