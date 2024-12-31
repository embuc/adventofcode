package y2024

import org.junit.jupiter.api.Assertions.assertEquals
import utils.readInputAsListOfStrings
import kotlin.test.Test

class Task7Test {

	val smallInput = """
		190: 10 19
	""".trimIndent()
	val smallInput2 = """
		29: 10 19
		""".trimIndent()
	val smallInput3 = """
		190: 10 19
		29: 10 19
	""".trimIndent()
	val smallInput4 = """
		3267: 81 40 27
	""".trimIndent()
	val smallInput5 = """
		292: 11 6 16 20
	""".trimIndent()
	val testInput = """
		190: 10 19
		3267: 81 40 27
		83: 17 5
		156: 15 6
		7290: 6 8 6 15
		161011: 16 10 13
		192: 17 8 14
		21037: 9 7 18 13
		292: 11 6 16 20
	""".trimIndent()

	@Test
	fun a_smallInput() {
		assertEquals(190L, Task7(smallInput.split("\n").map { it.trim() }).a())
		assertEquals(29L, Task7(smallInput2.split("\n").map { it.trim() }).a())
		assertEquals(219L, Task7(smallInput3.split("\n").map { it.trim() }).a())
		assertEquals(3267L, Task7(smallInput4.split("\n").map { it.trim() }).a())
		assertEquals(292L, Task7(smallInput5.split("\n").map { it.trim() }).a())
	}

	@Test
	fun a_testInput() {
		val task = Task7(testInput.split("\n").map { it.trim() })
		assertEquals(3749L, task.a())
	}

	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_7.txt")
		val task = Task7(input)
		assertEquals(3119088655389, task.a())
	}

	@Test
	fun b_testInput() {
		assertEquals(11387L, Task7(testInput.split("\n").map { it.trim() }).b())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_7.txt")
		val task = Task7(input)
		assertEquals(264184041398847, task.b())
	}
}


