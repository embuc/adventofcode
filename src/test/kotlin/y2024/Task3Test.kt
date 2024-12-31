package y2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsString

class Task3Test {
	val smallInput = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
	val smallInput_2 = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"

	@Test
	fun a_smallInput() {
		val Task = Task3(smallInput)
		assertEquals(161, Task.a())
	}

	@Test
	fun a() {
		val input = readInputAsString("~/git/aoc-inputs/2024/2024_3.txt")
		val Task = Task3(input)
		assertEquals(181345830, Task.a())
	}

	@Test
	fun b_smallInput2() {
		val Task = Task3(smallInput_2)
		assertEquals(48, Task.b())
	}

	@Test
	fun b() {
		val input = readInputAsString("~/git/aoc-inputs/2024/2024_3.txt")
		val Task = Task3(input)
		assertEquals(98729041, Task.b())
	}
}