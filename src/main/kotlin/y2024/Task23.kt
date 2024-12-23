package y2024

import Task
import java.util.*

//--- Day 23: LAN Party ---
class Task23(val input: List<String>) : Task {

	override fun a(): Long {
		val dict = mutableMapOf<String, MutableSet<String>>()
		val groups = input.map { it.trim().split("-") }
		for (group in groups) {
			val connected = dict.getOrDefault(group[0], mutableSetOf())
			connected.add(group[1])
			dict[group[0]] = connected
			val connected2 = dict.getOrDefault(group[1], mutableSetOf())
			connected2.add(group[0])
			dict[group[1]] = connected2
		}

		val threeGroups = mutableListOf<List<String>>()
		val visited = mutableSetOf<String>()

		for (key in dict.keys) {
			if (visited.contains(key)) continue // Skip nodes that have already been part of cycles.
			val stack = Stack<Pair<String, List<String>>>()
			stack.push(Pair(key, listOf(key)))

			while (stack.isNotEmpty()) {
				val current = stack.pop()
				val currentNode = current.first
				val path = current.second

				if (path.size == 3) {
					val neighbors = dict[currentNode] ?: continue
					if (neighbors.contains(path[0])) {
						val cycle = path.sorted()
						if (!threeGroups.any { it.joinToString() == cycle.joinToString() }) {
							threeGroups.add(cycle)
						}

					}
					continue
				}

				val neighbors = dict[currentNode] ?: continue // Handle nodes with no connections.
				for (neighbor in neighbors) {
					if (!path.contains(neighbor)) {
						stack.push(Pair(neighbor, path + neighbor))
					}
				}
			}
			visited.add(key)
		}

		println("Cycles of length 3:")
		threeGroups.forEach { println(it) }
		val startingWithT = threeGroups.filter { it.any { it.startsWith("t") } }
		return startingWithT.size.toLong()
	}

	override fun b(): String {
		// find all nodes that have edges to each other ('cliques')
		val adjList = mutableMapOf<String, MutableSet<String>>()
		for (edgeStr in input) {
			val edge = edgeStr.split("-").map { it.trim() }
			adjList.computeIfAbsent(edge[0]) { mutableSetOf() }.add(edge[1])
			adjList.computeIfAbsent(edge[1]) { mutableSetOf() }.add(edge[0])
		}

		val vertices = adjList.keys.toList()
		var maxClique: Set<String> = emptySet()

		fun isClique(nodes: Set<String>): Boolean {
			if (nodes.size <= 1) return true
			for (v1 in nodes) {
				for (v2 in nodes) {
					if (v1 != v2) {
						if (!adjList[v1]!!.contains(v2)) {
							return false
						}
					}
				}
			}
			return true
		}

		fun findMaxCliqueRecursive(currentClique: Set<String>, remainingNodes: List<String>) {
			if (remainingNodes.isEmpty()) {
				if (currentClique.size > maxClique.size) {
					maxClique = currentClique.toSet()
				}
				return
			}

			val node = remainingNodes[0]
			val remainingNodesWithoutFirst = remainingNodes.subList(1,remainingNodes.size)

			findMaxCliqueRecursive(currentClique, remainingNodesWithoutFirst)

			val newClique = currentClique.plus(node)
			if (isClique(newClique)){
				findMaxCliqueRecursive(newClique, remainingNodesWithoutFirst)
			}
		}

		findMaxCliqueRecursive(emptySet(), vertices)

		println("Largest Clique: ${maxClique.sorted().joinToString(",")}")
		return maxClique.sorted().joinToString(",")
	}
}