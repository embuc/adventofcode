package y2023

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import utils.Graphs
import utils.readInputAsListOfStrings

class Task25Test {

	@Nested
	inner class Part1 {

		@Test
		fun `Matches example`() {
			val input = """
				jqt: rhn xhk nvd
				rsh: frs pzl lsr
				xhk: hfx
				cmg: qnr nvd lhk bvb
				rhn: xhk bvb hfx
				bvb: xhk hfx
				pzl: lsr hfx nvd
				qnr: nvd
				ntq: jqt hfx bvb xhk
				nvd: lhk
				lsr: lhk
				rzs: qnr cmg lsr rsh
				frs: qnr lhk lsr
			""".trimIndent().lines()
			val answer = Task25(input).a()
			assertEquals(54, answer)
		}

		@Test
		fun `Matches actual`() {
			val answer = Task25(readInputAsListOfStrings("~/git/aoc-inputs/2023/2023_25.txt")).a()
			assertEquals(547410, answer)
		}
	}
}