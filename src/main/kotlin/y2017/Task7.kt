package y2017

import Task
import utils.TreeNode

//--- Day 7: Recursive Circus ---
class Task7(val input: List<String>) : Task {

	data class Node(val name:String, val weight : Int)

	override fun a(): Any {
		return parseInput(input).value.name
	}

	fun parseInput(input: List<String>): TreeNode<Node> {
		val nodeMap = mutableMapOf<String, TreeNode<Node>>()
		val weightMap = mutableMapOf<String, Int>()
		val relationships = mutableMapOf<String, List<String>>()

		for (line in input) {
			val (nodeDef, childrenDef) = line.split("->").let { parts ->
				if (parts.size > 1) parts[0] to parts[1]
				else parts[0] to null
			}

			val (name, weight) = parseNameAndWeight(nodeDef.trim())
			weightMap[name] = weight

			childrenDef?.let { children ->
				relationships[name] = children.split(",").map { it.trim() }
			}
		}

		weightMap.forEach { (name, weight) ->
			nodeMap[name] = TreeNode(Node(name, weight))
		}

		val nodeRelationships = relationships.mapValues { (_, children) ->
			children.map { childName ->
				nodeMap[childName]!!.value
			}
		}.mapKeys { (parentName, _) ->
			nodeMap[parentName]!!.value
		}

		return TreeNode.buildTree(
			nodes = nodeMap.mapKeys { it.value.value },
			relationships = nodeRelationships
		)
	}

	private fun parseNameAndWeight(input: String): Pair<String, Int> {
		val regex = """(\w+)\s*\((\d+)\)""".toRegex()
		val matchResult = regex.find(input)!!
		val (name, weightStr) = matchResult.destructured
		return name to weightStr.toInt()
	}

	override fun b(): Any {
		val root = parseInput(input)
		return findWeightAdjustment(root.children) ?: -1
	}

	private fun findWeightAdjustment(nodes: List<TreeNode<Node>>): Int? {
		val weightMap = getSumOfChildren(nodes)
		val (imbalancedNode, expectedWeight) = findImbalancedNode(weightMap) ?: return null

		// If the imbalanced node has children, recursively check them
		if (imbalancedNode.children.isNotEmpty()) {
			val childAdjustment = findWeightAdjustment(imbalancedNode.children)
			if (childAdjustment != null) {
				return childAdjustment
			}
		}

		// Calculate the weight adjustment needed
		val currentWeight = weightMap[imbalancedNode] ?: return null
		val adjustment = expectedWeight - currentWeight
		return imbalancedNode.value.weight + adjustment
	}

	private fun findImbalancedNode(weightMap: Map<TreeNode<Node>, Int>): Pair<TreeNode<Node>, Int>? {
		val weightGroups = weightMap.entries.groupBy({ it.value }, { it.key })

		// Find the group with only one node (the imbalanced one)
		val imbalancedGroup = weightGroups.entries.find { it.value.size == 1 } ?: return null
		// Find the group with multiple nodes (the expected weight)
		val balancedGroup = weightGroups.entries.find { it.value.size > 1 } ?: return null

		return Pair(imbalancedGroup.value.first(), balancedGroup.key)
	}

	private fun getSumOfChildren(nodes: List<TreeNode<Node>>): Map<TreeNode<Node>, Int> {
		return nodes.associateWith { node -> calculateTotalWeight(node) }
	}

	private fun calculateTotalWeight(node: TreeNode<Node>): Int {
		return node.value.weight + node.children.sumOf { calculateTotalWeight(it) }
	}
}