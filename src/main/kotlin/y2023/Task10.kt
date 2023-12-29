package y2023

import Task
import utils.readInputAsListOfStrings

//--- Day 10: Pipe Maze ---
// this one was hard, I had to look up the solution and work backwards from there using test cases to understand the logic
object Task10:Task {

	override fun a(): Int {
		val grid = readAndParseGrid("2023_10.txt")
		val startPos = findStartPosition(grid)
		return traversePathForDistance(grid, startPos)
	}

	override fun b(): Int {
		val grid = readAndParseGrid("2023_10.txt")
		val startPos = findStartPosition(grid)
		val path = traversePath(grid, startPos)
		return countInsideTiles(grid, path)
	}

	private fun readAndParseGrid(filename: String): Array<Array<Tile>> {
		val lines = readInputAsListOfStrings(filename)
		return parseGrid(lines)
	}

	fun countInsideTiles(grid: Array<Array<Tile>>, path: MutableList<Position>): Int {
		var count = 0
		for (y in grid.indices) {
			for (x in grid[0].indices) {
				val pos = Position(x, y)
				if (!path.contains(pos)) {
					if (insidePolygon(path, pos)) {
						// This tile is inside the loop
						count++
					} else {
						// This tile is outside the loop
					}
				}
			}
		}
		return count
	}

	fun traversePath(grid: Array<Array<Tile>>, startPos: Position): MutableList<Position> {
		var steps = 0
		val startTile = grid[startPos.y][startPos.x]
		val path = mutableListOf<Position>()
		path.add(startPos)
		val (initialTile, _) = getInitialConnectedTiles(startTile)
		steps++
		var currentTile = initialTile ?: throw IllegalStateException("Invalid start configuration")
		var previousTile: Tile? = startTile

		while (currentTile.type != TileType.START) {
			steps++
			path.add(currentTile.pos ?: throw IllegalStateException("Invalid tile position"))
			val nextTile = getNextTile(currentTile, previousTile)
			previousTile = currentTile
			currentTile = nextTile ?: break
		}

		return path
	}

	/**
	 * Determines if a given point [p] is inside a polygon defined by a list of [polygon] vertices.
	 *
	 * This function implements the ray-casting algorithm: it casts a horizontal ray to the right from the point
	 * and counts how many times it intersects with the sides of the polygon. If the count is odd, the point is inside;
	 * if even, the point is outside. This method works for all polygons, whether they are simple or complex.
	 *
	 * @param polygon List of positions representing the vertices of the polygon in order.
	 * @param p The position (point) to check.
	 * @return Boolean indicating whether the point is inside the polygon.
	 */
	private fun insidePolygon(polygon: List<Position>, p: Position): Boolean {
		var counter = 0
		var p1 = polygon[0]

		for (i in 1..polygon.size) {
			val p2 = polygon[i % polygon.size]

			// Check if the horizontal ray intersects with a side of the polygon
			if (p.y > minOf(p1.y, p2.y) && p.y <= maxOf(p1.y, p2.y) && p.x <= maxOf(p1.x, p2.x)) {
				// If p1.y != p2.y, calculate the x coordinate of the point of intersection of the polygon side and the ray
				if (p1.y != p2.y) {
					val xIntersect = ((p.y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y).toDouble() + p1.x).toInt()
					// If the ray passes through the vertex of the polygon (p1.x == p2.x) or intersects with the side
					if (p1.x == p2.x || p.x <= xIntersect) {
						counter++
					}
				}
			}

			p1 = p2
		}

		// If the count of intersections is odd, the point is inside the polygon
		return counter % 2 != 0
	}

	fun traversePathForDistance(grid: Array<Array<Tile>>, startPos: Position): Int {
		val path = traversePath(grid, startPos)
		return if (path.size % 2 == 0) path.size / 2 else (path.size - 1) / 2
	}

	class Tile(val type: TileType) {
		var pos: Position? = null
		var left: Tile? = null
		var right: Tile? = null
		var up: Tile? = null
		var down: Tile? = null
	}

	enum class TileType {
		VERTICAL, HORIZONTAL, BEND_L, BEND_J, BEND_7, BEND_F, START, GROUND
	}

	data class Position(val x: Int, val y: Int)

	fun findStartPosition(grid: Array<Array<Tile>>): Position {
		for (y in grid.indices) {
			for (x in grid[y].indices) {
				if (grid[y][x].type == TileType.START) {
					return Position(x, y)
				}
			}
		}
		throw IllegalStateException("Start position not found in the grid")
	}

	private fun getInitialConnectedTiles(startTile: Tile): Pair<Tile?, Tile?> {
		val connectedTiles = listOfNotNull(startTile.up, startTile.down, startTile.left, startTile.right)
		return Pair(connectedTiles[0], connectedTiles[1])
	}

