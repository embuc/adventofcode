package y2024

import Task
import java.util.*

//--- Day 23: LAN Party ---
class Task23(val input: List<String>) : Task {

	override fun a(): Long {
		val dict = mutableMapOf<String, MutableSet<String>>()
		val groups = input.map { it.trim().split("-") }
		for (group in groups) {
			// add in both directions
			val connected = dict.getOrDefault(group[0], mutableSetOf())
			connected.add(group[1])
			dict[group[0]] = connected
			val connected2 = dict.getOrDefault(group[1], mutableSetOf())
			connected2.add(group[0])
			dict[group[1]] = connected2
		}

		val threeGroups = mutableSetOf<List<String>>()
		val visited = mutableSetOf<String>()

		// use dfs to find cycles of length 3
		for (key in dict.keys) {
			if (visited.contains(key)) continue
			val stack = Stack<Pair<String, List<String>>>()
//			DFS is a way to explore a graph by going as deep as possible along each branch before backtracking. It works by:
//			Starting Node: Choosing a starting node.
//			Explore Depth: Exploring one of its neighbors, then the neighbor's neighbor, and so on, until you hit a dead end (a node with no unvisited neighbors).
//			Backtrack: Once you've reached a dead end, you "backtrack" to the previous node and try exploring a different path.
//			Repeat: You keep repeating steps 2 and 3 until all reachable nodes have been visited.
//			Push: When we encounter a new node that we need to explore further, we push it onto the stack.
//			Pop: When we've explored all the neighbors of the current node, we "pop" it off the stack, which leads us to the next node to process.
			stack.push(Pair(key, listOf(key)))

			while (stack.isNotEmpty()) {
				val current = stack.pop()
				val currentNode = current.first
				val path = current.second

				if (path.size == 3) {
					val neighbors = dict[currentNode] ?: continue
					if (neighbors.contains(path[0])) {
						val cycle = path.sorted()
						threeGroups.add(cycle)
					}
					continue
				}

				val neighbors = dict[currentNode] ?: continue
				for (neighbor in neighbors) {
					if (!path.contains(neighbor)) {
						stack.push(Pair(neighbor, path + neighbor))
					}
				}
			}
			visited.add(key)
		}
		val startingWithT = threeGroups.filter { it.any { it.startsWith("t") } }
		return startingWithT.size.toLong()
	}

	override fun b(): String {
		// find all nodes that have edges to each other ('cliques')
		// finding the maximum clique is NP-hard problem!

		val adjList = mutableMapOf<String, MutableSet<String>>()
		for (edgeStr in input) {
			val edge = edgeStr.split("-").map { it.trim() }
			adjList.computeIfAbsent(edge[0]) { mutableSetOf() }.add(edge[1])
			adjList.computeIfAbsent(edge[1]) { mutableSetOf() }.add(edge[0])
		}

		val vertices = adjList.keys.toList().sortedBy { adjList[it]?.size ?: 0 } // Sort by degree
		var maxClique: Set<String> = emptySet()

		fun isClique(nodes: Set<String>, newNode : String): Boolean {
			for (node in nodes) {
				if (!adjList[node]!!.contains(newNode)) {
					return false
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
			val remainingNodesWithoutFirst = remainingNodes.subList(1, remainingNodes.size)

			// Exclude case, continue search
			findMaxCliqueRecursive(currentClique, remainingNodesWithoutFirst)

			// Include case. Check early termination condition
			val newClique = currentClique.plus(node)
			if (isClique(currentClique, node)){
				val remainingClique =  remainingNodesWithoutFirst.filter { isClique(newClique, it) }
				if(newClique.size + remainingClique.size > maxClique.size)
					findMaxCliqueRecursive(newClique, remainingNodesWithoutFirst)
			}
		}

		findMaxCliqueRecursive(emptySet(), vertices)

//		println("Largest Clique: ${maxClique.sorted().joinToString(",")}")
		return maxClique.sorted().joinToString(",")
	}
}