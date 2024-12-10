package y2024

import Task
import utils.*
import kotlin.math.abs

/* --- Day 8: Resonant Collinearity --- */
class Task8(val input: List<String>) : Task {

	override fun a(): Any {
		val grid = toGrid(input)
		val antenasList = findAntenas(grid)
		for (antenas in antenasList) {
			for (i in 0 until antenas.size) {
				for (j in i + 1 until antenas.size) {
					val (x1, y1, _) = antenas[i]
					val (x2, y2, _) = antenas[j]
					val dx = Math.abs(x2 - x1)
					val dy = Math.abs(y2 - y1)
					val (xx1, yy1, xx2, yy2) = calculateNewPositions(x1, y1, x2, y2, dx, dy)
					markGrid(grid, xx1, yy1)
					markGrid(grid, xx2, yy2)
				}
			}
		}
		return countChar(grid, '#')
	}

	private fun markGrid(grid: Array<Array<Triple<Int, Int, Char>>>, x: Int, y: Int) {
		if (isInsideGrid(grid,x,y)) {
			grid[x][y] = Triple(x, y, '#')
		}
	}

	private fun calculateNewPositions(x1: Int, y1: Int, x2: Int, y2: Int, dx:Int, dy:Int): Quadruple<Int, Int, Int, Int> {
		val xx1 = if (x1 > x2) x1 + dx else x1 - dx
		val yy1 = if (y1 > y2) y1 + dy else y1 - dy
		val xx2 = if (x1 > x2) x2 - dx else x2 + dx
		val yy2 = if (y1 > y2) y2 - dy else y2 + dy
		return Quadruple(xx1, yy1, xx2, yy2)
	}

	data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

	override fun b(): Any {
		val grid = toGrid(input)
		val antenasList = findAntenas(grid)
		for (antenas in antenasList) {
			for (i in antenas.indices) {
				for (j in i + 1 until antenas.size) {
					val (x1, y1, _) = antenas[i]
					val (x2, y2, _) = antenas[j]
					calculateAndMarkNewPositions(grid, x1, y1, x2, y2)
				}
			}
		}
//		printGrid(grid)
		return countChar(grid){ it != '.' }

	}

	private fun calculateAndMarkNewPositions(
		grid: Array<Array<Triple<Int, Int, Char>>>,
		x1: Int,
		y1: Int,
		x2: Int,
		y2: Int
	) {
		val dx = abs(x2 - x1)
		val dy = abs(y2 - y1)

		var quad = Quadruple(x1, y1, x2, y2)
		while (true) {
			quad = calculateNewPositions(quad.first, quad.second, quad.third, quad.fourth, dx, dy)
			markGrid(grid, quad.first, quad.second)
			markGrid(grid, quad.third, quad.fourth)
			if(!isInsideGrid(grid, quad.first, quad.second) && !isInsideGrid(grid, quad.third, quad.fourth)){
				break
			}
		}
	}

	private fun findAntenas(grid: Array<Array<Triple<Int, Int, Char>>>): MutableCollection<List<Triple<Int, Int, Char>>> {
		val dict = mutableMapOf<Char, List<Triple<Int, Int, Char>>>()
		for (i in grid.indices) {
			for (j in grid[i].indices) {
				val c = grid[i][j].third
				if (c != '.') {
					dict[c] = dict.getOrDefault(c, mutableListOf()) + Triple(i, j, c)
				}
			}
		}
		return dict.values
	}
}