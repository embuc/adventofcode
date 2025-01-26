package y2017

import Task

//--- Day 24: Electromagnetic Moat ---
class Task24(val input: List<String>) : Task {
	private val memo = mutableMapOf<Pair<Set<Pair<Int, Int>>, Int>, Int>()

	override fun a(): Any {
		val sockets = input.map { it.split("/") }
			.map { it[0].toInt() to it[1].toInt() }
		return buildBridge(sockets.toSet(), mutableSetOf(), 0, 0)
	}

	private fun buildBridge(
		remaining: Set<Pair<Int, Int>>,
		current: MutableSet<Pair<Int, Int>>,
		lastPort: Int,
		bestScore: Int
	): Int {
		if (remaining.isEmpty() || isIsolated(remaining, lastPort)) {
			return maxOf(bestScore, current.sumOf { it.first + it.second })
		}

		val state = remaining to lastPort
		memo[state]?.let { return maxOf(it, bestScore) }

		var maxScore = bestScore
		val candidates = remaining.filter { it.first == lastPort || it.second == lastPort }

		for (socket in candidates) {
			val nextPort = if (socket.first == lastPort) socket.second else socket.first
			current.add(socket)
			maxScore = buildBridge(
				remaining - socket,
				current,
				nextPort,
				maxScore
			)
			current.remove(socket)
		}

		memo[state] = maxScore
		return maxScore
	}

	private fun isIsolated(sockets: Set<Pair<Int, Int>>, port: Int): Boolean {
		return port != 0 && sockets.none { it.first == port || it.second == port }
	}

	override fun b(): Any {
		val sockets = input.map { it.split("/") }
			.map { it[0].toInt() to it[1].toInt() }
			.toSet()
		return buildBridge(sockets, mutableSetOf(), 0).strength
	}

	data class Result(val length: Int, val strength: Int)

	private fun buildBridge(
		remaining: Set<Pair<Int, Int>>,
		current: MutableSet<Pair<Int, Int>>,
		lastPort: Int
	): Result {
		if (remaining.isEmpty() || isIsolated(remaining, lastPort)) {
			return Result(
				length = current.size,
				strength = current.sumOf { it.first + it.second }
			)
		}

		val state = remaining to lastPort
		memoB[state]?.let { return it }

		var bestResult = Result(0, 0)
		val candidates = remaining.filter { it.first == lastPort || it.second == lastPort }

		for (socket in candidates) {
			val nextPort = if (socket.first == lastPort) socket.second else socket.first
			current.add(socket)
			val result = buildBridge(
				remaining - socket,
				current,
				nextPort
			)
			current.remove(socket)

			if (result.length > bestResult.length ||
				(result.length == bestResult.length && result.strength > bestResult.strength)) {
				bestResult = result
			}
		}

		memoB[state] = bestResult
		return bestResult
	}

	private val memoB = mutableMapOf<Pair<Set<Pair<Int, Int>>, Int>, Result>()
}


