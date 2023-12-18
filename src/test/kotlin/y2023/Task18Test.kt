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
	fun solveExampleA() {
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
		val polygonInstructions = Task18.parseToPolygonInstructions(lines, false)
		assertEquals(14, polygonInstructions.size)
		val polygon = Task18.createPolygon(polygonInstructions)
//		Task18.printPolygon(polygon)
		assertEquals(39, polygon.size)
		val cubicMeters = Task18.lavaCubicMeters(polygon)
		assertEquals(62, cubicMeters)
	}

	/*
	#70c710 = R 461937
	#0dc571 = D 56407
	#5713f0 = R 356671
	#d2c081 = D 863240
	#59c680 = R 367720
	#411b91 = D 266681
	#8ceee2 = L 577262
	#caa173 = U 829975
	#1b58a2 = L 112010
	#caa171 = D 829975
	#7807d2 = L 491645
	#a77fa3 = U 686074
	#015232 = L 5411
	#7a21e3 = U 500254
	* */
	@Test
	fun solveExampleB() {
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
		val polygonInstructions = Task18.parseToPolygonInstructions(lines, true)
//		polygonInstructions.forEach { println(it) }
		assertEquals(14, polygonInstructions.size)
		val polygon = Task18.createPolygon(polygonInstructions)
		val cubicMeters = Task18.lavaCubicMeters(polygon)
		assertEquals(952408144115, cubicMeters)
	}

	@Test
	fun testSolveB() {// heap intensive
		val result = Task18.b()
		assertEquals(201398068194715L, result)
	}
}