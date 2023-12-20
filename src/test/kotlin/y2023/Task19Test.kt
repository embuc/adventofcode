package y2023

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested

class Task19Test {
	val exampleInputString = """
		px{a<2006:qkq,m>2090:A,rfg}
		pv{a>1716:R,A}
		lnx{m>1548:A,A}
		rfg{s<537:gd,x>2440:R,A}
		qs{s>3448:A,lnx}
		qkq{x<1416:A,crn}
		crn{x>2662:A,R}
		in{s<1351:px,qqz}
		qqz{s>2770:qs,m<1801:hdj,R}
		gd{a>3333:R,R}
		hdj{m>838:A,pv}

		{x=787,m=2655,a=1222,s=2876}
		{x=1679,m=44,a=2067,s=496}
		{x=2036,m=264,a=79,s=2244}
		{x=2461,m=1339,a=466,s=291}
		{x=2127,m=1623,a=2188,s=1013}
	""".trimIndent()

	@Test
	fun shouldSolveExampleA() {
		val actual = Task19.solveA(exampleInputString)
		assertEquals(19114, actual)
	}

	@Test
	fun shouldSolveExampleOnePartA() {
		val exampleInputString = """
		px{a<2006:qkq,m>2090:A,rfg}
		pv{a>1716:R,A}
		lnx{m>1548:A,A}
		rfg{s<537:gd,x>2440:R,A}
		qs{s>3448:A,lnx}
		qkq{x<1416:A,crn}
		crn{x>2662:A,R}
		in{s<1351:px,qqz}
		qqz{s>2770:qs,m<1801:hdj,R}
		gd{a>3333:R,R}
		hdj{m>838:A,pv}

		{x=787,m=2655,a=1222,s=2876}
	""".trimIndent()
		val actual = Task19.solveA(exampleInputString)
//		in -> qqz -> qs -> lnx -> A
		assertEquals(7540, actual)
	}

	@Test
	fun shouldParseWorkflow() {
		val input = "px{a<2006:qkq,m>2090:A,rfg}"
		val workflow = Task19.parseWorkflow(input)
		assertEquals("px", workflow.name)
		assertEquals("rfg", workflow.nextOrEndTarget)
		assertEquals(2, workflow.rules.size)
		assertEquals("qkq", workflow.rules[0].target)
		assertEquals("A", workflow.rules[1].target)
		assertEquals("a", workflow.rules[0].name)
		assertEquals("m", workflow.rules[1].name)
		assertTrue(workflow.rules[0].condition(100))
		assertFalse(workflow.rules[0].condition(9999))

	}

	@Test
	fun shouldParsePart() {
		val part = Task19.parseParts("{x=40,m=2274,a=2969,s=1136}")
		assertEquals(40, part.x)
		assertEquals(2274, part.m)
		assertEquals(2969, part.a)
		assertEquals(1136, part.s)
	}

	@Test
	fun shouldEvaluatePartToA() {
		val exampleInputString = """
		in{x>2662:A,s>1351:px,A}

		{x=1,m=2,a=3,s=4}
	""".trimIndent()
		val (workflows, parts) = Task19.parseInput(exampleInputString)
		val workflowMap = workflows.associateBy { it.name }
		val result = Task19.evaluatePart(workflowMap, workflowMap["in"]!!, parts[0])
		assertEquals(10, result)
	}

	@Test
	fun shouldEvaluatePartToB() {
		val exampleInputString = """
		in{s<1351:px,qqz}
		qqz{A}
		px{A}

		{x=1,m=2,a=3,s=4}
	""".trimIndent()
		val (workflows) = Task19.parseInput(exampleInputString)
		val workflowMap = workflows.associateBy { it.name }
		val result = Task19.evaluateRange(workflowMap, workflowMap["in"]!!, Task19.RangePart())
//		px+qqz
		assertEquals(86_400_000_000_000 + 169_600_000_000_000, result)
	}

	@Test
	fun shouldEvaluateExampleB() {
		val (workflows) = Task19.parseInput(exampleInputString)
		val workflowMap = workflows.associateBy { it.name }
		val result = Task19.evaluateRange(workflowMap, workflowMap["in"]!!, Task19.RangePart())
		assertEquals(167_409_079_868_000, result)
//		assertEquals(167_409_079_868_000, answer)
	}

