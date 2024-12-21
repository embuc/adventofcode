package y2024

import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import kotlin.test.assertEquals

class Task21Test {

    val test_input = """
	029A
	980A
	179A
	456A
	379A
	""".trimIndent().lines()

	@Test
	fun a() {
		assertEquals(126384L, Task21(test_input).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_21.txt")
		assertEquals(123096L, Task21(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_21.txt")
		assertEquals(154517692795352L, Task21(input).b())
	}
}