package y2022

import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import kotlin.test.assertEquals

class Task9Test {
	val testInput = """
		R 4
		U 4
		L 3
		D 1
		R 4
		D 1
		L 5
		R 2
		""".trim().split("\n").map { it.trim() }.toList()

	val testInputB = """
		R 5
		U 8
		L 8
		D 3
		R 17
		D 10
		L 25
		U 20
		""".trim().split("\n").map { it.trim() }.toList()

	@Test
	fun a() {
		assertEquals(13, Task9(testInput).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2022/2022_9.txt")
		assertEquals(6406, Task9(input).a())
	}

	@Test
	fun b() {
		assertEquals(36, Task9(testInputB).b())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2022/2022_9.txt")
		assertEquals(6406, Task9(input).b())
	}

}