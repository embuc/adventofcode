package y2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.getLinesFromFile

class Uppg11Test {

//	@Test TODO Fix this test
	fun testExpandGrid() {
		val uppg11 = Uppg11()

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

		val expandedGrid = uppg11.expandGrid(input, 1_000_000)
		uppg11.printGrid(expandedGrid)
		uppg11.printGridCoord(expandedGrid)

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
		val uppg11 = Uppg11()
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
		val expandedGrid = uppg11.expandGrid(input, 1_000_000)
		val galaxyPairs = uppg11.findAllGalaxyPairs(expandedGrid)
		assertEquals(36, galaxyPairs.size)
	}

	@Test
	fun findAllGalaxyPairsWhole() {
		val uppg11 = Uppg11()
		val input = getLinesFromFile("Input11.txt")
		val expandedGrid = uppg11.expandGrid(input, 1_000_000)
		val galaxyPairs = uppg11.findAllGalaxyPairs(expandedGrid)
		assertEquals(96141, galaxyPairs.size)
	}

//	@Test TODO Fix this test
	fun findShortestPaths() {
		val uppg11 = Uppg11()
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
		val expandedGrid = uppg11.expandGrid(input, 1_000_000)
		val galaxyPairs = uppg11.findAllGalaxyPairs(expandedGrid)
		uppg11.printGrid(expandedGrid)
		val shortestPaths = uppg11.findSumOfPaths(galaxyPairs)
		assertEquals(36, galaxyPairs.size)
		assertEquals(374, shortestPaths)
	}
}