package y2015

import org.junit.jupiter.api.Test
import utils.readInputAsString
import kotlin.test.assertEquals

class Task12Test {

	val smallInput = "[1,2,3]"
	val smallInput2 = "{\"c\":\"red\",\"a\":2,\"b\":4}"
	val smallInput3 = "{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}"
	val smallInput4 = "[1,{\"c\":\"red\",\"b\":2},3]"
	val smallInput5 = "[1,\"red\",5]"

	@Test
	fun a() {
		assertEquals(6, Task12(smallInput).a())
		assertEquals(6, Task12(smallInput2).a())
		val input = readInputAsString("~/git/aoc-inputs/2015/2015_12.txt")
		assertEquals(111754, Task12(input).a())
	}

	@Test
	fun b() {
		assertEquals(6, Task12(smallInput).b())
		assertEquals(0, Task12(smallInput2).b())
		assertEquals(0, Task12(smallInput3).b())
		assertEquals(4, Task12(smallInput4).b())
		assertEquals(6, Task12(smallInput5).b())
		val input = readInputAsString("~/git/aoc-inputs/2015/2015_12.txt")
		assertEquals(65402, Task12(input).b())
	}
}