	@Test
	fun shouldSolveA() {
		val actual = Task19.a()
		assertEquals(492702L, actual)
	}

	@Test
	fun shouldSolveB() {
		val actual = Task19.b()
		assertEquals(138_616_621_185_978, actual)
	}

	@Test
	fun shouldCalculateProduct() {
		val actual = Task19.getProductOfRanges(Task19.RangePart().copy(s=LongRange(1, 1350)))
		assertEquals(86_400_000_000_000, actual)
	}

	@Test
	fun shouldCalculateProductUpper() {
		val actual = Task19.getProductOfRanges(Task19.RangePart().copy(s=LongRange(1351, 4000)))
		assertEquals(169_600_000_000_000, actual)
	}

	@Test
	fun shouldUpperSecondaryRange() {
		val actual = Task19.adjustRangeForMoreThanCondition(LongRange(1, 4000), 1351, true)
		assertEquals(LongRange(1351, 4000), actual)
	}


	@Nested
	internal inner class Task19RangeLessTest {

		@Test
		fun `range entirely below threshold`() {
			val result = Task19.adjustRangeForLessThanCondition(1L..5L, 10)
			assertEquals(1L..5L, result)
		}

		@Test
		fun `range starts above threshold`() {
			val result = Task19.adjustRangeForLessThanCondition(15L..20L, 10)
			assertTrue(result.isEmpty())
		}

		@Test
		fun `range includes threshold without secondary`() {
			val result = Task19.adjustRangeForLessThanCondition(5L..15L, 10)
			assertEquals(5L..9L, result)
		}

		@Test
		fun `range includes threshold with secondary`() {
			val result = Task19.adjustRangeForLessThanCondition(5L..15L, 10, inverted = true)
			assertEquals(5L..10L, result)
		}

		@Test
		fun `range includes threshold with secondary at limit`() {
			val result = Task19.adjustRangeForLessThanCondition(9L..10L, 10, inverted = true)
			assertEquals(9L..10L, result)
		}
	}


	@Nested
	internal inner class Task19MoreRangeTest {

		@Test
		fun `range starts above threshold`() {
			val result = Task19.adjustRangeForMoreThanCondition(15L..20L, 10)
			assertEquals(15L..20L, result)
		}

		@Test
		fun `range entirely below threshold`() {
			val result = Task19.adjustRangeForMoreThanCondition(1L..5L, 10)
			assertTrue(result.isEmpty())
		}

		@Test
		fun `range straddles threshold without secondary`() {
			val result = Task19.adjustRangeForMoreThanCondition(5L..15L, 10)
			assertEquals(11L..15L, result)
		}

		@Test
		fun `range straddles threshold with secondary`() {
			val result = Task19.adjustRangeForMoreThanCondition(5L..15L, 10, inverted = true)
			assertEquals(10L..15L, result)
		}

		@Test
		fun `range just below threshold without secondary`() {
			val result = Task19.adjustRangeForMoreThanCondition(9L..12L, 10)
			assertEquals(11L..12L, result)
		}

		@Test
		fun `range just below threshold with secondary`() {
			val result = Task19.adjustRangeForMoreThanCondition(9L..12L, 10, inverted = true)
			assertEquals(10L..12L, result)
		}

		@Test
		fun `range just above threshold without secondary`() {
			val result = Task19.adjustRangeForMoreThanCondition(10L..12L, 10)
			assertEquals(10L..12L, result)
		}

		@Test
		fun `range just above threshold with secondary`() {
			val result = Task19.adjustRangeForMoreThanCondition(10L..12L, 10, inverted = true)
			assertEquals(10L..12L, result)
		}

		@Test
		fun `range equal to threshold without secondary`() {
			val result = Task19.adjustRangeForMoreThanCondition(10L..10L, 10)
			assertEquals(10L..10L, result)
		}
		@Test
		fun `range greater to threshold without secondary`() {
			val result = Task19.adjustRangeForMoreThanCondition(10L..12L, 10)
			assertEquals(10L..12L, result)
		}

		@Test
		fun `range equal to threshold with secondary`() {
			val result = Task19.adjustRangeForMoreThanCondition(10L..10L, 10, inverted = true)
			assertEquals(10L..10L, result)
		}

		// Additional test cases can be added here for more exhaustive coverage
	}


}