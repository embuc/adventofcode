package y2016

import org.junit.jupiter.api.Test
import utils.readInputAsString
import kotlin.test.assertEquals

class Task18Test {

	@Test
	fun a() {
		val input = readInputAsString("~/git/aoc-inputs/2016/2016_18.txt")
		assertEquals(1978, Task18(input, 40).a())
//		assertEquals(1978L, Task18(input, 40).b())
	}

	@Test
	fun b() {
		val input = readInputAsString("~/git/aoc-inputs/2016/2016_18.txt")
		assertEquals(20003246L, Task18(input, 400_000).b())
	}
}