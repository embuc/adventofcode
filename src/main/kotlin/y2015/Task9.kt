package y2015

import Task
import templates.Connection
import templates.GraphWithCost

//--- Day 9: All in a Single Night ---
class Task9(val input: List<String>) : Task {
	override fun a(): Int {
		val graph: GraphWithCost = parseToGraph()
		// choose a starting point and use dfs to find all the paths, but use a variant
		// of our minimal dfs, we need hamiltonian paths
		val paths = findAllHamiltonianPaths(graph)
		// find the shortest path
		val shortestPath = paths.minByOrNull { it.second }
		return shortestPath?.second ?: 0
	}

	private fun parseToGraph(): GraphWithCost {
		val graph: GraphWithCost = mutableMapOf()
		input.forEach {
			val (a, b, c) = it.trim().split(" to ", " = ")
			graph.getOrPut(a) { mutableSetOf() }.add(Connection(b, c.toInt()))
			graph.getOrPut(b) { mutableSetOf() }.add(Connection(a, c.toInt()))
		}
		return graph
	}

	fun findHamiltonianPaths(
		graph: GraphWithCost,
		start: String,
		currentPath: List<String> = emptyList(), // Initialize currentPath as empty list
		currentCost: Int = 0,
		allPaths: MutableList<Pair<List<String>,Int>> = mutableListOf()
	):List<Pair<List<String>, Int>> {

		val newPath = currentPath + start
		if (newPath.size == graph.keys.size) {
			allPaths.add(newPath to currentCost)
			return allPaths
		}

		for (connection in graph[start].orEmpty()) {
			if (!newPath.contains(connection.to)) {
				findHamiltonianPaths(
					graph,
					connection.to,
					newPath,
					currentCost + connection.cost,
					allPaths
				)
			}
		}
		return allPaths
	}

	fun findAllHamiltonianPaths(graph: GraphWithCost): List<Pair<List<String>,Int>> {
		val allHamiltonianPaths = mutableListOf<Pair<List<String>,Int>>()
		for(startNode in graph.keys){
			allHamiltonianPaths.addAll(findHamiltonianPaths(graph, startNode, allPaths = mutableListOf()))
		}
		return allHamiltonianPaths
	}

	override fun b(): Any {
		val graph: GraphWithCost = parseToGraph()
		val paths = findAllHamiltonianPaths(graph)
		val shortestPath = paths.maxByOrNull { it.second }
		return shortestPath?.second ?: 0
	}
}