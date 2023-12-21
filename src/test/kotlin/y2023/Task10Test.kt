package y2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task10Test {

	val task10 = Task10

	@Test
	fun testPipeTraversal() {
		val lines = listOf(
			".....",
			".S-7.",
			".|.|.",
			".L-J.",
			"....."
		)
		val grid = task10.parseGrid(lines)
		task10.printGrid(grid)
		val startPos = task10.findStartPosition(grid)
		val distance = task10.traversePath(grid, startPos)
		assertEquals(4, distance, "The longest distance from start should be 4")
	}

	@Test
	fun testPipeTraversal_B() {
		val lines = listOf(
			".....",
			".S-7.",
			".|.|.",
			".L-J.",
			"....."
		)
		val grid = task10.parseGrid(lines)
		task10.printGrid(grid)
		val startPos = task10.findStartPosition(grid)
		val path = task10.traversePath2(grid, startPos)
		val count = task10.countInsideTiles(grid, path)
		assertEquals(1, count, "Number of inside tiles")
	}

	@Test
	fun testPipeTraversal_B_1() {
		val lines = listOf(
			".....",
			".S--7",
			".|..|",
			".L--J",
			"....."
		)
		val grid = task10.parseGrid(lines)
		val startPos = task10.findStartPosition(grid)
		val path = task10.traversePath2(grid, startPos)
		val count = task10.countInsideTiles(grid, path)
		assertEquals(2, count, "Number of inside tiles")
	}

	@Test
	fun testPipeTraversal_B_1_1() {
		val lines = listOf(
			"......",
			".S--7.",
			".|..L7",
			".L---J",
			"......"
		)
		val grid = task10.parseGrid(lines)
		val startPos = task10.findStartPosition(grid)
		val path = task10.traversePath2(grid, startPos)
		val count = task10.countInsideTiles(grid, path)
		assertEquals(2, count, "Number of inside tiles")
	}

	@Test
	fun testPipeTraversal_B_2() {
		val lines = readInputAsListOfStrings("Input10test.txt")
		val grid = task10.parseGrid(lines)
		val startPos = task10.findStartPosition(grid)
		val path = task10.traversePath2(grid, startPos)
		val count = task10.countInsideTiles(grid, path)
		assertEquals(10, count, "Number of inside tiles")
	}

	@Test
	fun testHorizontalConnections() {
		val horizontalTile = Task10.Tile(Task10.TileType.HORIZONTAL)
		val verticalTile = Task10.Tile(Task10.TileType.VERTICAL)
		val bendLTile = Task10.Tile(Task10.TileType.BEND_L)
		val bendFTile = Task10.Tile(Task10.TileType.BEND_F)
		val bend7Tile = Task10.Tile(Task10.TileType.BEND_7)
		val bendJTile = Task10.Tile(Task10.TileType.BEND_J)
		val startTile = Task10.Tile(Task10.TileType.START)
		val groundTile = Task10.Tile(Task10.TileType.GROUND)

		// Horizontal tile should connect to horizontal, bends, and start on left or right
		//on right
		assertTrue(task10.canConnect(horizontalTile, startTile, Task10.Position(0, 0), Task10.Position(1, 0)))
		assertTrue(task10.canConnect(horizontalTile, bendJTile, Task10.Position(0, 0), Task10.Position(1, 0)))
		assertTrue(task10.canConnect(horizontalTile, bend7Tile, Task10.Position(0, 0), Task10.Position(1, 0)))
		//on left
		assertTrue(task10.canConnect(horizontalTile,startTile, Task10.Position(0, 0), Task10.Position(-1, 0)))
		assertTrue(task10.canConnect(horizontalTile, bendLTile, Task10.Position(0, 0), Task10.Position(-1, 0)))
		assertTrue(task10.canConnect(horizontalTile, bendFTile, Task10.Position(0, 0), Task10.Position(-1, 0)))

		// Horizontal tile should not connect to vertical or ground tiles, or in up/down direction
		assertFalse(task10.canConnect(horizontalTile, verticalTile, Task10.Position(0, 0), Task10.Position(0, 1)))
		assertFalse(task10.canConnect(horizontalTile, horizontalTile, Task10.Position(0, 0), Task10.Position(0, -1)))
		assertFalse(task10.canConnect(horizontalTile, bendLTile, Task10. Position(0, 0), Task10.Position(1, 0)))
		assertFalse(task10.canConnect(horizontalTile, bendFTile, Task10.Position(0, 0), Task10. Position(1, 0)))
		assertFalse(task10.canConnect(horizontalTile, groundTile, Task10. Position(0, 0), Task10.Position(1, 0)))
	}


	@Test
	fun testBend7Connections() {
		val bend7Tile = Task10.Tile(Task10.TileType.BEND_7)
		val bendLTile = Task10.Tile(Task10.TileType.BEND_L)
		val bendFTile = Task10.Tile(Task10.TileType.BEND_F)
		val bendJTile = Task10.Tile(Task10.TileType.BEND_J)
		val horizontalTile = Task10.Tile(Task10.TileType.HORIZONTAL)
		val verticalTile = Task10.Tile(Task10.TileType.VERTICAL)

		// Test valid connections for BEND_7
		assertTrue(task10.canConnect(bend7Tile, horizontalTile, Task10.Position(0, 0), Task10.Position(-1, 0))) // Left
		assertTrue(task10.canConnect(bend7Tile, bendLTile, Task10.Position(0, 0), Task10.Position(-1, 0))) // Left
		assertTrue(task10.canConnect(bend7Tile, bendFTile, Task10.Position(0, 0), Task10.Position(-1, 0))) // Left
		assertTrue(task10.canConnect(bend7Tile, verticalTile, Task10.Position(0, 0), Task10.Position(0, 1))) // down
		assertTrue(task10.canConnect(bend7Tile, bendJTile, Task10.Position(0, 0), Task10.Position(0, 1))) // down
		assertTrue(task10.canConnect(bend7Tile, bendLTile, Task10.Position(0, 0), Task10.Position(0, 1))) // down

		// Test invalid connections for BEND_7
		assertFalse(task10.canConnect(bend7Tile, horizontalTile, Task10.Position(0, 0), Task10.Position(0, -1))) // Up
		assertFalse(task10.canConnect(bend7Tile, verticalTile, Task10.Position(0, 0), Task10.Position(-1, 0))) // Left
	}

	@Test
	fun testBendFConnections() {
		val bendFTile = Task10.Tile(Task10.TileType.BEND_F)
		val horizontalTile = Task10.Tile(Task10.TileType.HORIZONTAL)
		val verticalTile = Task10.Tile(Task10.TileType.VERTICAL)

		// Test valid connections for BEND_F
		assertTrue(task10.canConnect(bendFTile, horizontalTile, Task10.Position(0, 0), Task10.Position(1, 0))) // Right
		assertTrue(task10.canConnect(bendFTile, verticalTile, Task10.Position(0, 0), Task10.Position(0, 1))) // down

		// Test invalid connections for BEND_F
		assertFalse(task10.canConnect(bendFTile, horizontalTile, Task10.Position(0, 0), Task10.Position(0, -1))) // Up
		assertFalse(task10.canConnect(bendFTile, verticalTile, Task10.Position(0, 0), Task10.Position(1, 0))) // Right
	}

	@Test
	fun testStartConnections() {
		val startTile = Task10.Tile(Task10.TileType.START)
		val groundTile = Task10.Tile(Task10.TileType.GROUND)
		val verticalTile = Task10.Tile(Task10.TileType.VERTICAL)

		// Start should connect to anything except ground
		assertTrue(task10.canConnect(startTile, verticalTile, Task10.Position(0, 0), Task10.Position(0, 1)))

		// Start should not connect to ground
		assertFalse(task10.canConnect(startTile, groundTile, Task10.Position(0, 0), Task10.Position(0, 1)))
	}

}