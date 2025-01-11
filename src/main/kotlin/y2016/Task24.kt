package y2016

import Task
import templates.MinimalBFS
import templates.MinimalBFS.Node
import utils.toCharGrid

//--- Day 24: Air Duct Spelunking ---
class Task24(val input: List<String>) : Task {

	override fun a(): Any {
		val grid = toCharGrid(input)
//		printGridColor(grid)
		val nodes = getNodes(grid)
		val edgesMap = getEdgesMap(grid, nodes)
		// TSP with backtracking
		return backtrackA(edgesMap, nodes).sumOf { it.weight }
	}

	private fun getEdgesMap(grid: Array<CharArray>, nodes: List<Node>): MutableMap<Pair<Node, Node>, Int> {
		val edgesMap = mutableMapOf<Pair<Node, Node>, Int>()
		fun addEdge(fromNode: Node, toNode: Node) {
			// -1 is because the size of the path includes the start node
			val shortestPathLength = MinimalBFS.findShortestPath(grid, fromNode, toNode).size - 1
			edgesMap[Pair(fromNode, toNode)] = shortestPathLength
			edgesMap[Pair(toNode, fromNode)] = shortestPathLength
		}
		for (i in 0..7) {
			for (j in (i + 1)..7) {
				addEdge(nodes[i], nodes[j])
			}
		}
		return edgesMap
	}

	// Helper function to find the shortest path and add it to edgesMap
	private fun getNodes(grid: Array<CharArray>): List<Node> {
		return (0..7).map { i ->
			val c = MinimalBFS.findChar(grid, i.toString().first())
			Node(c[0], c[1], i.toString())
		}
	}

	private data class Edge(val from: Node, val to: Node, val weight: Int)

	private fun backtrackA(
		dict: MutableMap<Pair<Node, Node>, Int>,
		nodes: List<Node>,
		currentPath: MutableList<Edge> = mutableListOf(),
		bestPath: MutableList<Edge> = mutableListOf(),
		visitedNodes: MutableSet<Node> = mutableSetOf()
	): List<Edge> {

		// Initialize with node 0 if starting
		if (currentPath.isEmpty()) {
			visitedNodes.add(nodes[0])
		}

		// base case
		if (visitedNodes.size == nodes.size) {
			val currentCost = currentPath.sumOf { it.weight }
			val bestCost = if(bestPath.isEmpty()) Int.MAX_VALUE else bestPath.sumOf { it.weight }
			if (currentCost < bestCost) {
				bestPath.clear()
				bestPath.addAll(currentPath)
			}
			return bestPath
		}

		val currentNode = if (currentPath.isEmpty()) nodes[0] else currentPath.last().to

		for (nextNode in nodes) {
			if (nextNode !in visitedNodes) {
				val edge = Edge(currentNode, nextNode, dict[currentNode to nextNode]!!)
				currentPath.add(edge)
				visitedNodes.add(nextNode)

				backtrackA(dict, nodes, currentPath, bestPath, visitedNodes)

				currentPath.removeAt(currentPath.size - 1)
				visitedNodes.remove(nextNode)
			}
		}

		return bestPath
	}

	override fun b(): Any {
		val grid = toCharGrid(input)
		val nodes = mutableListOf<Node>()
		nodes.addAll(getNodes(grid))
		val edgesMap = getEdgesMap(grid, nodes)
		// TSP with backtracking
		val bestPath = mutableListOf<Edge>()
		backtrackB(edgesMap, nodes, bestPath = bestPath)
		return bestPath.sumOf { it.weight }
	}

	private fun backtrackB(
		dict: MutableMap<Pair<Node, Node>, Int>,
		nodes: List<Node>,
		currentPath: MutableList<Edge> = mutableListOf(),
		bestPath: MutableList<Edge> = mutableListOf(),
		visitedNodes: MutableSet<Node> = mutableSetOf(),
		depth: Int = 0
	): List<Edge> {

		// Initialize with node 0 if starting
		if (currentPath.isEmpty()) {
			visitedNodes.add(nodes[0])
		}

		val currentNode = if (currentPath.isEmpty()) nodes[0] else currentPath.last().to

		// Base case
		if (visitedNodes.size == nodes.size) {
			val returnEdge = Edge(currentNode, nodes[0], dict[currentNode to nodes[0]]!!)

			val currentCost = currentPath.sumOf { it.weight } + returnEdge.weight
			val bestCost = if(bestPath.isEmpty()) Int.MAX_VALUE else bestPath.sumOf { it.weight }

			if (currentCost < bestCost) {
				bestPath.clear()
				bestPath.addAll(currentPath)
				bestPath.add(returnEdge)
			}
			return bestPath
		}

		// Try all possible next moves
		for (nextNode in nodes) {
			if (nextNode !in visitedNodes) {
				val edge = Edge(currentNode, nextNode, dict[currentNode to nextNode]!!)
				currentPath.add(edge)
				visitedNodes.add(nextNode)

				backtrackB(dict, nodes, currentPath, bestPath, visitedNodes, depth + 1)

				currentPath.removeAt(currentPath.size - 1)
				visitedNodes.remove(nextNode)
			}
		}

		return bestPath
	}

}