package y2023

import Task
import utils.Point2D
import utils.readInput
import java.util.*

object Task17:Task {

	override fun a(): Any {
		val grid = readInput("2023_17.txt").lines().map { s -> s.map { it.digitToInt() }.toIntArray() }.toTypedArray()
		return solvePart1(grid)
	}

	override fun b(): Any {
		val grid = readInput("2023_17.txt").lines().map { s -> s.map { it.digitToInt() }.toIntArray() }.toTypedArray()
		return solvePart2(grid)
	}

	fun solvePart1(grid: Array<IntArray>): Int {
		return findMinHeatLoss(
			grid,
			initialStates = listOf(State(utils.Point2D(0, 0), Point2D.EAST, 0)),
			minBlocks = 0,
			maxBlocks = 3
		)
	}
	fun solvePart2(grid: Array<IntArray>): Int {
		return findMinHeatLoss(
			grid,
			initialStates = listOf(State(utils.Point2D(0, 0), Point2D.EAST, 0), State(utils.Point2D(0, 0), Point2D.SOUTH, 0)),
			minBlocks = 4,
			maxBlocks = 10
		)
	}

	private fun findMinHeatLoss(grid: Array<IntArray>, initialStates: List<State>, minBlocks: Int, maxBlocks: Int): Int {
		val end = utils.Point2D(grid.first().lastIndex, grid.lastIndex)

		val costs = mutableMapOf<State, Int>().withDefault { Int.MAX_VALUE }
		val toVisit = PriorityQueue<StateWithCost>()

		for (state in initialStates) {
			costs[state] = 0
			toVisit.add(StateWithCost(state, 0))
		}

		while (toVisit.isNotEmpty()) {
			val current = toVisit.poll()
			if (current.state.point == end) {
				return current.cost
			}

			current.state.next(minBlocks, maxBlocks)
				.filter { it.point.y in grid.indices && it.point.x in grid.first().indices }
				.forEach { next ->
					val newCost = current.cost + grid[next.point.y][next.point.x]
					if (newCost < costs.getValue(next)) {
						costs[next] = newCost
						toVisit.add(StateWithCost(next, newCost))
					}
				}
		}

		error("No path found")
	}

	private data class State(val point: Point2D, val dir: Point2D, val blocks: Int) {
		fun next(minBlocks: Int, maxBlocks: Int) = buildList {
			when {
				blocks < minBlocks -> add(copy(point = point + dir, dir = dir, blocks = blocks + 1))
				else               -> {
					val left = Point2D(dir.y, dir.x)
					val right = Point2D(-dir.y, -dir.x)

					add(copy(point = point + left, dir = left, blocks = 1))
					add(copy(point = point + right, dir = right, blocks = 1))

					if (blocks < maxBlocks) {
						add(copy(point = point + dir, dir = dir, blocks = blocks + 1))
					}
				}
			}
		}
	}

	private data class StateWithCost(val state: State, val cost: Int) : Comparable<StateWithCost> {
		override fun compareTo(other: StateWithCost): Int {
			return cost compareTo other.cost
		}
	}



}