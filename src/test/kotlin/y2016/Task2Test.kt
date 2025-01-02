package y2016

import org.junit.jupiter.api.Test
import utils.readInputAsString
import kotlin.test.assertEquals

class Task2Test {

	@Test
	fun a() {
		val input = readInputAsString("~/git/aoc-inputs/2016/2016_2.txt")
		assertEquals("78293", Task2(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsString("~/git/aoc-inputs/2016/2016_2.txt")
		assertEquals("AC8C8", Task2(input).b())
	}
}