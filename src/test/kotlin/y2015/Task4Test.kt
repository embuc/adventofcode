package y2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsString
import kotlin.test.Ignore

// comment ignore annotation to run tests, they are slowish
class Task4Test {

	@Ignore
	@Test
	fun a() {
		val input = readInputAsString("~/git/aoc-inputs/2015/2015_4.txt")
		assertEquals(282749, Task4(input).a())
	}

	@Ignore
	@Test
	fun b() {
		val input = readInputAsString("~/git/aoc-inputs/2015/2015_4.txt")
		assertEquals(9962624, Task4(input).b())
	}
}