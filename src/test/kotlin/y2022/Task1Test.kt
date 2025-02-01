package y2022

import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import kotlin.test.assertEquals

class Task1Test {

	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2022/2022_1.txt")
        assertEquals(70613, Task1(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2022/2022_1.txt")
        assertEquals(205805, Task1(input).b())
	}
}