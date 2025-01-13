package y2017

import org.junit.jupiter.api.Test
import utils.readInputAsString
import kotlin.test.assertEquals

class Task6Test {

	@Test
	fun a() {
		val input = readInputAsString("~/git/aoc-inputs/2017/2017_6.txt")
		assertEquals(12841, Task6(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsString("~/git/aoc-inputs/2017/2017_6.txt")
		assertEquals(8038, Task6(input).b())
	}
}