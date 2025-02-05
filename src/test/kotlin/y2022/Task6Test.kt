package y2022

import org.junit.jupiter.api.Test
import utils.readInputAsString
import kotlin.test.assertEquals

class Task6Test {
	@Test
	fun a() {
		val input = readInputAsString("~/git/aoc-inputs/2022/2022_6.txt")
		assertEquals(1892, Task6(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsString("~/git/aoc-inputs/2022/2022_6.txt")
		assertEquals(2313, Task6(input).b())
	}
}