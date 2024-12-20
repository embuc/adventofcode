package y2023

import Task
import utils.borrowed.Point2D
import utils.readInputAsString
import java.util.*

// --- Day 17: Clumsy Crucible ---
// This one was a bit tricky. It was clear that it was a shortest path problem, but the twist was that you could only
// move a certain number of blocks in a given direction before you had to turn. This meant that you couldn't use a
// simple BFS, but had to keep track of the number of blocks moved in the current direction. I spent a lot of time
// on this one, but then I moved on to other problems and came back to this one later. This solution is not mine but
// its from user: github.com/ClouddJR.
// *** Needs more work ***.
object Task17:Task {

	override fun a(): Any {
		val grid = readInputAsString("~/git/aoc-inputs/2023/2023_17.txt").lines().map { s -> s.map { it.digitToInt() }.toIntArray() }.toTypedArray()
		return solvePart1(grid)
	}

	override fun b(): Any {
		val grid = readInputAsString("~/git/aoc-inputs/2023/2023_17.txt").lines().map { s -> s.map { it.digitToInt() }.toIntArray() }.toTypedArray()
		return solvePart2(grid)
	}

	fun solvePart1(grid: Array<IntArray>): Int {
		return findMinHeatLoss(
			grid,
			initialStates = listOf(State(Point2D(0, 0), Point2D.EAST, 0)),
			minBlocks = 0,
			maxBlocks = 3
		)
	}
	fun solvePart2(grid: Array<IntArray>): Int {
		return findMinHeatLoss(
			grid,
			initialStates = listOf(State(Point2D(0, 0), Point2D.EAST, 0), State(Point2D(0, 0), Point2D.SOUTH, 0)),
			minBlocks = 4,
			maxBlocks = 10
		)
	}

	private fun findMinHeatLoss(grid: Array<IntArray>, initialStates: List<State>, minBlocks: Int, maxBlocks: Int): Int {
		val end = Point2D(grid.first().lastIndex, grid.lastIndex)

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