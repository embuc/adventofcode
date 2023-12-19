package y2023

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

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
	fun shouldSolveA() {
	}

	@Test
	fun shouldSolveB() {
	}
}