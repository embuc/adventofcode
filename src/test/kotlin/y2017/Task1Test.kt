package y2017

import org.junit.jupiter.api.Test
import utils.readInputAsString
import kotlin.test.assertEquals

class Task1Test {

	@Test
	fun a() {
		val input = readInputAsString("~/git/aoc-inputs/2017/2017_1.txt")
		assertEquals(1044, Task1(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsString("~/git/aoc-inputs/2017/2017_1.txt")
		assertEquals(1054, Task1(input).b())
	}
}