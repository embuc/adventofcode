package y2016

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import kotlin.test.Ignore

class Task7Test {

	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2016/2016_7.txt")
		assertEquals(118, Task7(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2016/2016_7.txt")
		assertEquals(260, Task7(input).b())
	}

	@Ignore
	@Test
	fun shouldExtract() {
		val input =  "lhhhlyioiiknkray[omilmxkodlmvzhkgbaf]cyftkgjpvjvdnortlj[ifljdtkgscmnmxsq]nxtettqnuaotfsh"
		val (inside, outside) = Task7(listOf()).partitionInsideOutside(input)
		assertEquals(mutableListOf("omilmxkodlmvzhkgbaf", "ifljdtkgscmnmxsq"), inside)
		assertEquals(mutableListOf("lhhhlyioiiknkray", "cyftkgjpvjvdnortlj", "nxtettqnuaotfsh"), outside)
	}
}