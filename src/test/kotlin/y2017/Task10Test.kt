package y2017

import org.junit.jupiter.api.Test
import utils.readInputAsString
import kotlin.test.assertEquals

class Task10Test {

	@Test
	fun a() {
		assertEquals(12, Task10(listOf(3, 4, 1, 5), 4).a())
		val input = readInputAsString("~/git/aoc-inputs/2017/2017_10.txt")
		assertEquals(3770, Task10(input.split(",").map { it.toInt() }, 255).a())
	}

	@Test
	fun b() {
	}
}