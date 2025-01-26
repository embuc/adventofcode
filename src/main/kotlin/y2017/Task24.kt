package y2017

import Task
import utils.reversed

//--- Day 24: Electromagnetic Moat ---
class Task24(val input: List<String>) : Task {

	override fun a(): Any {
		val sockets = input.map { it.split("/") }.map { it[0].toInt() to it[1].toInt() }.sortedBy { it.first }.toMutableList()

		return buildBridge(sockets).sumOf { it.first + it.second }
	}

	private fun buildBridge(
		sockets: MutableList<Pair<Int, Int>>,
		current: MutableList<Pair<Int, Int>> = mutableListOf(),
		best: MutableList<Pair<Int, Int>> = mutableListOf()
	): List<Pair<Int, Int>> {
		// 1. Base case: if the solution is complete
		if (noMorePossibleSockets(sockets, current)) {
			if (current.sumOf { it.first + it.second } > best.sumOf { it.first + it.second }) {
				best.clear()
				best.addAll(current)
			}
			return current
		}

		// 2. Explore all possible candidates
		for (candidate in getCandidates(sockets, current)) {
			if (candidate in current || candidate.reversed() in current) continue

			val nextPair = if (current.isEmpty() || current.last().second == candidate.first) {
				candidate
			} else if (current.last().second == candidate.second) {
				candidate.reversed()
			} else {
				continue
			}

			current.add(nextPair)
			buildBridge(sockets, current, best)
			current.removeLast()
		}
		return best
	}

	private fun getCandidates(sockets: List<Pair<Int, Int>>, current: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
		if (current.isEmpty()) {
			return sockets.filter { it.first == 0 || it.second == 0 }
		}
		val second = current.last().second
		return sockets.filter { it.first == second || it.second == second }
	}

	private fun noMorePossibleSockets(sockets: List<Pair<Int, Int>>, current: List<Pair<Int, Int>>): Boolean {
		val second = current.lastOrNull()?.second ?: 0
		val filtered = mutableListOf<Pair<Int, Int>>()
		for (socket in sockets) {
			if (socket !in current && socket.reversed() !in current) {
				filtered.add(socket)
			}
		}
		return filtered.none { it.first == second || it.second == second }
	}

	override fun b(): Any {

		return 0
	}
}


