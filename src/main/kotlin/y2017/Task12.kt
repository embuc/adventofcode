package y2017

import Task

//--- Day 12: Digital Plumber ---
class Task12(val input: List<String>) : Task {

	override fun a(): Any {
		val dict = parseGraph()

		val visited = constructGraph(dict, 0)
		return visited.size
	}

	override fun b(): Any {
		val dict = parseGraph()
		var countGroups = 0
		val visited = mutableSetOf<Int>()
		for (k in dict.keys) {
			if (k in visited) continue
			countGroups++
			visited.addAll(constructGraph(dict, k))
		}
		return countGroups
	}

	private fun parseGraph(): MutableMap<Int, MutableList<Int>> {
		val dict = mutableMapOf<Int, MutableList<Int>>()
		for (line in input) {
			val parts = line.split("<->")
			val node = parts[0].trim().toInt()
			val nodes = parts[1].split(",").map { it.trim().toInt() }
			dict.putIfAbsent(node, mutableListOf())
			dict[node]!!.addAll(nodes)
		}
		return dict
	}

	private fun constructGraph(dict: MutableMap<Int, MutableList<Int>>, startNode: Int): MutableSet<Int> {
		val visited = mutableSetOf<Int>()
		val toVisit = mutableListOf(startNode)
		while (toVisit.isNotEmpty()) {
			val current = toVisit.removeAt(0)
			visited.add(current)
			val neighbours = dict[current] ?: continue
			for (neighbour in neighbours) {
				if (neighbour !in visited) {
					toVisit.add(neighbour)
				}
			}
		}
		return visited
	}
}