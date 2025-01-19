package y2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task21Test {

	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2017/2017_21.txt")
		assertEquals(110, Task21(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2017/2017_21.txt")
		assertEquals(0, Task21(input).b())
	}

	@Test
	fun rotateGrid() {
		val input = ".#./..#/###"
		val expected90 = "#../#.#/##."
		val expected180 = "###/#../.#."
		val expected270 = ".##/#.#/..#"
		assertEquals(input, Task21(emptyList()).rotateGrid(input, 0))
		assertEquals(expected90, Task21(emptyList()).rotateGrid(input, 1))
		assertEquals(expected180, Task21(emptyList()).rotateGrid(input, 2))
		assertEquals(expected270, Task21(emptyList()).rotateGrid(input, 3))
	}

	@Test
	fun flipGrid() {
		val input = ".#./..#/###"
		val expectedH = "###/..#/.#."
		val expectedV = ".#./#../###"
		assertEquals(expectedV, Task21(emptyList()).flipGrid(input, 0))
		assertEquals(expectedH, Task21(emptyList()).flipGrid(input, 1))
	}
}