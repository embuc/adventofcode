package y2022

import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import kotlin.test.assertEquals

class Task5Test {

	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2022/2022_5.txt")
		assertEquals("", Task5(input).a())
	}

	@Test
	fun b() {
	}

}