package y2022

import Task

//--- Day 8: Treetop Tree House ---
class Task8(val input: List<String>) : Task {
	private data class Tree(val height: Int, var visible: Boolean = false)

	override fun a(): Any {
		val forest = MutableList<MutableList<Tree>>(input.size) { mutableListOf() }
		for ((i, line) in input.withIndex()) {
			println(line)
			for (d in line) {
				forest[i].add(Tree(d - '0'))
			}
		}
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
		return forest.sumOf{ it.count { it.visible } }
	}

	override fun b(): Any {
		return 0
	}
}