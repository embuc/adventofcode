package y2024

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Task10Test {

	val small_input = """
		...0...
		...1...
		...2...
		6543456
		7.....7
		8.....8
		9.....9
	""".trimIndent().lines()

	val small_input2 = """
		..90..9
		...1.98
		...2..7
		6543456
		765.987
		876....
		987....
	""".trimIndent().lines()

	val test_input = """
		89010123
		78121874
		87430965
		96549874
		45678903
		32019012
		01329801
		10456732
	""".trimIndent().lines()

	@Test
	fun a_small_input() {
		assertEquals(2, Task10(small_input).a())
	}

	@Test
	fun a_small_input2() {
		assertEquals(4, Task10(small_input2).a())
	}

	@Test
	fun a_test_input() {
		assertEquals(36, Task10(test_input).a())
	}

	@Test
	fun a() {
		val input = utils.readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_10.txt")
		val task = Task10(input)
		assertEquals(698, task.a())
	}

	@Test
	fun b() {
		val input = utils.readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_10.txt")
		val task = Task10(input)
		assertEquals(1436, task.b())
	}
}