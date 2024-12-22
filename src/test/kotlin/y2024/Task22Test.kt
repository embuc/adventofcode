package y2024

import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import kotlin.test.assertEquals

class Task22Test {

	val test_input = """
		1
        10
        100
        2024
    """.trimIndent().lines()

	val test_input_b = """
		1
        2
        3
        2024
    """.trimIndent().lines()

	@Test
	fun a() {
		assertEquals(37327623, Task22(test_input).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_22.txt")
		assertEquals(14476723788L, Task22(input).a())
	}

	@Test
	fun b() {
		assertEquals(23L, Task22(test_input_b).b())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_22.txt")
		assertEquals(1630L, Task22(input).b())
	}
}