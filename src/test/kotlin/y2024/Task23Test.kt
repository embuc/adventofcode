package y2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task23Test {

	val small_input = """
yn-aq
tb-ka
yn-cg
aq-cg
""".trimIndent().lines()
	val test_input = """
        kh-tc
qp-kh
de-cg
ka-co
yn-aq
qp-ub
cg-tb
vc-aq
tb-ka
wh-tc
yn-cg
kh-ub
ta-co
de-co
tc-td
tb-wq
wh-td
ta-ka
td-qp
aq-cg
wq-ub
ub-vc
de-ta
wq-aq
wq-vc
wh-yn
ka-de
kh-ta
co-tc
wh-qp
tb-vc
td-yn
    """.trimIndent().lines()

	@Test
	fun a() {
		assertEquals(7, Task23(test_input).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_23.txt")
		assertEquals(1411, Task23(input).a())
	}

	@Test
	fun b() {
		assertEquals("co,de,ka,ta", Task23(test_input).b())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_23.txt")
		assertEquals("aq,bn,ch,dt,gu,ow,pk,qy,tv,us,yx,zg,zu", Task23(input).b())
	}
}