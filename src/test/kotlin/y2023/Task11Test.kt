package y2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task11Test {

	val task11 = Task11

//	@Test TODO Fix this test
	fun testExpandGrid() {
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

		val expandedProcessedInput = listOf(
			"....1........",
			".........2...",
			"3............",
			".............",
			".............",
			"........4....",
			".5...........",
			"............6",
			".............",
			".............",
			".........7...",
			"8....9......."
		)

		val expandedGrid = task11.expandGrid(input, 1_000_000)
		task11.printGrid(expandedGrid)
		task11.printGridCoord(expandedGrid)

		// Define your expected result
		val expected = expandedProcessedInput

		// Convert expandedGrid to a comparable format if necessary
		val actual = expandedGrid.map { row ->
			row.joinToString("") { it.label }
		}

		assertEquals(expected, actual)
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
		task11.printGrid(expandedGrid)
		val shortestPaths = task11.findSumOfPaths(galaxyPairs)
		assertEquals(36, galaxyPairs.size)
		assertEquals(374, shortestPaths)
	}
}