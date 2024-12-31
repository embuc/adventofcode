package y2015

import Task
import java.util.*

//--- Day 19: Medicine for Rudolph ---
class Task19(val input: List<String>) : Task {

	override fun a(): Int {
		val transformations = mutableMapOf<String, List<String>>()
		for (t in input.dropLast(2)) {
			val (from, to) = t.split(" => ")
			transformations[from] = transformations.getOrDefault(from, mutableListOf()).plus(to)
		}
		val molecule = input.last()
		val molecules = mutableSetOf<String>()
		for (t in transformations) {
			val (from, to) = t
			for (r in to) {
				var index = 0
				index = molecule.indexOf(from, index)
				while (index != -1) {
					molecules.add(molecule.substring(0, index) + r + molecule.substring(index + from.length))
					index = molecule.indexOf(from, index + 1)
				}
			}
		}
		return molecules.size
	}

	override fun b(): Int {
		val molecule = input.last()

		// Count key elements
		val rnCount = molecule.windowed(2).count { it == "Rn" }
		val arCount = molecule.windowed(2).count { it == "Ar" }
		val yCount = molecule.count { it == 'Y' }

		// Count total atoms (each capital letter starts an atom)
		var totalAtoms = 0
		for (i in molecule.indices) {
			if (molecule[i].isUpperCase()) {
				totalAtoms++
			}
		}

		// Formula based on patterns in the transformations:
		// - Each Rn/Ar pair represents one replacement
		// - Each Y represents an additional replacement
		// - The total number of steps is related to the total atoms minus special elements
		return totalAtoms - rnCount - arCount - (2 * yCount) - 1
	}

	// didnt work, too slow (worked nice with test data)
	fun reverseBFS(startMolecule: String, target: String, transformations: Map<String, List<String>>): Int {
		val queue: Queue<Pair<String, Int>> = LinkedList()
		val visited = mutableSetOf<String>()

		// Start from the complex molecule
		queue.add(Pair(startMolecule, 0))
		visited.add(startMolecule)

		while (queue.isNotEmpty()) {
			val (currentMolecule, steps) = queue.poll()

			if (currentMolecule == target) {
				return steps
			}

			// Try all possible replacements in the current molecule
			for ((to, fromList) in transformations) {
				var index = currentMolecule.indexOf(to)
				while (index != -1) {
					for (from in fromList) {
						// Replace 'to' with 'from' (going backwards)
						val result = currentMolecule.substring(0, index) + from +
								currentMolecule.substring(index + to.length)

						if (result !in visited) {
							queue.add(Pair(result, steps + 1))
							visited.add(result)
						}
					}
					index = currentMolecule.indexOf(to, index + 1)
				}
			}
		}
		return -1
	}

	// didnt work, too slow (worked nice with test data)
	fun BFS(startNode: String, molecule: String, transformations: MutableMap<String, List<String>>): Int {
		// Queue for BFS
		val queue: Queue<Pair<String, Int>> = LinkedList()
		val visited = mutableSetOf<String>()

		// init bfs with start node
		queue.add(Pair(startNode, 0))
		visited.add(startNode)

		// BFS Loop
		while (queue.isNotEmpty()) {
			val (currentMolecule, steps) = queue.poll()

			// If the current molecule matches the target, return the number of steps
			if (currentMolecule == molecule) {
				return steps
			}

			// Find and apply applicable transformations
			for ((from, toList) in transformations) {
				var index = currentMolecule.indexOf(from)
				while (index != -1) {
					for (to in toList) {
						// Create the new molecule by applying the transformation
						val result = currentMolecule.substring(0, index) + to + currentMolecule.substring(index + from.length)

						// If the new molecule hasn't been visited, process it
						if (result !in visited) {
							queue.add(Pair(result, steps + 1))
							visited.add(result)
						}
					}
					// Find the next occurrence of `from`
					index = currentMolecule.indexOf(from, index + 1)
				}
			}
		}
		return -1
	}

}