	fun parseGrid(lines: List<String>): Array<Array<Tile>> {
		val grid = Array(lines.size) { y -> Array(lines[0].length) { x -> Tile(determineTileType(lines[y][x])) } }

		for (y in grid.indices) {
			for (x in grid[0].indices) {
				val tile = grid[y][x]
				tile.pos = Position(x, y)
				val pos = Position(x, y)

				if (x > 0 && canConnect(tile, grid[y][x - 1], pos, Position(x - 1, y))) {
					tile.left = grid[y][x - 1]
				}
				if (x < grid[0].size - 1 && canConnect(tile, grid[y][x + 1], pos, Position(x + 1, y))) {
					tile.right = grid[y][x + 1]
				}
				if (y > 0 && canConnect(tile, grid[y - 1][x], pos, Position(x, y - 1))) {
					tile.up = grid[y - 1][x]
				}
				if (y < grid.size - 1 && canConnect(tile, grid[y + 1][x], pos, Position(x, y + 1))) {
					tile.down = grid[y + 1][x]
				}
			}
		}

		return grid
	}

	fun canConnect(tile1: Tile, tile2: Tile, pos1: Position, pos2: Position): Boolean {
		val dx = pos2.x - pos1.x
		val dy = pos2.y - pos1.y

		return when (tile1.type) {
			//to the right
			TileType.HORIZONTAL -> (dx > 0) && (tile2.type in listOf(
				TileType.START,
				TileType.HORIZONTAL,
				TileType.BEND_7,
				TileType.BEND_J
			))
			//to the left
			||	(dx < 0) && (tile2.type in listOf(
				TileType.START,
				TileType.HORIZONTAL,
				TileType.BEND_L,
				TileType.BEND_F
			))

			//down
			TileType.VERTICAL -> (dy > 0) && (tile2.type in listOf(
				TileType.START,
				TileType.VERTICAL,
				TileType.BEND_L,
				TileType.BEND_J
			))
			//up
			|| (dy < 0) && (tile2.type in listOf(
				TileType.START,
				TileType.VERTICAL,
				TileType.BEND_F,
				TileType.BEND_7
			))

			TileType.BEND_L -> ((dx == 1 && tile2.type in listOf(
				TileType.START,
				TileType.HORIZONTAL,
				TileType.BEND_7,
				TileType.BEND_J
			))
			|| (dy == -1 && tile2.type in listOf(
				TileType.START,
				TileType.VERTICAL,
				TileType.BEND_F,
				TileType.BEND_7
			)))

			TileType.BEND_J -> ((dx == -1 && tile2.type in listOf(
				TileType.START,
				TileType.HORIZONTAL,
				TileType.BEND_F,
				TileType.BEND_L
			))
			||	(dy == -1 && tile2.type in listOf(
				TileType.START,
				TileType.VERTICAL,
				TileType.BEND_F,
				TileType.BEND_7
			)))

			TileType.BEND_7 -> ((dx == -1 && tile2.type in listOf(
				TileType.START,
				TileType.HORIZONTAL,
				TileType.BEND_L,
				TileType.BEND_F
			))
			|| (dy == 1 && tile2.type in listOf(
				TileType.START,
				TileType.VERTICAL,
				TileType.BEND_J,
				TileType.BEND_L
			)))

			TileType.BEND_F -> ((dx == 1 && tile2.type in listOf(
				TileType.START,
				TileType.HORIZONTAL,
				TileType.BEND_7,
				TileType.BEND_J
			))
			|| (dy == 1 && tile2.type in listOf(
				TileType.START,
				TileType.VERTICAL,
				TileType.BEND_J,
				TileType.BEND_L
			)))

			TileType.START ->
			((dx == 1 && tile2.type in listOf(
				TileType.HORIZONTAL,
				TileType.BEND_7,
				TileType.BEND_J
			)) ||
			((dx == -1 && tile2.type in listOf(
				TileType.HORIZONTAL,
				TileType.BEND_F,
				TileType.BEND_L
			)) ||
			(dy == 1 && tile2.type in listOf(
				TileType.VERTICAL,
				TileType.BEND_J,
				TileType.BEND_L
			))) ||
			(dy == -1 && tile2.type in listOf(
				TileType.VERTICAL,
				TileType.BEND_F,
				TileType.BEND_7
			)))

			else -> false // TileType.GROUND or any other type should not connect
		}
	}

	private fun determineTileType(char: Char): TileType {
		return when (char) {
			'|' -> TileType.VERTICAL
			'-' -> TileType.HORIZONTAL
			'L' -> TileType.BEND_L
			'J' -> TileType.BEND_J
			'7' -> TileType.BEND_7
			'F' -> TileType.BEND_F
			'S' -> TileType.START
			'.' -> TileType.GROUND
			else -> throw IllegalArgumentException("Unknown tile character: $char")
		}
	}

	private fun getNextTile(currentTile: Tile, previousTile: Tile?): Tile? {
		// For each tile type, determine the possible next tiles and select one that is not the previous tile
		return when (currentTile.type) {
			TileType.VERTICAL, TileType.HORIZONTAL, TileType.START ->
				listOfNotNull(currentTile.up, currentTile.down, currentTile.left, currentTile.right)
					.firstOrNull { it != previousTile }

			TileType.BEND_L ->
				listOfNotNull(currentTile.up, currentTile.right).firstOrNull { it != previousTile }

			TileType.BEND_J ->
				listOfNotNull(currentTile.up, currentTile.left).firstOrNull { it != previousTile }

			TileType.BEND_7 ->
				listOfNotNull(currentTile.down, currentTile.left).firstOrNull { it != previousTile }

			TileType.BEND_F ->
				listOfNotNull(currentTile.down, currentTile.right).firstOrNull { it != previousTile }

			TileType.GROUND -> null
		}
	}

}


