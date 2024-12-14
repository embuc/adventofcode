package y2024

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class Task11Test {

	val small_input = "0 1 10 99 999"
	val test_input = "125 17"

	@Test
	fun a_small() {
		assertEquals(7L, Task11(small_input).a(1))
	}

	@Test
	fun a_test() {
//	    "2097446912 14168 4048 2 0 2 4 40 48 2024 40 48 80 96 2 8 6 7 6 0 3 2",
		assertEquals(22L, Task11(test_input).a(6))
		assertEquals(55312L, Task11(test_input).a(25))
	}


	@Test
	fun a() {
		val input = utils.readInputAsString("~/git/aoc-inputs/2024/2024_11.txt")
		val task = Task11(input.trim())
		assertEquals(218079L, task.a())
	}

	@Test
	fun b_small() {
		assertEquals(149161030616311L, Task11(small_input).a(75))
	}

	@Test
	fun b() {
		val input = utils.readInputAsString("~/git/aoc-inputs/2024/2024_11.txt")
		val task = Task11(input)
		assertEquals(259755538429618L, task.b())
	}
}