package y2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task5Test {

	val small_input1 = "xazeggov"
	val small_input2 = "aeiouaeiouaeiouu"
	val test_input1 = "aaa"
	val test_input2 = "ugknbfddgicrmopn"
	val test_input3 = "jchzalrnumimnmhp"
	val test_input4 = "haegwjzuvuyypxyu"
	val test_input5 = "dvszwmarrgswjxmb"

	val small_input_b0 = "dsxababdsyplpå"
	val small_input_b1 = "qjhvhtzxzqqjkmpb"
	val small_input_b2 = "xxyxx"
	val small_input_b3 = "uurcxstgmygtbstg"
	val small_input_b4 = "ieodomkazucvgmuy"

	@Test
	fun a() {
		assertEquals(1, Task5(listOf(small_input1)).a())
		assertEquals(1, Task5(listOf(small_input2)).a())
		assertEquals(1, Task5(listOf(test_input1)).a())
		assertEquals(1, Task5(listOf(test_input2)).a())
		assertEquals(0, Task5(listOf(test_input3)).a())
		assertEquals(0, Task5(listOf(test_input4)).a())
		assertEquals(0, Task5(listOf(test_input5)).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_5.txt")
		assertEquals(236, Task5(input).a())
	}

	@Test
	fun b() {
		assertEquals(1, Task5(listOf(small_input_b0)).b())
		assertEquals(1, Task5(listOf(small_input_b1)).b())
		assertEquals(1, Task5(listOf(small_input_b2)).b())
		assertEquals(0, Task5(listOf(small_input_b3)).b())
		assertEquals(0, Task5(listOf(small_input_b4)).b())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_5.txt")
		assertEquals(51, Task5(input).b())
	}
}