package y2023

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Task18Test {

	@Test
	fun testSolveA() {
		val result = Task18.a()
		assertEquals(46394, result)
	}

	/*
	#######
	#.....#
	###...#
	..#...#
	..#...#
	###.###
	#...#..
	##..###
	.#....#
	.######
	*/
	@Test
	fun solveExample() {
		val lines =
			"""
			R 6 (#70c710)
			D 5 (#0dc571)
			L 2 (#5713f0)
			D 2 (#d2c081)
			R 2 (#59c680)
			D 2 (#411b91)
			L 5 (#8ceee2)
			U 2 (#caa173)
			L 1 (#1b58a2)
			U 2 (#caa171)
			R 2 (#7807d2)
			U 3 (#a77fa3)
			L 2 (#015232)
			U 2 (#7a21e3)
		""".trimIndent().lines()
		val polygonInstructions = Task18.parseToPolygonInstructions(lines)
//		polygonInstructions.forEach { println(it) }
		assertEquals(14, polygonInstructions.size)
		val polygon = Task18.createPolygon(polygonInstructions)
		Task18.printPolygon(polygon)
		println()
		assertEquals(39, polygon.size)
//		assertEquals(62, area)
//		assertEquals(61, area)
		val cubicMeters = Task18.lavaCubicMeters(polygon)
		assertEquals(62, cubicMeters)

	}

	@Test
	fun testSolveB() {
		val result = Task18.b()
		assertEquals(-1, result)
	}
}