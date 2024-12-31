package y2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task6Test {

	val smallInput = """
		....#.....
		.........#
		..........
		..#.......
		.......#..
		..........
		.#..^.....
		........#.
		#.........
		......#...
	""".trimIndent()

	val smallInput2 = """
		....#.....
		...#.#....
		.#..^.....
	""".trimIndent()

	val smallInput3 = """
		.....
		..#..
		..^.#
		...#.
		.....
	""".trimIndent()

	val smallInput4 = """
		....#
		...#.
		..^..
		.....
		..#..
	""".trimIndent()

	val smallInput5 = """
		.....
		..#..
		#.^..
		.....
		.....
	""".trimIndent()

	val smallInput6 = """
		########
		#......#
		...^...#
		########
	""".trimIndent()
	val smallInput7 = """
		##..
		...#
		....
		^.#.
	""".trimIndent()

	val smallInput8 = """
		.#...
		....#
		.....
		.^.#.
		#....
		..#..
	""".trimIndent()
	val smallInput9 = """
		.#.
		..#
		#^.
		...
	""".trimIndent()

	val smallInput10 = """
		.#.
		#.#
		#^.
		...
	""".trimIndent()

	val smallInput11 = """
		###
		#.#
		.^#
		###
	""".trimIndent()

	val smallInput12 = """
		#.....
		.....#
		^.#..#
		....#.
	""".trimIndent()

	@Test
	fun a_smallInput() {
		val input = smallInput.split("\n")
		val task = Task6(input)
		assertEquals(41, task.a())
	}

	@Test
	fun a_smallInput2() {
		val input = smallInput2.split("\n")
		val task = Task6(input)
		assertEquals(2, task.a())
	}

	@Test
	fun a_smallInput3() {
		val input = smallInput3.split("\n")
		val task = Task6(input)
		assertEquals(4, task.a())
	}

	@Test
	fun a_smallInput4() {
		val input = smallInput4.split("\n")
		val task = Task6(input)
		assertEquals(3, task.a())
	}

	@Test
	fun a_smallInput5() {
		val input = smallInput5.split("\n")
		val task = Task6(input)
		assertEquals(3, task.a())
	}

	@Test
	fun a_smallInput6() {
		val input = smallInput6.split("\n")
		val task = Task6(input)
		assertEquals(11, task.a())
	}

	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_6.txt")
		val task = Task6(input)
		assertEquals(5305, task.a())
	}

	@Test
	fun b_smallInput() {
		val input = smallInput.split("\n")
		val task = Task6(input)
		assertEquals(6, task.b())
	}

	@Test
	fun b_smallInput6() {
		val input = smallInput6.split("\n")
		val task = Task6(input)
		assertEquals(6, task.b())
	}

	@Test
	fun b_smallInput7() {
		val input = smallInput7.split("\n")
		val task = Task6(input)
		assertEquals(0, task.b())
	}

	@Test
	fun b_smallInput8() {
		val input = smallInput8.split("\n")
		val task = Task6(input)
		assertEquals(3, task.b())
	}

	@Test
	fun b_smallInput9() {
		val input = smallInput9.split("\n")
		val task = Task6(input)
		assertEquals(1, task.b())
	}

	@Test
	fun b_smallInput10() {
		val input = smallInput10.split("\n")
		val task = Task6(input)
		assertEquals(1, task.b())
	}

	@Test
	fun b_smallInput11() {
		val input = smallInput11.split("\n")
		val task = Task6(input)
		assertEquals(1, task.b())
	}

	@Test
	fun b_smallInput12() {
		val input = smallInput12.split("\n")
		val task = Task6(input)
		assertEquals(1, task.b())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_6.txt")
		val task = Task6(input)
		assertEquals(2143, task.b())
	}
}