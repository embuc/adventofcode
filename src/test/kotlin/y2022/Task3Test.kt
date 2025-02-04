package y2022

import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import kotlin.test.assertEquals

class Task3Test {
	val testInput = """
		vJrwpWtwJgWrhcsFMMfFFhFp
		jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
		PmmdzqPrVvPwwTWBwg
		wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
		ttgJtRGJQctTZtZT
		CrZsJsPPZsGzwwsLwLmpwMDw"""
		.trim().lines().map { it.trim() }

	@Test
	fun a() {
		assertEquals(157, Task3(testInput).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2022/2022_3.txt")
		assertEquals(8088, Task3(input).a())
	}

	@Test
	fun b() {
		assertEquals(70, Task3(testInput).b())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2022/2022_3.txt")
		assertEquals(2522, Task3(input).b())
	}

}