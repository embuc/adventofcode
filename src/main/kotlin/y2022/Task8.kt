package y2022

import Task

//--- Day 8: Treetop Tree House ---
class Task8(val input: List<String>) : Task {
	private data class Tree(val height: Int, var visible: Boolean = false, var scenic: Int = 0)

	override fun a(): Any {
		val forest = populateForest()
		checkVisibilityFromAllSides(forest)
		return forest.sumOf { row -> row.count { it.visible } }
	}

	private fun checkVisibilityFromAllSides(forest: MutableList<MutableList<Tree>>) {
		// Left to right
		checkVisibilityInDirection(forest) { i, j -> i to j }
		// Right to left
		checkVisibilityInDirection(forest) { i, j -> i to (forest[0].size - 1 - j) }
		// Top to bottom
		checkVisibilityInDirection(forest) { i, j -> j to i }
		// Bottom to top
		checkVisibilityInDirection(forest) { i, j -> (forest.size - 1 - j) to i }
	}

	private fun checkVisibilityInDirection(forest: MutableList<MutableList<Tree>>, getPosition: (Int, Int) -> Pair<Int, Int>) {
		for (i in forest.indices) {
			var maxHeight = -1
			for (j in forest[0].indices) {
				val (row, col) = getPosition(i, j)
				val currentTree = forest[row][col]
				if (currentTree.height > maxHeight) {
					currentTree.visible = true
					maxHeight = currentTree.height
				}
			}
		}
	}

	override fun b(): Any {
		val forest = populateForest()
		calculateAllScenicScores(forest)
		return forest.maxOf { row -> row.maxOf { it.scenic } }
	}

	private fun calculateAllScenicScores(forest: MutableList<MutableList<Tree>>) {
		for (i in forest.indices) {
			for (j in forest[i].indices) {
				forest[i][j].scenic = calculateScenicScore(forest, i, j)
			}
		}
	}

	private fun calculateScenicScore(forest: MutableList<MutableList<Tree>>, row: Int, col: Int): Int {
		val height = forest[row][col].height
		val leftCount = countVisibleTrees(height, (col - 1 downTo 0)) { i -> forest[row][i] }
		val rightCount = countVisibleTrees(height, (col + 1 until forest[0].size)) { i -> forest[row][i] }
		val upCount = countVisibleTrees(height, (row - 1 downTo 0)) { i -> forest[i][col] }
		val downCount = countVisibleTrees(height, (row + 1 until forest.size)) { i -> forest[i][col] }

		return leftCount * rightCount * upCount * downCount
	}

	private fun countVisibleTrees(height: Int, range: IntProgression, getTree: (Int) -> Tree): Int {
		var count = 0
		for (i in range) {
			count++
			if (getTree(i).height >= height) break
		}
		return count
	}

	private fun populateForest(): MutableList<MutableList<Tree>> {
		return input.map { line ->
			line.map { char -> Tree(char - '0') }.toMutableList()
		}.toMutableList()
	}
}