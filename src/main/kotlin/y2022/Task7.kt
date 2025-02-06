package y2022

import Task
import utils.TreeNode

//--- Day 7: No Space Left On Device ---
class Task7(val input: List<String>) : Task {

	data class File(val name: String, val children: MutableList<File>, var size: Int)

	override fun a(): Any {
		var i = 0
		var node = TreeNode(File("/", mutableListOf(), 0), mutableListOf(), null)
		while (i in input.indices) {
			println(input[i])
			val line = input[i]
			val parts = line.split(" ")
			if (parts[1] == "cd") {
				if (parts[2] == "..") {
					node = node.parent!!
				} else if (parts[2] == "/") {
					node = node.getRoot()
				} else {
					val dir = node.children.find { it.value.name == parts[2] }!!
					node = dir
				}
			} else if (parts[0] == "dir") {
				val dir = File(parts[1], mutableListOf(), 0)
				node.addChild(dir)
			} else if(parts[0].toIntOrNull() != null) {
				val file = File(parts[1], mutableListOf(), parts[0].toInt())
				node.value.children.add(file)
			}
//			else if(parts[1] == "ls"){
				// do nothing, just ignore
//			}
			i++
		}
		node.getRoot().printTree()
		val root = node.getRoot()
		calculateDirectorySizes(root)  // calculate all sizes (whole tree)

		val smallDirs = findDirectoriesUnderThreshold(root, 100_000)

//		smallDirs.forEach { dir ->
//			println("Directory ${dir.value.name} has size ${dir.value.size}")
//		}

		return smallDirs.sumOf { it.value.size }
	}

	fun calculateDirectorySizes(node: TreeNode<File>): Int {
		val immediateFilesSize = node.value.children.sumOf { it.size }
		val subdirectoriesSize = node.children.sumOf { calculateDirectorySizes(it) }
		val totalSize = immediateFilesSize + subdirectoriesSize
		// Update the current node's size
		node.value.size = totalSize
		return totalSize
	}

	fun findDirectoriesUnderThreshold(root: TreeNode<File>, threshold: Int): List<TreeNode<File>> {
		val matches = mutableListOf<TreeNode<File>>()

		root.traverseAndApply { node ->
			// Only consider directories (nodes that have children or empty directories)
			// and check their precalculated size
			if (node.value.size <= threshold) {
				matches.add(node)
			}
		}

		return matches
	}

	fun findDirectoriesOverThreshold(root: TreeNode<File>, threshold: Int): List<TreeNode<File>> {
		val matches = mutableListOf<TreeNode<File>>()

		root.traverseAndApply { node ->
			// Only consider directories (nodes that have children or empty directories)
			// and check their precalculated size
			if (node.value.size >= threshold) {
				matches.add(node)
			}
		}

		return matches
	}

	override fun b(): Any {
		val totalSize = 70000000
		val unusedMinSize = 30000000
		var i = 0
		var node = TreeNode(File("/", mutableListOf(), 0), mutableListOf(), null)
		while (i in input.indices) {
			println(input[i])
			val line = input[i]
			val parts = line.split(" ")
			if (parts[1] == "cd") {
				if (parts[2] == "..") {
					node = node.parent!!
				} else if (parts[2] == "/") {
					node = node.getRoot()
				} else {
					val dir = node.children.find { it.value.name == parts[2] }!!
					node = dir
				}
			} else if (parts[0] == "dir") {
				val dir = File(parts[1], mutableListOf(), 0)
				node.addChild(dir)
			} else if(parts[0].toIntOrNull() != null) {
				val file = File(parts[1], mutableListOf(), parts[0].toInt())
				node.value.children.add(file)
			}
			// if "ls" do nothing, just ignore e.g. skip to next line
			i++
		}
		node.getRoot().printTree()
		val root = node.getRoot()
		calculateDirectorySizes(root)  // calculate all sizes (whole tree)

		val threshold = node.getRoot().value.size - (totalSize - unusedMinSize)
		val smallDirs = findDirectoriesOverThreshold(root, threshold)
		node.getRoot().printTree()
		println("Total size: ${node.getRoot().value.size}")
		println("Unused size: $unusedMinSize")
		println("Threshold: $threshold")

		return smallDirs.minOf { it.value.size }
	}
}