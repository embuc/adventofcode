package y2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task4Test {
	@Test
	fun a() {
		var input = readInputAsListOfStrings("~/git/aoc-inputs/2022/2022_4.txt")
		assertEquals(305, Task4(input).a())
	}

	@Test
	fun b() {
		var input = readInputAsListOfStrings("~/git/aoc-inputs/2022/2022_4.txt")
		assertEquals(811, Task4(input).b())
	}

}