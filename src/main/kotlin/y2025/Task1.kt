package y2025

import Task

class Task1(val input1: List<String>) : Task {

	override fun a(): Any {
		var start = 50
		var code = 0
		for (line in input1) {
			val dir = line[0]
			val dist = line.substring(1).toInt()
			when (dir) {
				'L' -> start -= dist
				'R' -> start += dist
			}
			start %=100
			if(start < 0) start += 100
			if(start == 0) code++
			print("line=$line ")
			println("after start=$start code=$code")
		}
		return code
	}

	override fun b(): Any {
		TODO("Not yet implemented")
	}

}