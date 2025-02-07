package y2022

import Task

//--- Day 10: Cathode-Ray Tube ---
class Task10(val input: List<String>) : Task {

	override fun a(): Any {
		var cycles = 1
		var x = 1
		var sum = 0
		for (line in input) {
//			println(line)
			if(cycles == 20 || cycles == 60 || cycles == 100 || cycles == 140 || cycles == 180 || cycles == 220 ) {
				println("begin: x: $x cycles: $cycles ")
				sum += x * cycles
			}
			if (line == "noop") {
				cycles++
				continue
			}
			val value = line.split(" ")[1].toInt()
			cycles++
			if(cycles == 20 || cycles == 60 || cycles == 100 || cycles == 140 || cycles == 180 || cycles == 220 ) {
				println("mellan: x: $x cycles: $cycles ")
				sum += x * cycles
			}
			x += value
			cycles++


		}
		return sum
	}

	override fun b(): Any {
		return 0
	}
}