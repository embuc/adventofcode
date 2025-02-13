package y2022

import Task
//--- Day 16: Proboscidea Volcanium ---
class Task16(val input: List<String>) : Task {

	data class Valve(val name: String, val flowRate: Int, val tunnels: List<String>)

	override fun a(): Any {
		val valves = parseInput(input)
		val distances = computeShortestPaths(valves)
		val max = maxPressure("AA", 30, emptySet(), valves, distances)
		return max
	}

	override fun b(): Any {
		return 0
	}

	fun parseInput(input: List<String>): Map<String, Valve> {
		val valves = mutableMapOf<String, Valve>()
		val regex = Regex("Valve (\\w+) has flow rate=(\\d+); tunnels? leads? to valves? (.+)")
		for (line in input) {
			val match = regex.matchEntire(line) ?: throw IllegalArgumentException("Invalid input: $line")
			val (name, flowRate, tunnels) = match.destructured
			valves[name] = Valve(name, flowRate.toInt(), tunnels.split(", "))
		}
		return valves
	}

	fun computeShortestPaths(valves: Map<String, Valve>): Map<String, Map<String, Int>> {
		// Floyd-Warshall algorithm
		val distances = mutableMapOf<String, MutableMap<String, Int>>()
		for (u in valves.keys) {
			distances[u] = mutableMapOf()
			for (v in valves.keys) {
				distances[u]!![v] = if (u == v) 0 else Int.MAX_VALUE / 2
			}
		}
		for (u in valves.keys) {
			for (v in valves[u]!!.tunnels) {
				distances[u]!![v] = 1 //set distance to 1 (we simulate weight 1 for all edges as it takes 1 minute to travel through a tunnel)
			}
		}
		for (k in valves.keys) {
			for (i in valves.keys) {
				for (j in valves.keys) {
					distances[i]!![j] = minOf(
						distances[i]!![j]!!,
						distances[i]!![k]!! + distances[k]!![j]!!
					)
				}
			}
		}
		return distances
	}

	fun maxPressure(
		currentValve: String,
		timeRemaining: Int,
		openedValves: Set<String>,
		valves: Map<String, Valve>,
		distances: Map<String, Map<String, Int>>,
		memo: MutableMap<Triple<String, Int, Set<String>>, Int> = mutableMapOf()
	): Int {
		val key = Triple(currentValve, timeRemaining, openedValves)
		if (key in memo) return memo[key]!!

		var max = 0
		for (nextValve in valves.keys) {
			if (nextValve !in openedValves && valves[nextValve]!!.flowRate > 0) {
				val distance = distances[currentValve]!![nextValve]!!
				val newTime = timeRemaining - distance - 1
				if (newTime >= 0) {
					val pressure = valves[nextValve]!!.flowRate * newTime
					val total = pressure + maxPressure(
						nextValve,
						newTime,
						openedValves + nextValve,
						valves,
						distances,
						memo
					)
					max = maxOf(max, total)
				}
			}
		}
		memo[key] = max
		return max
	}

}