package y2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task11Test {

	val task11 = Task11

	@Test
	fun a() {
		assertEquals(9795148L, task11.a())
	}

	@Test
	fun b() {
		assertEquals(650672493820, task11.b())
	}

	@Test
	fun findAllGalaxyPairs() {
		val input = listOf(
			"...#......",
			".......#..",
			"#.........",
			"..........",
			"......#...",
			".#........",
			".........#",
			"..........",
			".......#..",
			"#...#....."
		)
		val expandedGrid = task11.expandGrid(input, 1_000_000)
		val galaxyPairs = task11.findAllGalaxyPairs(expandedGrid)
		assertEquals(36, galaxyPairs.size)
	}

	@Test
	fun findAllGalaxyPairsWhole() {
		val input = readInputAsListOfStrings("2023_11.txt")
		val expandedGrid = task11.expandGrid(input, 1_000_000)
		val galaxyPairs = task11.findAllGalaxyPairs(expandedGrid)
		assertEquals(96141, galaxyPairs.size)
	}

//	@Test TODO Fix this test
	fun findShortestPaths() {
		val input = listOf(
			"...#......",
			".......#..",
			"#.........",
			"..........",
			"......#...",
			".#........",
			".........#",
			"..........",
			".......#..",
			"#...#....."
		)
		val expandedGrid = task11.expandGrid(input, 1_000_000)
		val galaxyPairs = task11.findAllGalaxyPairs(expandedGrid)
		val shortestPaths = task11.findSumOfPaths(galaxyPairs)
		assertEquals(36, galaxyPairs.size)
		assertEquals(374, shortestPaths)
	}
}