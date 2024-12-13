package y2024

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class Task12Test {

	val small_input = """
		AAAA
		BBCD
		BBCC
		EEEC
	""".trimIndent().lines()

	val small_input2 = """
		OOOOO
		OXOXO
		OOOOO
		OXOXO
		OOOOO
	""".trimIndent().lines()

	val small_input_E_formed = """
		EEEEE
		EXXXX
		EEEEE
		EXXXX
		EEEEE
	""".trimIndent().lines()

	val test_input = """
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

	val test_input_b = """
		AAAAAA
		AAABBA
		AAABBA
		ABBAAA
		ABBAAA
		AAAAAA
	""".trimIndent().lines()

	val small_input_b = """
		OOOOO
		OXOXO
		OXXXO
	""".trimIndent().lines()

	val small_input_b2 = """
		.....
		.AAA.
		.A.A.
		.AA..
		.A.A.
		.AAA.
		.....
	""".trimIndent().lines()

	val small_input_b3 = """
		LDDDDDDXXX
		LLLDDVDXXX
		LLLDDDXXXX
	""".trimIndent().lines()

	val test_input_b2 = """
		AAAEAAAAAA
		FFAEAADAAA
		FFAAAADACA
		FFAABAAAAB
		FFABBBABBB
		FAAAABBBBB
		FAGGABBBBB
		FAGAABBBBB
	""".trimIndent().lines()

	val small_input_b4 = """
		BBBBBC
		BAAABC
		BABABC
		BAABBB
		BABABC
		BAAABC
	""".trimIndent().lines()

	val small_input_b5 = """
		AAAAA
		ABABA
		ABBBA
		ABABA
	""".trimIndent().lines()

	val test_input_b3 = """
		VVVVVCRRCCCCCCCYYC
		CVCCVCCCCCCCCCCYCC
		CCCCCCCCCCCCCCCCCC
		CCCQQCCCCCCCCCCCCC
		QQQQCCCCCCCCCCCCCC
		QQQQQQCCCCCCCCCCCC
		QQQQQQQCCKCKKCCCYY
		QQQQQQQQKKKKKKKCYY
	""".trimIndent().lines()

	val small_input_b6 = """
		----
		OOO-
		O-O-
		O-OO
		OOO-
	""".trimIndent().lines()

	val small_input_b7 = """
		----
		-OOO
		-O-O
		OO-O
		-OOO
	""".trimIndent().lines()

	val test_input_b4 = """
		AAAAAAAA
		AACBBDDA
		AACBBAAA
		ABBAAAAA
		ABBADDDA
		AAAADADA
		AAAAAAAA
	""".trimIndent().lines()

	val small_input_b8 = """
		AAAAA
		A...A
		A.A.A
		AAAAA
	""".trimIndent().lines()

	@Test
	fun a() {
		assertEquals(140, Task12(small_input).a())
		assertEquals(772, Task12(small_input2).a())
		assertEquals(1930, Task12(test_input).a())
		val input = utils.readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_12.txt")
		val task = Task12(input)
		assertEquals(1573474, task.a())
	}

	@Test
	fun b() {
		assertEquals(80, Task12(small_input).b())
		assertEquals(436, Task12(small_input2).b())
		assertEquals(236, Task12(small_input_E_formed).b())
		assertEquals(368, Task12(test_input_b).b())
		assertEquals(1206, Task12(test_input).b())
		assertEquals(160, Task12(small_input_b).b())
		assertEquals(452, Task12(small_input_b2).b())
		assertEquals(250, Task12(small_input_b3).b())
		assertEquals(1992, Task12(test_input_b2).b())
		assertEquals(492, Task12(small_input_b4).b())
		assertEquals(232, Task12(small_input_b5).b())
		assertEquals(4614, Task12(test_input_b3).b())
		assertEquals(180, Task12(small_input_b6).b())
		assertEquals(180, Task12(small_input_b7).b())
		assertEquals(220, Task12(small_input_b8).b())
		assertEquals(946, Task12(test_input_b4).b())
		val input = utils.readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_12.txt")
		val task = Task12(input)
		assertEquals(966476, task.b())
	}

}

