package y2023

import Task
import utils.readInputAsListOfStrings

object Task16 : Task {

	const val EMPTY = '.'
	const val HORIZONTAL = '-'
	const val VERTICAL = '|'
	const val DIAGONAL_LEFT = '/'
	const val DIAGONAL_RIGHT = '\\'

	enum class Direction {
		UP, DOWN, LEFT, RIGHT
	}

	override fun a(): Any {
		val grid = readInputAsListOfStrings("2023_16.txt").map { it.toCharArray() }.toTypedArray()

		return evaluateGrid(solveGrid(grid, Pair(0, 0), Direction.RIGHT))
	}

	override fun b(): Any {
		val grid = readInputAsListOfStrings("2023_16.txt").map { it.toCharArray() }.toTypedArray()
		var maxResult = Int.MIN_VALUE
		//TOP
		for(j in grid[0].indices) {
			val result = evaluateGrid(solveGrid(grid, Pair(0,j), Direction.DOWN))
			maxResult = maxOf(maxResult, result)
		}
		//Bottom
		for(j in grid[0].indices) {
			val result = evaluateGrid(solveGrid(grid, Pair(grid.size-1,j), Direction.UP))
			maxResult = maxOf(maxResult, result)
		}
		//Left
		for(i in grid.indices) {
			val result = evaluateGrid(solveGrid(grid, Pair(i,0), Direction.RIGHT))
			maxResult = maxOf(maxResult, result)
		}
		//Right
		for(i in grid.indices) {
			val result = evaluateGrid(solveGrid(grid, Pair(i,grid[0].size-1), Direction.LEFT))
			maxResult = maxOf(maxResult, result)
		}

		return maxResult

	}

	private fun evaluateGrid(solveGrid: Array<BooleanArray>): Int =
		solveGrid.sumOf { booleanArray -> booleanArray.count() { it } }

	data class PositionAndDirection(val position: Pair<Int, Int>, val direction: Direction)

	fun solveGrid(grid: Array<CharArray>, position: Pair<Int, Int>, direction: Direction): Array<BooleanArray> {
		val visited = Array(grid.size) { BooleanArray(grid[0].size) }
		val cache = mutableSetOf<PositionAndDirection>()
		traverseBeam(grid, visited, position, direction, cache)
		return visited
	}

	fun traverseBeam(
		grid: Array<CharArray>,
		visited: Array<BooleanArray>,
		position: Pair<Int, Int>,
		direction: Direction,
		cache: MutableSet<PositionAndDirection>
	) {
		val (x, y) = position
		val beamWasHere = cache.contains(PositionAndDirection(position, direction))
		if (x !in grid.indices || y !in grid[0].indices || beamWasHere) {
			return
		}

		cache.add(PositionAndDirection(position, direction))
		visited[x][y] = true

		when (grid[x][y]) {
			HORIZONTAL -> {
				when (direction) {
					Direction.LEFT -> {
						traverseBeam(grid, visited, Pair(x, y - 1), Direction.LEFT, cache)
					}

					Direction.RIGHT -> {
						traverseBeam(grid, visited, Pair(x, y + 1), Direction.RIGHT, cache)
					}

					else -> {
						// beam is hitting the flat side of '-', it splits
						traverseBeam(grid, visited, Pair(x, y - 1), Direction.LEFT, cache)
						traverseBeam(grid, visited, Pair(x, y + 1), Direction.RIGHT, cache)
					}
				}
			}

			VERTICAL -> {
				when (direction) {
					Direction.UP -> {
						traverseBeam(grid, visited, Pair(x - 1, y), Direction.UP, cache)
					}

					Direction.DOWN -> {
						traverseBeam(grid, visited, Pair(x + 1, y), Direction.DOWN, cache)
					}

					else -> {
						traverseBeam(grid, visited, Pair(x - 1, y), Direction.UP, cache)
						traverseBeam(grid, visited, Pair(x + 1, y), Direction.DOWN, cache)
					}
				}
			}

			EMPTY -> {
				when (direction) {
					Direction.UP -> {
						traverseBeam(grid, visited, Pair(x - 1, y), Direction.UP, cache)
					}

					Direction.DOWN -> {
						traverseBeam(grid, visited, Pair(x + 1, y), Direction.DOWN, cache)
					}

					Direction.LEFT -> {
						traverseBeam(grid, visited, Pair(x, y - 1), Direction.LEFT, cache)
					}

					Direction.RIGHT -> {
						traverseBeam(grid, visited, Pair(x, y + 1), Direction.RIGHT, cache)
					}
				}
			}

			DIAGONAL_LEFT -> {
				when (direction) {
					Direction.UP -> {
						traverseBeam(grid, visited, Pair(x, y + 1), Direction.RIGHT, cache)
					}

					Direction.DOWN -> {
						traverseBeam(grid, visited, Pair(x, y - 1), Direction.LEFT, cache)
					}

					Direction.LEFT -> {
						traverseBeam(grid, visited, Pair(x + 1, y), Direction.DOWN, cache)
					}

					Direction.RIGHT -> {
						traverseBeam(grid, visited, Pair(x - 1, y), Direction.UP, cache)
					}
				}
			}

			DIAGONAL_RIGHT -> {
				when (direction) {
					Direction.UP -> {
						traverseBeam(grid, visited, Pair(x, y - 1), Direction.LEFT, cache)
					}

					Direction.DOWN -> {
						traverseBeam(grid, visited, Pair(x, y + 1), Direction.RIGHT, cache)
					}

					Direction.LEFT -> {
						traverseBeam(grid, visited, Pair(x - 1, y), Direction.UP, cache)
					}

					Direction.RIGHT -> {
						traverseBeam(grid, visited, Pair(x + 1, y), Direction.DOWN, cache)
					}
				}
			}
		}
	}

	fun printDebugGrid(grid: Array<CharArray>, visited: Array<BooleanArray>) {
		grid.indices.forEach { i ->
			val rowString = grid[i].indices.joinToString("") { j ->
				if (visited[i][j]) "#" else grid[i][j].toString()
			}
			println(rowString)
		}
	}
}