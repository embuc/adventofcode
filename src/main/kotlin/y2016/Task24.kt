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
		val edgesMap = mutableMapOf<Pair<Node, Node>, Int>()

		// Helper function to find the shortest path and add it to edgesMap
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

		//debug prints, TODO remove
//		println("0->1= ${edgesMap[Pair(nodes[0], nodes[1])]} 0->2= ${edgesMap[Pair(nodes[0], nodes[2])]} 0->3= ${edgesMap[Pair(nodes[0], nodes[3])]} 0->4= ${edgesMap[Pair(nodes[0], nodes[4])]} 0->5= ${edgesMap[Pair(nodes[0], nodes[5])]} 0->6= ${edgesMap[Pair(nodes[0], nodes[6])]} 0->7= ${edgesMap[Pair(nodes[0], nodes[7])]}")
//		println("1->0= ${edgesMap[Pair(nodes[1], nodes[0])]} 1->2= ${edgesMap[Pair(nodes[1], nodes[2])]} 1->3= ${edgesMap[Pair(nodes[1], nodes[3])]} 1->4= ${edgesMap[Pair(nodes[1], nodes[4])]} 1->5= ${edgesMap[Pair(nodes[1], nodes[5])]} 1->6= ${edgesMap[Pair(nodes[1], nodes[6])]} 1->7= ${edgesMap[Pair(nodes[1], nodes[7])]}")
//		println("2->0= ${edgesMap[Pair(nodes[2], nodes[0])]} 2->1= ${edgesMap[Pair(nodes[2], nodes[1])]} 2->3= ${edgesMap[Pair(nodes[2], nodes[3])]} 2->4= ${edgesMap[Pair(nodes[2], nodes[4])]} 2->5= ${edgesMap[Pair(nodes[2], nodes[5])]} 2->6= ${edgesMap[Pair(nodes[2], nodes[6])]} 2->7= ${edgesMap[Pair(nodes[2], nodes[7])]}")
//		println("3->0= ${edgesMap[Pair(nodes[3], nodes[0])]} 3->1= ${edgesMap[Pair(nodes[3], nodes[1])]} 3->2= ${edgesMap[Pair(nodes[3], nodes[2])]} 3->4= ${edgesMap[Pair(nodes[3], nodes[4])]} 3->5= ${edgesMap[Pair(nodes[3], nodes[5])]} 3->6= ${edgesMap[Pair(nodes[3], nodes[6])]} 3->7= ${edgesMap[Pair(nodes[3], nodes[7])]}")
//		println("4->0= ${edgesMap[Pair(nodes[4], nodes[0])]} 4->1= ${edgesMap[Pair(nodes[4], nodes[1])]} 4->2= ${edgesMap[Pair(nodes[4], nodes[2])]} 4->3= ${edgesMap[Pair(nodes[4], nodes[3])]} 4->5= ${edgesMap[Pair(nodes[4], nodes[5])]} 4->6= ${edgesMap[Pair(nodes[4], nodes[6])]} 4->7= ${edgesMap[Pair(nodes[4], nodes[7])]}")
//		println("5->0= ${edgesMap[Pair(nodes[5], nodes[0])]} 5->1= ${edgesMap[Pair(nodes[5], nodes[1])]} 5->2= ${edgesMap[Pair(nodes[5], nodes[2])]} 5->3= ${edgesMap[Pair(nodes[5], nodes[3])]} 5->4= ${edgesMap[Pair(nodes[5], nodes[4])]} 5->6= ${edgesMap[Pair(nodes[5], nodes[6])]} 5->7= ${edgesMap[Pair(nodes[5], nodes[7])]}")
//		println("6->0= ${edgesMap[Pair(nodes[6], nodes[0])]} 6->1= ${edgesMap[Pair(nodes[6], nodes[1])]} 6->2= ${edgesMap[Pair(nodes[6], nodes[2])]} 6->3= ${edgesMap[Pair(nodes[6], nodes[3])]} 6->4= ${edgesMap[Pair(nodes[6], nodes[4])]} 6->5= ${edgesMap[Pair(nodes[6], nodes[5])]} 6->7= ${edgesMap[Pair(nodes[6], nodes[7])]}")
//		println("7->0= ${edgesMap[Pair(nodes[7], nodes[0])]} 7->1= ${edgesMap[Pair(nodes[7], nodes[1])]} 7->2= ${edgesMap[Pair(nodes[7], nodes[2])]} 7->3= ${edgesMap[Pair(nodes[7], nodes[3])]} 7->4= ${edgesMap[Pair(nodes[7], nodes[4])]} 7->5= ${edgesMap[Pair(nodes[7], nodes[5])]} 7->6= ${edgesMap[Pair(nodes[7], nodes[6])]}")

		// TSP with backtracking
		return backtrack(edgesMap, nodes).sumOf { it.weight }
	}

	private fun getNodes(grid: Array<CharArray>): List<Node> {
		return (0..7).map { i ->
			val c = MinimalBFS.findChar(grid, i.toString().first())
			Node(c[0], c[1], i.toString())
		}
	}

	private data class Edge(val from: Node, val to: Node, val weight: Int)

	private fun backtrack(
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

				backtrack(dict, nodes, currentPath, bestPath, visitedNodes)

				currentPath.removeAt(currentPath.size - 1)
				visitedNodes.remove(nextNode)
			}
		}

		return bestPath
	}

	override fun b(): Any {
		return 0
	}

}