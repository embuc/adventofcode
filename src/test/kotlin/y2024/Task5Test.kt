package y2024

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import utils.readInputAsListOfStrings

class Task5Test {

	val small_input = """
		47|53
		97|13
		97|61
		97|47
		75|29
		61|13
		75|53
		29|13
		97|29
		53|29
		61|53
		97|53
		61|29
		47|13
		75|47
		97|75
		47|61
		75|61
		47|29
		75|13
		53|13
		
		75,47,61,53,29
		97,61,53,29,13
		75,29,13
		75,97,47,61,53
		61,13,29
		97,13,75,29,47
		""";

	@Test
	fun a_small_input() {
		val input = small_input.split("\n")
		val task = Task5(input)
		assertEquals(143, task.a())
	}

	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_5.txt")
		val task = Task5(input)
		assertEquals(5948, task.a())
	}

	@Test
	fun b_small_input() {
		val input = small_input.split("\n")
		val task = Task5(input)
		assertEquals(123, task.b())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_5.txt")
		val task = Task5(input)
		assertEquals(3062, task.b())
	}
}