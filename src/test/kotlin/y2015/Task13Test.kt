package y2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task13Test {

	val test_input = """
		Alice would gain 54 happiness units by sitting next to Bob.
		Alice would lose 79 happiness units by sitting next to Carol.
		Alice would lose 2 happiness units by sitting next to David.
		Bob would gain 83 happiness units by sitting next to Alice.
		Bob would lose 7 happiness units by sitting next to Carol.
		Bob would lose 63 happiness units by sitting next to David.
		Carol would lose 62 happiness units by sitting next to Alice.
		Carol would gain 60 happiness units by sitting next to Bob.
		Carol would gain 55 happiness units by sitting next to David.
		David would gain 46 happiness units by sitting next to Alice.
		David would lose 7 happiness units by sitting next to Bob.
		David would gain 41 happiness units by sitting next to Carol.
    """.trim().lines()

	@Test
	fun a() {
		assertEquals(330, Task13(test_input).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_13.txt")
		assertEquals(709, Task13(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_13.txt")
		assertEquals(668, Task13(input).b())
	}
}