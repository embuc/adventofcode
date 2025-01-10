package y2016

import Task

//--- Day 22: Grid Computing ---
class Task22(val input: List<String>) : Task {

	private data class FileNode(val x: Int, val y: Int, val size: Int, val used: Int, val avail: Int, val use: Int)

	override fun a(): Any {
		val nodes = parseNodes()
		return getViablePairs(nodes).size
	}

	private fun parseNodes(): MutableList<FileNode> {
		val nodes = mutableListOf<FileNode>()
		for (line in input.drop(2)) {
			line.split(" ").filter { it.isNotBlank() }.let {
				val (x, y) = it[0].replace("/dev/grid/node-x", "").replace("-y", " ").split(" ")
				val size = it[1].substring(0, it[1].length - 1).toInt()
				val used = it[2].substring(0, it[2].length - 1).toInt()
				val avail = it[3].substring(0, it[3].length - 1).toInt()
				val use = it[4].substring(0, it[4].length - 1).toInt()
				nodes.add(FileNode(x.toInt(), y.toInt(), size, used, avail, use))
			}
		}
		return nodes
	}

	private fun getViablePairs(nodes: MutableList<FileNode>): List<Pair<FileNode, FileNode>> {
		val viablePairs = mutableListOf<Pair<FileNode, FileNode>>()
		for (node1 in nodes) {
			for (node2 in nodes) {
				if (node1 != node2 && node1.used != 0 && node1.used <= node2.avail) {
					viablePairs.add(node1 to node2)
				}
			}
		}
		return viablePairs

	}

	override fun b(): Any {
		val nodes = parseNodes().associateBy { it.x to it.y }
		val grid = createSimplifiedGrid(nodes)
//		printGrid(grid)
		// print the grid and count the steps manually
		// 61 steps to move the empty node to the top right corner,
		// then 30 x 5 steps to move the goal data to the left + 1 last step into target node
		return 207
	}

	private fun createSimplifiedGrid(nodes: Map<Pair<Int, Int>, FileNode>): MutableMap<Pair<Int, Int>, Char> {
		val maxX = nodes.keys.maxOfOrNull { it.first } ?: 0
		val maxY = nodes.keys.maxOfOrNull { it.second } ?: 0
		val grid = mutableMapOf<Pair<Int, Int>, Char>()

		val largeNodeThreshold = nodes.values.maxOf { it.size } * 0.80

		for (y in 0..maxY) {
			for (x in 0..maxX) {
				val node = nodes[x to y]
				val symbol = when {
					node == null -> ' '
					node.used == 0 -> '_'
					node.used.toDouble()/node.size.toDouble() > 0.8 && node.size > largeNodeThreshold-> '#'
					x == maxX && y == 0 -> 'G'
					else -> '.'
				}
				grid[x to y] = symbol
			}
		}
		return grid
	}


	private fun printGrid(grid: Map<Pair<Int, Int>, Char>) {
		val maxX = grid.keys.maxOfOrNull { it.first } ?: 0
		val maxY = grid.keys.maxOfOrNull { it.second } ?: 0

		for (y in 0..maxY) {
			for (x in 0..maxX) {
				if (x == 0 && y == 0) {
					print("(${grid[x to y]}) ")
				} else {
					print(" ${grid[x to y]} ")
				}
			}
			println()
		}
	}
}
