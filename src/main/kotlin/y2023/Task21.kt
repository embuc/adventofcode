package y2023

import Task
import utils.borrowed.Grid
import utils.borrowed.Point
import utils.pm
import utils.readInputAsListOfStrings
import java.util.*

// --- Day 21: Step Counter ---
// This one was too hard for me to solve in the given time-frame, sort of got it right for part one, but it was way too
// hasty of this solution. Until I have time to explore this problem more, solution is from user: github.com/eagely.
// *** Needs more work ***.
object Task21:Task {

	override fun a(): Any {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2023/2023_21.txt")
		return solveA(input, 64)
	}

	fun solveA(input: List<String>, steps: Int): Any {
		val grid = Grid.of(input)
		val queue = ArrayDeque<Pair<Point, Int>>()
		queue.add(grid['S']!! to 0)
		val visited = hashSetOf<Point>()
		while (queue.isNotEmpty()) {
			val (current, move) = queue.removeFirst()
			if(move > steps) break
			if (current in visited) continue
			if(move pm 2 == 0) visited.add(current)
			queue.addAll(current.getCardinalNeighbors().filter { grid[it] != '#' }.map { it to move + 1 })
		}
		return visited.size
	}

	override fun b(): Any {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2023/2023_21.txt")
		return solveB(input, 26501365L)
	}

	fun solveB(input: List<String>, l: Long): Any {
		return solvePart2(input, l)
	}

	fun solvePart2(input: List<String>, steps:Long): Any {
		val grid = Grid.of(input)
		var delta = 0L
		var skip = 0L
		val queue = ArrayDeque<Pair<Point, Long>>()
		queue.add(Pair(grid['S']!!, 0))
		val visited = hashSetOf<Point>()
		val size = Point(grid.rows, grid.columns)
		val cycle = size.x * 2
		var lastStep = 0L
		var previousPlots = 0L
		var delta1 = 0L
		var delta2 = 0L
		var plots = 0L
		while (queue.isNotEmpty()) {
			val (position, step) = queue.removeFirst()
			if (position in visited) continue
			if (step % 2 == 1L) visited.add(position)
			if (step % cycle == 66L && step > lastStep) {
				lastStep = step
				if (plots - previousPlots - delta1 == delta2) {
					delta = plots - previousPlots + delta2
					skip = step - 1
					break
				}
				delta2 = (plots - previousPlots) - delta1
				delta1 = plots - previousPlots
				previousPlots = plots
			}
			plots = visited.size.toLong()
			queue.addAll(position.getCardinalNeighbors().filter { grid[it % size] != '#' }.map { it to step + 1 })
		}
		while (skip < steps) {
			skip += cycle
			plots += delta
			delta += delta2
		}
		return plots
	}

}