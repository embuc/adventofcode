package y2024

import org.junit.jupiter.api.Assertions.assertEquals
import utils.readInputAsListOfStrings
import utils.readTestInputAsListOfStrings
import kotlin.test.Test

class Task7Test {

	val small_input = """
		190: 10 19
	""".trimIndent();
	val small_input2 = """
		29: 10 19
		""".trimIndent();
	val small_input3 = """
		190: 10 19
		29: 10 19
	""".trimIndent();
	val small_input4 = """
		3267: 81 40 27
	""".trimIndent();
	val small_input5 = """
		292: 11 6 16 20
	""".trimIndent();
	val test_input = """
		190: 10 19
		3267: 81 40 27
		83: 17 5
		156: 15 6
		7290: 6 8 6 15
		161011: 16 10 13
		192: 17 8 14
		21037: 9 7 18 13
		292: 11 6 16 20
	""".trimIndent();

	@Test
	fun a_small_input() {
		assertEquals(190, Task7(small_input.split("\n").map { it.trim() }).a())
		assertEquals(29, Task7(small_input2.split("\n").map { it.trim() }).a())
		assertEquals(219, Task7(small_input3.split("\n").map { it.trim() }).a())
		assertEquals(3267, Task7(small_input4.split("\n").map { it.trim() }).a())
		assertEquals(292, Task7(small_input5.split("\n").map { it.trim() }).a())
	}

	@Test
	fun a_test_input() {
		val task = Task7(test_input.split("\n").map { it.trim() })
		assertEquals(3749, task.a())
	}
	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_7.txt")
		val task = Task7(input)
		assertEquals(3119088655389, task.a())
	}

	@Test
	fun b_test_input() {
		assertEquals(11387, Task7(test_input.split("\n").map { it.trim() }).b())
	}

	@Test
	fun b() {
	}
}


