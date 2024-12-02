package y2023

import Task
import utils.Tile
import utils.readInputAsListOfStrings
import kotlin.math.abs

// --- Day 11: Cosmic Expansion ---
// Solved this one first with DFS, but it was too slow, then I read discussion and everyone was using Manhattan distance
// which is both simpler and faster -can't un-see it now :)
object Task11:Task  {

	override fun a(): Any {
		val lines = readInputAsListOfStrings("~/git/aoc-inputs/2023/2023_11.txt")
		val grid = expandGrid(lines, 2)
		val galaxyPairs = findAllGalaxyPairs(grid)
		return findSumOfPaths(galaxyPairs)
	}

	fun findSumOfPaths(galaxyPairs: List<Pair<Tile, Tile>>): Long {
		val shortestPaths = galaxyPairs.map {
			manhattanDistanceLong(it.first, it.second)
		}.sumOf { it }
		return shortestPaths
	}

	override fun b(): Any {
		val lines = readInputAsListOfStrings("~/git/aoc-inputs/2023/2023_11.txt")
		val grid = expandGrid(lines,1_000_000)
		val galaxyPairs = findAllGalaxyPairs(grid)
		return findSumOfPaths(galaxyPairs)
	}

	private fun manhattanDistanceLong(p1: Tile, p2: Tile): Long {
		val deltaX = abs(p1.x - p2.x).toLong()
		val deltaY = abs(p1.y - p2.y).toLong()
		return deltaX + deltaY
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

	fun Tile.isGalaxy(): Boolean = this.value == '#'

	fun Tile.customToString(): String {
		return if (this.isGalaxy()) this.label else this.value.toString()
	}



}

