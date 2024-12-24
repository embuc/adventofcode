package y2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task24Test {

	val small_input = """
		x00: 1
		x01: 1
		x02: 1
		y00: 0
		y01: 1
		y02: 0
		
		x00 AND y00 -> z00
		x01 XOR y01 -> z01
		x02 OR y02 -> z02
	""".trimIndent().lines()

	val test_input = """
		x00: 1
		x01: 0
		x02: 1
		x03: 1
		x04: 0
		y00: 1
		y01: 1
		y02: 1
		y03: 1
		y04: 1

		ntg XOR fgs -> mjb
		y02 OR x01 -> tnw
		kwq OR kpj -> z05
		x00 OR x03 -> fst
		tgd XOR rvg -> z01
		vdt OR tnw -> bfw
		bfw AND frj -> z10
		ffh OR nrd -> bqk
		y00 AND y03 -> djm
		y03 OR y00 -> psh
		bqk OR frj -> z08
		tnw OR fst -> frj
		gnj AND tgd -> z11
		bfw XOR mjb -> z00
		x03 OR x00 -> vdt
		gnj AND wpb -> z02
		x04 AND y00 -> kjc
		djm OR pbm -> qhw
		nrd AND vdt -> hwm
		kjc AND fst -> rvg
		y04 OR y02 -> fgs
		y01 AND x02 -> pbm
		ntg OR kjc -> kwq
		psh XOR fgs -> tgd
		qhw XOR tgd -> z09
		pbm OR djm -> kpj
		x03 XOR y03 -> ffh
		x00 XOR y04 -> ntg
		bfw OR bqk -> z06
		nrd XOR fgs -> wpb
		frj XOR qhw -> z04
		bqk OR frj -> z07
		y03 OR x01 -> nrd
		hwm AND bqk -> z03
		tgd XOR rvg -> z12
		tnw OR pbm -> gnj
	""".trimIndent().lines()

	val test_input_b = """
		x00: 0
		x01: 1
		x02: 0
		x03: 1
		x04: 0
		x05: 1
		y00: 0
		y01: 0
		y02: 1
		y03: 1
		y04: 0
		y05: 1
		
		x00 AND y00 -> z05
		x01 AND y01 -> z02
		x02 AND y02 -> z01
		x03 AND y03 -> z03
		x04 AND y04 -> z04
		x05 AND y05 -> z00
		""".trimIndent().lines()

	@Test
	fun a() {
		assertEquals(4L, Task24(small_input).a())
		assertEquals(2024L, Task24(test_input).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_24.txt")
		assertEquals(49430469426918L, Task24(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_24.txt")
		assertEquals("fbq,pbv,qff,qnw,qqp,z16,z23,z36", Task24(input).b())
	}

}