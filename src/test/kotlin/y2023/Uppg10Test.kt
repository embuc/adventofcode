package y2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import utils.getLinesFromFile
import y2023.Uppg10

class Uppg10Test {

	@Test
	fun testPipeTraversal() {
		// Arrange
		val lines = listOf(
			".....",
			".S-7.",
			".|.|.",
			".L-J.",
			"....."
		)

		val uppg10 = Uppg10()

		// Act
		val grid = uppg10.parseGrid(lines)
		uppg10.printGrid(grid)
		val startPos = uppg10.findStartPosition(grid)
		println("Start position: $startPos")
		val distance = uppg10.traversePath(grid, startPos)

		// Assert
		assertEquals(4, distance, "The longest distance from start should be 4")
	}
	@Test
	fun testPipeTraversal_B() {
		// Arrange
		val lines = listOf(
			".....",
			".S-7.",
			".|.|.",
			".L-J.",
			"....."
		)

		val uppg10 = Uppg10()

		// Act
		val grid = uppg10.parseGrid(lines)
		uppg10.printGrid(grid)
		val startPos = uppg10.findStartPosition(grid)
		val path = uppg10.traversePath2(grid, startPos)
		val count = uppg10.countInsideTiles(grid, path)
		// Assert
		assertEquals(1, count, "Number of inside tiles")
	}

	@Test
	fun testPipeTraversal_B_1() {
		// Arrange
		val lines = listOf(
			".....",
			".S--7",
			".|..|",
			".L--J",
			"....."
		)

		val uppg10 = Uppg10()

		// Act
		val grid = uppg10.parseGrid(lines)
//		uppg10.printGrid(grid)
		val startPos = uppg10.findStartPosition(grid)
		val path = uppg10.traversePath2(grid, startPos)
		val count = uppg10.countInsideTiles(grid, path)
		// Assert
		assertEquals(2, count, "Number of inside tiles")
	}
	@Test
	fun testPipeTraversal_B_1_1() {
		// Arrange
		val lines = listOf(
			"......",
			".S--7.",
			".|..L7",
			".L---J",
			"......"
		)

		val uppg10 = Uppg10()

		// Act
		val grid = uppg10.parseGrid(lines)
//		uppg10.printGrid(grid)
		val startPos = uppg10.findStartPosition(grid)
		val path = uppg10.traversePath2(grid, startPos)
		val count = uppg10.countInsideTiles(grid, path)
		// Assert
		assertEquals(2, count, "Number of inside tiles")
	}

	@Test
	fun testPipeTraversal_B_2() {
		val uppg10 = Uppg10()
		// Arrange
		val lines = getLinesFromFile("Input10test.txt")


		// Act
		val grid = uppg10.parseGrid(lines)
//		uppg10.printGrid(grid)
		val startPos = uppg10.findStartPosition(grid)
		val path = uppg10.traversePath2(grid, startPos)
		val count = uppg10.countInsideTiles(grid, path)
		// Assert
		assertEquals(10, count, "Number of inside tiles")
	}

	@Test
	fun testHorizontalConnections() {
		val uppg10 = Uppg10()
		val horizontalTile = Uppg10.Tile(Uppg10.TileType.HORIZONTAL)
		val verticalTile = Uppg10.Tile(Uppg10.TileType.VERTICAL)
		val bendLTile = Uppg10.Tile(Uppg10.TileType.BEND_L)
		val bendFTile = Uppg10.Tile(Uppg10.TileType.BEND_F)
		val bend7Tile = Uppg10.Tile(Uppg10.TileType.BEND_7)
		val bendJTile = Uppg10.Tile(Uppg10.TileType.BEND_J)
		val startTile = Uppg10.Tile(Uppg10.TileType.START)
		val groundTile = Uppg10.Tile(Uppg10.TileType.GROUND)

		// Horizontal tile should connect to horizontal, bends, and start on left or right
		//on right
		assertTrue(uppg10.canConnect(horizontalTile, startTile, Uppg10.Position(0, 0), Uppg10.Position(1, 0)))
		assertTrue(uppg10.canConnect(horizontalTile, bendJTile, Uppg10.Position(0, 0), Uppg10.Position(1, 0)))
		assertTrue(uppg10.canConnect(horizontalTile, bend7Tile, Uppg10.Position(0, 0), Uppg10.Position(1, 0)))
		//on left
		assertTrue(uppg10.canConnect(horizontalTile,startTile, Uppg10.Position(0, 0), Uppg10.Position(-1, 0)))
		assertTrue(uppg10.canConnect(horizontalTile, bendLTile, Uppg10.Position(0, 0), Uppg10.Position(-1, 0)))
		assertTrue(uppg10.canConnect(horizontalTile, bendFTile, Uppg10.Position(0, 0), Uppg10.Position(-1, 0)))

		// Horizontal tile should not connect to vertical or ground tiles, or in up/down direction
		assertFalse(uppg10.canConnect(horizontalTile, verticalTile, Uppg10.Position(0, 0), Uppg10.Position(0, 1)))
		assertFalse(uppg10.canConnect(horizontalTile, horizontalTile, Uppg10.Position(0, 0), Uppg10.Position(0, -1)))
		assertFalse(uppg10.canConnect(horizontalTile, bendLTile, Uppg10. Position(0, 0), Uppg10.Position(1, 0)))
		assertFalse(uppg10.canConnect(horizontalTile, bendFTile, Uppg10.Position(0, 0), Uppg10. Position(1, 0)))
		assertFalse(uppg10.canConnect(horizontalTile, groundTile, Uppg10. Position(0, 0), Uppg10.Position(1, 0)))
	}


