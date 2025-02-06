package y2022

import Task

//--- Day 8: Treetop Tree House ---
class Task8(val input: List<String>) : Task {

	private data class Tree(val height: Int, var visible: Boolean = false, var scenic: Int = 0)

	override fun a(): Any {
		val forest = populateForest()
		//traverse left to right, right to left, up to down, down to up and mark visible trees
		for (i in 0 until forest.size) {
			var max = -1
			for (j in 0 until forest[i].size) {
				if (forest[i][j].height > max) {
					forest[i][j].visible = true
					max = forest[i][j].height
				}
			}
		}
		for (i in 0 until forest.size) {
			var max = -1
			for (j in forest[i].size - 1 downTo 0) {
				if (forest[i][j].height > max) {
					forest[i][j].visible = true
					max = forest[i][j].height
				}
			}
		}
		for (j in 0 until forest[0].size) {
			var max = -1
			for (i in 0 until forest.size) {
				if (forest[i][j].height > max) {
					forest[i][j].visible = true
					max = forest[i][j].height
				}
			}
		}
		for (j in 0 until forest[0].size) {
			var max = -1
			for (i in forest.size - 1 downTo 0) {
				if (forest[i][j].height > max) {
					forest[i][j].visible = true
					max = forest[i][j].height
				}
			}
		}
		return forest.sumOf { it.count { it.visible } }
	}

	private fun populateForest(): MutableList<MutableList<Tree>> {
		val forest = MutableList<MutableList<Tree>>(input.size) { mutableListOf() }
		for ((i, line) in input.withIndex()) {
			println(line)
			for (d in line) {
				forest[i].add(Tree(d - '0'))
			}
		}
		return forest
	}

	override fun b(): Any {
		var forest = populateForest()
		// traverse each tree and calculate scenic value
		for (i in 0 until forest.size) {
			for (j in 0 until forest[i].size) {
				forest[i][j].scenic = calculateScenic(forest, i, j)
			}
		}
		return forest.maxOf { it.maxOf { it.scenic } }
	}

	private fun calculateScenic(
		lists: MutableList<MutableList<Tree>>,
		i: Int,
		j: Int
	): Int {
		// calculate scenic value for tree by going in all 4 cardinal directions until same height tree is found, then multiply 4 values
		val height = lists[i][j].height
		var left = 0
		var right = 0
		var up = 0
		var down = 0
		for (k in j - 1 downTo 0) {
			if (lists[i][k].height >= height) {
				left++//count even this one
				break
			}
			left++
		}
		for (k in j + 1 until lists[i].size) {
			if (lists[i][k].height >= height) {
				right++//count even this one
				break
			}
			right++
		}
		for (k in i - 1 downTo 0) {
			if (lists[k][j].height >= height) {
				up++//count even this one
				break
			}
			up++
		}
		for (k in i + 1 until lists.size) {
			if (lists[k][j].height >= height) {
				down++//count even this one
				break
			}
			down++
		}
		return left * right * up * down
	}
}