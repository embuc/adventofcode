package y2017

import org.junit.jupiter.api.Test
import utils.readInputAsString
import kotlin.test.assertEquals

class Task7Test {

	@Test
	fun a() {
		val input = readInputAsString("~/git/aoc-inputs/2017/2017_7.txt")
		assertEquals("fbgguv", Task7(input.split("\n").map { it.trim() }).a())
	}

	@Test
	fun b() {
		val input = readInputAsString("~/git/aoc-inputs/2017/2017_7.txt")
		assertEquals(1864, Task7(input.split("\n").map { it.trim() }).b())
	}
}