package y2022

import org.junit.jupiter.api.Test
import utils.readInputAsString
import kotlin.test.assertEquals

class Task17Test {
	@Test
	fun a() {
		val input = readInputAsString("~/git/aoc-inputs/2022/2022_17.txt")
		assertEquals(2124, Task17(input).a())
	}

	@Test
	fun b() {
	}

}