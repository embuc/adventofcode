package y2022

import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import kotlin.test.assertEquals

class Task11Test {

	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2022/2022_11.txt")
		assertEquals(50616, Task11(input).a())
	}

	@Test
	fun b() {
	}

}