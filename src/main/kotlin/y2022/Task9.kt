package y2022

import Task
import kotlin.math.abs

//--- Day 9: Rope Bridge ---
class Task9(val input: List<String>) : Task {

	override fun a(): Any {
		var head: Pair<Int, Int> = Pair(0, 0)
		var tail: Pair<Int, Int> = Pair(0, 0)
		val visited = mutableSetOf<Pair<Int, Int>>()
		visited.add(tail)
		for (line in input) {
			val (direction, steps) = line.split(" ")
			println("dir: $direction steps: $steps")
			repeat(steps.toInt()) {
				when (direction) {
					"L" -> {
						head = Pair(head.first, head.second - 1)
						tail = calculateTail(head, tail)
					}

					"D" -> {
						head = Pair(head.first + 1, head.second)
						tail = calculateTail(head, tail)
					}

					"R" -> {
						head = Pair(head.first, head.second + 1)
						tail = calculateTail(head, tail)
					}

					"U" -> {
						head = Pair(head.first - 1, head.second)
						tail = calculateTail(head, tail)
					}
				}
				visited.add(tail)
			}

		}
		return visited.size
	}

	private fun calculateTail(
		head: Pair<Int, Int>,
		tail: Pair<Int, Int>
	): Pair<Int, Int> {
		// if head within all neighbors of tail or on the same tile, return same tail
		if (abs(head.first - tail.first) <= 1 && abs(head.second - tail.second) <= 1
		) {
			return tail
		}
		// else check if head is on the same row or column as tail and return the new tail updated by that row or column
		if (head.first == tail.first) {
			return Pair(tail.first, tail.second + if (head.second > tail.second) 1 else -1)
		}
		if (head.second == tail.second) {
			return Pair(tail.first + if (head.first > tail.first) 1 else -1, tail.second)
		}
		// else return the new tail updated by the diagonal
		return Pair(
			tail.first + if (head.first > tail.first) 1 else -1,
			tail.second + if (head.second > tail.second) 1 else -1
		)
	}

	override fun b(): Any {
		return 0
	}
}