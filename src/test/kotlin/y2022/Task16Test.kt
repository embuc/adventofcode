package y2022

import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import kotlin.test.assertEquals

class Task16Test {
	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2022/2022_16.txt")
		assertEquals(2124, Task16(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2022/2022_16.txt")
		assertEquals(2775, Task16(input).b())
	}

}