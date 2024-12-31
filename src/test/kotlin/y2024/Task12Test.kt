package y2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Task12Test {

	val smallInput = """
		AAAA
		BBCD
		BBCC
		EEEC
	""".trimIndent().lines()

	val smallInput2 = """
		OOOOO
		OXOXO
		OOOOO
		OXOXO
		OOOOO
	""".trimIndent().lines()

	val smallInput_E_formed = """
		EEEEE
		EXXXX
		EEEEE
		EXXXX
		EEEEE
	""".trimIndent().lines()

	val testInput = """
		RRRRIICCFF
		RRRRIICCCF
		VVRRRCCFFF
		VVRCCCJFFF
		VVVVCJJCFE
		VVIVCCJJEE
		VVIIICJJEE
		MIIIIIJJEE
		MIIISIJEEE
		MMMISSJEEE
	""".trimIndent().lines()

	val testInput_b = """
		AAAAAA
		AAABBA
		AAABBA
		ABBAAA
		ABBAAA
		AAAAAA
	""".trimIndent().lines()

	val smallInput_b = """
		OOOOO
		OXOXO
		OXXXO
	""".trimIndent().lines()

	val smallInput_b2 = """
		.....
		.AAA.
		.A.A.
		.AA..
		.A.A.
		.AAA.
		.....
	""".trimIndent().lines()

	val smallInput_b3 = """
		LDDDDDDXXX
		LLLDDVDXXX
		LLLDDDXXXX
	""".trimIndent().lines()

	val testInput_b2 = """
		AAAEAAAAAA
		FFAEAADAAA
		FFAAAADACA
		FFAABAAAAB
		FFABBBABBB
		FAAAABBBBB
		FAGGABBBBB
		FAGAABBBBB
	""".trimIndent().lines()

	val smallInput_b4 = """
		BBBBBC
		BAAABC
		BABABC
		BAABBB
		BABABC
		BAAABC
	""".trimIndent().lines()

	val smallInput_b5 = """
		AAAAA
		ABABA
		ABBBA
		ABABA
	""".trimIndent().lines()

	val testInput_b3 = """
		VVVVVCRRCCCCCCCYYC
		CVCCVCCCCCCCCCCYCC
		CCCCCCCCCCCCCCCCCC
		CCCQQCCCCCCCCCCCCC
		QQQQCCCCCCCCCCCCCC
		QQQQQQCCCCCCCCCCCC
		QQQQQQQCCKCKKCCCYY
		QQQQQQQQKKKKKKKCYY
	""".trimIndent().lines()

	val smallInput_b6 = """
		----
		OOO-
		O-O-
		O-OO
		OOO-
	""".trimIndent().lines()

	val smallInput_b7 = """
		----
		-OOO
		-O-O
		OO-O
		-OOO
	""".trimIndent().lines()

	val testInput_b4 = """
		AAAAAAAA
		AACBBDDA
		AACBBAAA
		ABBAAAAA
		ABBADDDA
		AAAADADA
		AAAAAAAA
	""".trimIndent().lines()

	val smallInput_b8 = """
		AAAAA
		A...A
		A.A.A
		AAAAA
	""".trimIndent().lines()

	@Test
	fun a() {
		assertEquals(140, Task12(smallInput).a())
		assertEquals(772, Task12(smallInput2).a())
		assertEquals(1930, Task12(testInput).a())
		val input = utils.readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_12.txt")
		val task = Task12(input)
		assertEquals(1573474, task.a())
	}

	@Test
	fun b() {
		assertEquals(80, Task12(smallInput).b())
		assertEquals(436, Task12(smallInput2).b())
		assertEquals(236, Task12(smallInput_E_formed).b())
		assertEquals(368, Task12(testInput_b).b())
		assertEquals(1206, Task12(testInput).b())
		assertEquals(160, Task12(smallInput_b).b())
		assertEquals(452, Task12(smallInput_b2).b())
		assertEquals(250, Task12(smallInput_b3).b())
		assertEquals(1992, Task12(testInput_b2).b())
		assertEquals(492, Task12(smallInput_b4).b())
		assertEquals(232, Task12(smallInput_b5).b())
		assertEquals(4614, Task12(testInput_b3).b())
		assertEquals(180, Task12(smallInput_b6).b())
		assertEquals(180, Task12(smallInput_b7).b())
		assertEquals(220, Task12(smallInput_b8).b())
		assertEquals(946, Task12(testInput_b4).b())
		val input = utils.readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_12.txt")
		val task = Task12(input)
		assertEquals(966476, task.b())
	}

}

