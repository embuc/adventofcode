package y2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task5Test {

	val smallInput1 = "xazeggov"
	val smallInput2 = "aeiouaeiouaeiouu"
	val testInput1 = "aaa"
	val testInput2 = "ugknbfddgicrmopn"
	val testInput3 = "jchzalrnumimnmhp"
	val testInput4 = "haegwjzuvuyypxyu"
	val testInput5 = "dvszwmarrgswjxmb"

	val smallInput_b0 = "dsxababdsyplpå"
	val smallInput_b1 = "qjhvhtzxzqqjkmpb"
	val smallInput_b2 = "xxyxx"
	val smallInput_b3 = "uurcxstgmygtbstg"
	val smallInput_b4 = "ieodomkazucvgmuy"

	@Test
	fun a() {
		assertEquals(1, Task5(listOf(smallInput1)).a())
		assertEquals(1, Task5(listOf(smallInput2)).a())
		assertEquals(1, Task5(listOf(testInput1)).a())
		assertEquals(1, Task5(listOf(testInput2)).a())
		assertEquals(0, Task5(listOf(testInput3)).a())
		assertEquals(0, Task5(listOf(testInput4)).a())
		assertEquals(0, Task5(listOf(testInput5)).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_5.txt")
		assertEquals(236, Task5(input).a())
	}

	@Test
	fun b() {
		assertEquals(1, Task5(listOf(smallInput_b0)).b())
		assertEquals(1, Task5(listOf(smallInput_b1)).b())
		assertEquals(1, Task5(listOf(smallInput_b2)).b())
		assertEquals(0, Task5(listOf(smallInput_b3)).b())
		assertEquals(0, Task5(listOf(smallInput_b4)).b())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_5.txt")
		assertEquals(51, Task5(input).b())
	}
}