package y2023

import Task
import utils.getLinesFromFile
import kotlin.math.abs

class Uppg11:Task {

	override fun a(): Any {
		val lines = getLinesFromFile("Input11.txt")
		val grid = expandGrid(lines, 2)
		val galaxyPairs = findAllGalaxyPairs(grid)
		return findSumOfPaths(galaxyPairs)
	}

	fun findSumOfPaths(galaxyPairs: List<Pair<Tile, Tile>>): Long {
		val shortestPaths = galaxyPairs.map {
			//bfs(it.first, it.second, grid)
			manhattanDistanceLong(it.first, it.second)
		}.sumOf { it }
		return shortestPaths
	}

	override fun b(): Any {
		val lines = getLinesFromFile("Input11.txt")
		val grid = expandGrid(lines,1_000_000)
		val galaxyPairs = findAllGalaxyPairs(grid)
		return findSumOfPaths(galaxyPairs)
	}

	private fun manhattanDistanceLong(p1: Tile, p2: Tile): Long {
		val deltaX = abs(p1.x - p2.x).toLong()
		val deltaY = abs(p1.y - p2.y).toLong()
		return deltaX + deltaY
	}

	fun printGrid(grid: List<List<Tile>>) {
		for (row in grid) {
			for (tile in row) {
				print("$tile ")
			}
			println()
		}
	}

	fun printGridCoord(grid: List<List<Tile>>) {
		for (row in grid) {
			for (tile in row) {
				print("(${tile.x},${tile.y}) ")
			}
			println()
		}
	}

	fun expandGrid(lines: List<String>, times: Long): List<List<Tile>> {
		val colCount = lines.first().length
		val rowCount = lines.size

		val grid = List(rowCount) { r -> List(colCount) { c -> Tile(
			r.toLong(),
			c.toLong(),
			lines[r][c],
			"."

		) } }
		val fullRows = BooleanArray(rowCount) { true }
		val fullCols = BooleanArray(colCount) { true }

		// Labeling galaxies
		var galaxyCounter = 1
		for (r in grid.indices) {
			for (c in grid[0].indices) {
				if (grid[r][c].isGalaxy()) {
					grid[r][c].label = galaxyCounter++.toString()
				}
			}
		}
		println("Galaxy count: ${galaxyCounter - 1}")

		// Check for full rows and columns
		for (r in 0 until rowCount) {
			for (c in 0 until colCount) {
				if (grid[r][c].isGalaxy()) {
					fullRows[r] = false
					fullCols[c] = false
				}
			}
		}

		// Expand rows
		val expandedRows = mutableListOf<List<Tile>>()
		var i = 0L;
		grid.forEachIndexed { index, row ->
			row.forEach { tile -> tile.x += i*(times-1) }
			expandedRows.add(row)
			if (fullRows[index]) {
				println("Adding row at index $index")
				i++
			}
		}

		// Expand columns
		val expandedGrid = expandedRows.map { row ->
			val newRow = mutableListOf<Tile>()
			i = 0;
			row.forEachIndexed { index, tile ->
				tile.y += i*(times-1)
				newRow.add(tile)
				if (fullCols[index]) {
					i++
				}
			}
			newRow.toTypedArray().toList()
		}

		return expandedGrid
	}

	fun findAllGalaxyPairs(grid: List<List<Tile>>): List<Pair<Tile, Tile>> {
		val galaxies = grid.flatten().filter { it.isGalaxy() }
		val pairs = mutableListOf<Pair<Tile, Tile>>()

		for (i in galaxies.indices) {
			for (j in i + 1 until galaxies.size) {
				pairs.add(Pair(galaxies[i], galaxies[j]))
			}
		}

		return pairs
	}

//	fun bfs(start: Tile, end: Tile, grid: List<List<Tile>>): List<Tile> {
//		val visited = mutableSetOf<Tile>()
//		val queue = ArrayDeque<List<Tile>>()
//		queue.add(listOf(start))
//
//		while (queue.isNotEmpty()) {
//			val path = queue.removeFirst()
//			val node = path.last()
//
//			if (node == end) {
//				return path
//			}
//
//			if (node in visited) {
//				continue
//			}
//
//			visited.add(node)
//
//			val neighbors = getNeighbors(node, grid)
//			for (neighbor in neighbors) {
//				if (neighbor !in visited) {
//					val newPath = path.toMutableList()
//					newPath.add(neighbor)
//					queue.add(newPath)
//				}
//			}
//		}
//
//		return emptyList()
//	}

//	fun getNeighbors(tile: Tile, grid: List<List<Tile>>): List<Tile> {
//		val directions = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
//		val neighbors = mutableListOf<Tile>()
//
//		for ((dx, dy) in directions) {
//			val newX = tile.x + dx
//			val newY = tile.y + dy
//			if (newX in grid.indices && newY in grid[newX].indices) {
//				neighbors.add(grid[newX][newY])
//			}
//		}
//
//		return neighbors
//	}
}

class Tile(var x: Long, var y: Long, private val value: Char, var label: String) {

	fun isGalaxy(): Boolean {
		return value == '#'
	}
	override fun toString(): String {
		return if (isGalaxy()) label.toString() else value.toString()
	}
}
