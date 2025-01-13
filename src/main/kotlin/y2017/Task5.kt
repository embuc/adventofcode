package y2017

import Task

//--- Day 5: A Maze of Twisty Trampolines, All Alike ---
class Task5(private val nums: IntArray) : Task {

	override fun a(): Any {
		var idx = 0
		var steps = 0
		while (idx in nums.indices) {
			val value = nums[idx]
			nums[idx]++
			idx += value
			steps++
		}
		return steps
	}

	override fun b(): Any {
		var idx = 0
		var steps = 0
		while (idx in nums.indices) {
			val value = nums[idx]
			nums[idx] += if (value >= 3) -1 else 1
			idx += value
			steps++
		}
		return steps
	}
}