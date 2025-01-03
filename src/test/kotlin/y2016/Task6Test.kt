package y2016

import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import kotlin.test.assertEquals

class Task6Test {

	val testInput = """
		eedadn
        drvtee
        eandsr
        raavrd
        atevrs
        tsrnev
        sdttsa
        rasrtv
        nssdts
        ntnada
        svetve
        tesnvt
        vntsnd
        vrdear
        dvrsen
        enarar
        """.trim().lines().map { it.trim() }

	@Test
	fun a() {
		assertEquals("easter", Task6(testInput).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2016/2016_6.txt")
		assertEquals("mlncjgdg", Task6(input).a())
	}

	@Test
	fun b() {
		assertEquals("advent", Task6(testInput).b())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2016/2016_6.txt")
		assertEquals("bipjaytb", Task6(input).b())
	}
}