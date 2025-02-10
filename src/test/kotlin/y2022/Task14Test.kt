package y2022

import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import kotlin.test.assertEquals

class Task14Test {
	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2022/2022_14.txt")
		assertEquals(0, Task14(input).a())
	}

	@Test
	fun b() {
	}

}