	@Test
	fun testBend7Connections() {
		val uppg10 = Uppg10()

		val bend7Tile = Uppg10.Tile(Uppg10.TileType.BEND_7)
		val bendLTile = Uppg10.Tile(Uppg10.TileType.BEND_L)
		val bendFTile = Uppg10.Tile(Uppg10.TileType.BEND_F)
		val bendJTile = Uppg10.Tile(Uppg10.TileType.BEND_J)
		val horizontalTile = Uppg10.Tile(Uppg10.TileType.HORIZONTAL)
		val verticalTile = Uppg10.Tile(Uppg10.TileType.VERTICAL)

		// Test valid connections for BEND_7
		assertTrue(uppg10.canConnect(bend7Tile, horizontalTile, Uppg10.Position(0, 0), Uppg10.Position(-1, 0))) // Left
		assertTrue(uppg10.canConnect(bend7Tile, bendLTile, Uppg10.Position(0, 0), Uppg10.Position(-1, 0))) // Left
		assertTrue(uppg10.canConnect(bend7Tile, bendFTile, Uppg10.Position(0, 0), Uppg10.Position(-1, 0))) // Left
		assertTrue(uppg10.canConnect(bend7Tile, verticalTile, Uppg10.Position(0, 0), Uppg10.Position(0, 1))) // down
		assertTrue(uppg10.canConnect(bend7Tile, bendJTile, Uppg10.Position(0, 0), Uppg10.Position(0, 1))) // down
		assertTrue(uppg10.canConnect(bend7Tile, bendLTile, Uppg10.Position(0, 0), Uppg10.Position(0, 1))) // down

		// Test invalid connections for BEND_7
		assertFalse(uppg10.canConnect(bend7Tile, horizontalTile, Uppg10.Position(0, 0), Uppg10.Position(0, -1))) // Up
		assertFalse(uppg10.canConnect(bend7Tile, verticalTile, Uppg10.Position(0, 0), Uppg10.Position(-1, 0))) // Left
	}

	@Test
	fun testBendFConnections() {
		val uppg10 = Uppg10()

		val bendFTile = Uppg10.Tile(Uppg10.TileType.BEND_F)
		val horizontalTile = Uppg10.Tile(Uppg10.TileType.HORIZONTAL)
		val verticalTile = Uppg10.Tile(Uppg10.TileType.VERTICAL)

		// Test valid connections for BEND_F
		assertTrue(uppg10.canConnect(bendFTile, horizontalTile, Uppg10.Position(0, 0), Uppg10.Position(1, 0))) // Right
		assertTrue(uppg10.canConnect(bendFTile, verticalTile, Uppg10.Position(0, 0), Uppg10.Position(0, 1))) // down

		// Test invalid connections for BEND_F
		assertFalse(uppg10.canConnect(bendFTile, horizontalTile, Uppg10.Position(0, 0), Uppg10.Position(0, -1))) // Up
		assertFalse(uppg10.canConnect(bendFTile, verticalTile, Uppg10.Position(0, 0), Uppg10.Position(1, 0))) // Right
	}

	@Test
	fun testStartConnections() {
		val uppg10 = Uppg10()

		val startTile = Uppg10.Tile(Uppg10.TileType.START)
		val groundTile = Uppg10.Tile(Uppg10.TileType.GROUND)
		val verticalTile = Uppg10.Tile(Uppg10.TileType.VERTICAL)

		// Start should connect to anything except ground
		assertTrue(uppg10.canConnect(startTile, verticalTile, Uppg10.Position(0, 0), Uppg10.Position(0, 1)))

		// Start should not connect to ground
		assertFalse(uppg10.canConnect(startTile, groundTile, Uppg10.Position(0, 0), Uppg10.Position(0, 1)))
	}

}