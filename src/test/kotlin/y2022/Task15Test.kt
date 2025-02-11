package y2022

import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import kotlin.test.assertEquals

class Task15Test {
	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2022/2022_15.txt")
		assertEquals(5461729, Task15(input, 2_000_000).a())
	}

	@Test
	fun b() {
	}

}