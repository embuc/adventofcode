package y2022

import Task
import kotlin.math.abs

//--- Day 9: Rope Bridge ---
class Task9(val input: List<String>) : Task {

	override fun a(): Any {
		return moveRope(1)
	}

	override fun b(): Any {
		return moveRope(9)
	}

	private fun moveRope(numTails: Int): Int {
		var head: Pair<Int, Int> = Pair(0, 0)
		val tails = MutableList(numTails) { Pair(0, 0) }
		val visited = mutableSetOf<Pair<Int, Int>>()
		visited.add(tails.last())
		for (line in input) {
			val (direction, steps) = line.split(" ")
			repeat(steps.toInt()) {
				when (direction) {
					"L" -> {
						head = Pair(head.first, head.second - 1)
						updateTails(tails, head)
					}

					"D" -> {
						head = Pair(head.first + 1, head.second)
						updateTails(tails, head)
					}

					"R" -> {
						head = Pair(head.first, head.second + 1)
						updateTails(tails, head)
					}

					"U" -> {
						head = Pair(head.first - 1, head.second)
						updateTails(tails, head)
					}
				}
				visited.add(tails.last())
			}
		}
		return visited.size
	}

	private fun updateTails(tails: MutableList<Pair<Int, Int>>, head: Pair<Int, Int>) {
		for (i in tails.indices) {
			if (i == 0) {
				tails[i] = calculateTail(head, tails[i])
			} else {
				tails[i] = calculateTail(tails[i - 1], tails[i])
			}
		}
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
}