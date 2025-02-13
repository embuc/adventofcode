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
		val valves = parseInput(input)
		val distances = computeShortestPaths(valves)

		// Precompute maximum possible pressure for each valve at each time
		val maxPotentialPressure = mutableMapOf<Pair<String, Int>, Int>()
		val valvesWithFlow = valves.filter { it.value.flowRate > 0 }

		for (valve in valvesWithFlow.keys) {
			for (time in 0..26) {
				maxPotentialPressure[valve to time] = valves[valve]!!.flowRate * time
			}
		}

		// Precompute best possible pressure for remaining time
		val bestPossibleAtTime = IntArray(27)
		for (time in 0..26) {
			bestPossibleAtTime[time] = valvesWithFlow.values
				.sortedByDescending { it.flowRate }
				.take((time + 1) / 2) // We can at best open a valve every 2 minutes
				.sumOf { it.flowRate * (time - 1) }
		}

		var maxPressure = 0
		val valvesWithFlowList = valvesWithFlow.keys.toList()
		val totalValves = valvesWithFlowList.size

		// Function to estimate upper bound for a path
		fun estimateUpperBound(
			valve: String,
			timeLeft: Int,
			opened: Set<String>,
			allowedValves: Set<String>
		): Int {
			val remainingValves = allowedValves - opened
			if (remainingValves.isEmpty() || timeLeft <= 1) return 0

			// Sort remaining valves by potential pressure
			val sorted = remainingValves.sortedByDescending { v ->
				valves[v]!!.flowRate * maxOf(0, timeLeft - distances[valve]!![v]!! - 1)
			}

			var estimate = 0
			var currentTime = timeLeft
			var currentValve = valve

			// Simulate best case scenario where we open highest flow valves first
			for (nextValve in sorted) {
				val distance = distances[currentValve]!![nextValve]!! + 1
				if (currentTime <= distance) break

				currentTime -= distance
				estimate += valves[nextValve]!!.flowRate * currentTime
				currentValve = nextValve

				if (currentTime <= 2) break // Not enough time to reach another valve
			}

			return estimate
		}

		// Modified maxPressure function with better pruning
		fun maxPressureWithPruning(
			currentValve: String,
			timeRemaining: Int,
			openedValves: Set<String>,
			allowedValves: Set<String>,
			memo: MutableMap<Triple<String, Int, Set<String>>, Int>
		): Int {
			val key = Triple(currentValve, timeRemaining, openedValves)
			memo[key]?.let { return it }

			// Early pruning using precomputed best possible pressure
			if (timeRemaining <= 1) return 0

			val upperBound = estimateUpperBound(currentValve, timeRemaining, openedValves, allowedValves)
			if (upperBound == 0) return 0

			var max = 0
			val remainingValves = allowedValves - openedValves

			// Sort valves by potential pressure/distance ratio
			val sortedValves = remainingValves.sortedByDescending { valve ->
				val timeAfterMove = timeRemaining - distances[currentValve]!![valve]!! - 1
				if (timeAfterMove <= 0) 0.0 else
					valves[valve]!!.flowRate.toDouble() / (distances[currentValve]!![valve]!! + 1)
			}

			for (nextValve in sortedValves) {
				val distance = distances[currentValve]!![nextValve]!!
				val newTime = timeRemaining - distance - 1

				if (newTime <= 1) continue

				// Pruning: if even with perfect play we can't beat max, skip this branch
				val potential = valves[nextValve]!!.flowRate * newTime +
						estimateUpperBound(nextValve, newTime, openedValves + nextValve, allowedValves)
				if (potential <= max) continue

				val pressure = valves[nextValve]!!.flowRate * newTime
				val total = pressure + maxPressureWithPruning(
					nextValve,
					newTime,
					openedValves + nextValve,
					allowedValves,
					memo
				)
				max = maxOf(max, total)
			}

			memo[key] = max
			return max
		}

		// Try different valve distributions more intelligently
		val sortedByFlow = valvesWithFlowList.sortedByDescending { valves[it]!!.flowRate }
		val midFlow = valves[sortedByFlow[sortedByFlow.size / 2]]!!.flowRate

		// Start with most promising distributions
		for (yourCount in totalValves/2-1..totalValves/2+1) {
			val indices = (0 until totalValves).toList()
			for (yourIndices in generateCombinations(indices, yourCount)) {
				val yourValves = yourIndices.map { valvesWithFlowList[it] }.toSet()
				val elephantValves = valvesWithFlowList.toSet() - yourValves

				// More aggressive pruning of distributions
				val yourTotalFlow = yourValves.sumOf { valves[it]!!.flowRate }
				val elephantTotalFlow = elephantValves.sumOf { valves[it]!!.flowRate }

				// Skip if distribution is too unbalanced
				if (yourTotalFlow < elephantTotalFlow * 0.4 ||
					elephantTotalFlow < yourTotalFlow * 0.4) continue

				val yourPressure = maxPressureWithPruning(
					"AA",
					26,
					emptySet(),
					yourValves,
					mutableMapOf()
				)

				// Early exit if this path can't possibly beat the max
				if (yourPressure * 2 < maxPressure) continue

				val elephantPressure = maxPressureWithPruning(
					"AA",
					26,
					emptySet(),
					elephantValves,
					mutableMapOf()
				)

				maxPressure = maxOf(maxPressure, yourPressure + elephantPressure)
			}
		}

		return maxPressure
	}

	// Optimized combination generator
	fun generateCombinations(indices: List<Int>, k: Int): Sequence<List<Int>> = sequence {
		val n = indices.size
		if (k in 0..n) {
			val result = MutableList(k) { it }
			yield(result.toList())

			while (true) {
				var i = k - 1
				while (i >= 0 && result[i] == i + n - k) i--
				if (i < 0) break

				result[i]++
				for (j in i + 1 until k) {
					result[j] = result[j - 1] + 1
				}
				yield(result.toList())
			}
		}
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