package y2022

import Task
import utils.TreeNode

//--- Day 7: No Space Left On Device ---
class Task7(val input: List<String>) : Task {
	data class File(val name: String, val children: MutableList<File>, var size: Int)

	private fun buildFileSystem(): TreeNode<File> {
		var currentNode = TreeNode(File("/", mutableListOf(), 0), mutableListOf(), null)

		input.forEach { line ->
			val parts = line.split(" ")
			when {
				parts[1] == "cd" -> currentNode = handleCdCommand(currentNode, parts[2])
				parts[0] == "dir" -> currentNode.addChild(File(parts[1], mutableListOf(), 0))
				parts[0].toIntOrNull() != null -> currentNode.value.children.add(
					File(parts[1], mutableListOf(), parts[0].toInt())
				)
			}
		}
		return currentNode.getRoot()
	}

	private fun handleCdCommand(currentNode: TreeNode<File>, destination: String): TreeNode<File> = when(destination) {
		".." -> currentNode.parent!!
		"/" -> currentNode.getRoot()
		else -> currentNode.children.find { it.value.name == destination }!!
	}

	private fun calculateDirectorySizes(node: TreeNode<File>): Int {
		val totalSize = node.value.children.sumOf { it.size } +
				node.children.sumOf { calculateDirectorySizes(it) }
		node.value.size = totalSize
		return totalSize
	}

	private fun findDirectories(root: TreeNode<File>, predicate: (Int) -> Boolean): List<TreeNode<File>> {
		val matches = mutableListOf<TreeNode<File>>()
		root.traverseAndApply { node ->
			if (predicate(node.value.size)) {
				matches.add(node)
			}
		}
		return matches
	}

	override fun a(): Any {
		val root = buildFileSystem()
		calculateDirectorySizes(root)
		return findDirectories(root) { it <= 100_000 }.sumOf { it.value.size }
	}

	override fun b(): Any {
		val root = buildFileSystem()
		calculateDirectorySizes(root)

		val unusedSpace = 70_000_000 - root.value.size
		val requiredCleanup = 30_000_000 - unusedSpace

		return findDirectories(root) { it >= requiredCleanup }
			.minOf { it.value.size }
	}
}