package utils

class TreeNode<T>(
	val value: T,
	val children: MutableList<TreeNode<T>> = mutableListOf(),
	var parent: TreeNode<T>? = null
) {

	fun addChild(childValue: T): TreeNode<T> {
		val childNode = TreeNode(childValue)
		childNode.parent = this  // Set parent reference
		children.add(childNode)
		return childNode
	}

	// Find a node in the tree using Depth-First Search (DFS)
	fun find(value: T): TreeNode<T>? {
		if (this.value == value) return this
		for (child in children) {
			val result = child.find(value)
			if (result != null) return result
		}
		return null
	}

	fun getRoot(): TreeNode<T> {
		var current = this
		while (current.parent != null) {
			current = current.parent!!
		}
		return current
	}

	// Get the height of the tree (distance to the deepest leaf)
	fun height(): Int {
		if (children.isEmpty()) return 1
		return 1 + children.maxOf { it.height() }
	}

	// Get the depth of this node (distance to the root)
	fun depth(): Int {
		return if (parent == null) 0 else 1 + parent!!.depth()
	}

	// Print the tree structure in a hierarchical format
	fun printTree(indent: String = "") {
		println("$indent$value")
		for (child in children) {
			child.printTree("$indent    ")
		}
	}

	// Get nodes at a specific depth/level from this node
	fun getNodesAtDepth(depth: Int): List<TreeNode<T>> {
		if (depth < 0) return emptyList()
		if (depth == 0) return listOf(this)

		return children.flatMap { it.getNodesAtDepth(depth - 1) }
	}

	// Get values of nodes at a specific depth/level from this node
	fun getValuesAtDepth(depth: Int): List<T> {
		return getNodesAtDepth(depth).map { it.value }
	}

	// Get all nodes grouped by their depth
	fun getAllNodesByDepth(): Map<Int, List<TreeNode<T>>> {
		val result = mutableMapOf<Int, MutableList<TreeNode<T>>>()

		// Helper function to traverse the tree
		fun traverse(node: TreeNode<T>, depth: Int) {
			result.getOrPut(depth) { mutableListOf() }.add(node)
			node.children.forEach { child ->
				traverse(child, depth + 1)
			}
		}

		traverse(this, 0)
		return result
	}

	// Get all values grouped by their depth
	fun getAllValuesByDepth(): Map<Int, List<T>> {
		return getAllNodesByDepth().mapValues { (_, nodes) ->
			nodes.map { it.value }
		}
	}

	// Get maximum depth of the tree from this node
	fun getMaxDepth(): Int {
		if (children.isEmpty()) return 0
		return 1 + children.maxOf { it.getMaxDepth() }
	}

	// Find the path to a specific node
	fun pathTo(value: T): List<T>? {
		val node = find(value) ?: return null
		val path = mutableListOf<T>()
		var current: TreeNode<T>? = node
		while (current != null) {
			path.add(current.value)
			current = current.parent
		}
		return path.reversed() // Reverse to get the path from root to the target node
	}

	// Safe toString that doesn't cause stack overflow
	override fun toString(): String {
		return "TreeNode(value=$value, childCount=${children.size})"
	}

	// Safe equals that only compares values and immediate children
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is TreeNode<*>) return false

		if (value != other.value) return false
		if (children.size != other.children.size) return false

		// Compare only immediate children values
		val childrenValues = children.map { it.value }
		val otherChildrenValues = other.children.map { it.value }
		return childrenValues == otherChildrenValues
	}

	// Safe hashCode that only uses value and immediate children
	override fun hashCode(): Int {
		var result = value?.hashCode() ?: 0
		result = 31 * result + children.size
		// Only include immediate children values in hash
		result = 31 * result + children.map { it.value }.hashCode()
		return result
	}

	fun traverseAndApply(action: (TreeNode<T>) -> Unit) {
		action(this)
		children.forEach { it.traverseAndApply(action) }
	}

	companion object {
		// Build tree from prepared nodes and relationships
		fun <T> buildTree(nodes: Map<T, TreeNode<T>>, relationships: Map<T, List<T>>): TreeNode<T> {
			// Track which nodes are children
			val childNodes = mutableSetOf<T>()

			// Build relationships
			relationships.forEach { (parentValue, childrenValues) ->
				val parentNode = nodes[parentValue]
					?: throw IllegalArgumentException("Parent node not found: $parentValue")

				childrenValues.forEach { childValue ->
					val childNode = nodes[childValue]
						?: throw IllegalArgumentException("Child node not found: $childValue")
					childNode.parent = parentNode
					parentNode.children.add(childNode)
					childNodes.add(childValue)
				}
			}

			// Find root (the only node that's not a child)
			val rootValue = nodes.keys.firstOrNull { it !in childNodes }
				?: throw IllegalStateException("No root node found")

			return nodes[rootValue]!!
		}
	}

//	fun main() {
//		val relationships = listOf(
//			"A" to listOf("B", "C"),
//			"B" to listOf("D", "E"),
//			"E" to listOf("F")
//		)
//
//		// Build the tree
//		val root = TreeNode.buildTree(relationships)
//
//		// Print the tree structure
//		root?.printTree()
//
//		/*
//		Output:
//		A
//		  B
//			D
//			E
//			  F
//		  C
//		*/
//	}
}
