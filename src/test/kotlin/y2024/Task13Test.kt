package y2024

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import utils.readInputAsListOfStrings
import utils.readTestInputAsListOfStrings

class Task13Test {

	val small_input = """
		Button A: X+94, Y+34
		Button B: X+22, Y+67
		Prize: X=8400, Y=5400
	""".trimIndent().lines()

	val test_input = """
		Button A: X+94, Y+34
		Button B: X+22, Y+67
		Prize: X=8400, Y=5400

		Button A: X+26, Y+66
		Button B: X+67, Y+21
		Prize: X=12748, Y=12176

		Button A: X+17, Y+86
		Button B: X+84, Y+37
		Prize: X=7870, Y=6450

		Button A: X+69, Y+23
		Button B: X+27, Y+71
		Prize: X=18641, Y=10279
	""".trimIndent().lines()

	@Test
	fun a() {
		assertEquals(280, Task13(small_input).a())
		assertEquals(480, Task13(test_input).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_13.txt")
		assertEquals(39748L, Task13(input).a())
	}



	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_13.txt")
		assertEquals(74478585072604L, Task13(input).b())
	}
}