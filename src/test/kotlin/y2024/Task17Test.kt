package y2024

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import utils.readInputAsListOfStrings

class Task17Test {

	val test_input1 = """
		Register A: 729
		Register B: 0
		Register C: 0
		
		Program: 0,1,5,4,3,0
	""".trimIndent().lines()
	val test_input2 = """
		Register A: 47719761
		Register B: 0
		Register C: 0
		
		Program: 2,4,1,5,7,5,0,3,4,1,1,6,5,5,3,0
	""".trimIndent().lines()

	val test_inputb = """
		Register A: 2024
		Register B: 0
		Register C: 0
		
		Program: 0,3,5,4,3,0
	""".trimIndent().lines()

	val test_inputb2 = """
		Register A: 247839539763386
		Register B: 0
		Register C: 0
		
		Program: 2,4,1,1,7,5,0,3,4,3,1,6,5,5,3,0
	""".trimIndent().lines()

	@Test
	fun a() {
		assertEquals("4,6,3,5,6,3,5,2,1,0", Task17(test_input1).a())
		assertEquals("7,0,3,1,2,6,3,7,1", Task17(test_input2).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_17.txt")
		assertEquals("2,0,7,3,0,3,1,3,7", Task17(input).a())
		assertEquals("2,4,1,1,7,5,0,3,4,3,1,6,5,5,3,0", Task17(test_inputb2).a())
	}

	@Test
	fun b() {
		assertEquals(117440L, Task17(test_inputb).b())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_17.txt")
		assertEquals(247839539763386L, Task17(input).b())
	}
}