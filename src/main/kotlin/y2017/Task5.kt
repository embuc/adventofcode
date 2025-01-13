package y2017

import Task

//--- Day 5: A Maze of Twisty Trampolines, All Alike ---
class Task5(val input:String) : Task {

	override fun a(): Any {
		val intArray = input.split("\n").map { it.toInt() }.toIntArray()
		var index = 0
		var steps = 0
		while(index in intArray.indices) {
			val value = intArray[index]
			intArray[index] = intArray[index] + 1
			index += value
			steps ++
		}
		return steps
	}

	override fun b(): Any {
		return -1
	}
}