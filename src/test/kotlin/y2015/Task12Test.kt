package y2015

import org.junit.jupiter.api.Test
import utils.readInputAsString
import kotlin.test.assertEquals

class Task12Test {

	val small_input = "[1,2,3]"
	val small_input2 = "{\"c\":\"red\",\"a\":2,\"b\":4}"
	val small_input3 = "{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}"
	val small_input4 = "[1,{\"c\":\"red\",\"b\":2},3]"
	val small_input5 = "[1,\"red\",5]"

	@Test
	fun a() {
		assertEquals(6, Task12(small_input).a())
		assertEquals(6, Task12(small_input2).a())
		val input = readInputAsString("~/git/aoc-inputs/2015/2015_12.txt")
		assertEquals(111754, Task12(input).a())
	}

	@Test
	fun b() {
		assertEquals(6, Task12(small_input).b())
		assertEquals(0, Task12(small_input2).b())
		assertEquals(0, Task12(small_input3).b())
		assertEquals(4, Task12(small_input4).b())
		assertEquals(6, Task12(small_input5).b())
		val input = readInputAsString("~/git/aoc-inputs/2015/2015_12.txt")
		assertEquals(65402, Task12(input).b())
	}
}