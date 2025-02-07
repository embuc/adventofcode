package y2022

import Task

//--- Day 10: Cathode-Ray Tube ---
class Task10(val input: List<String>) : Task {

	override fun a(): Any {
		var cycles = 1
		var x = 1
		var sum = 0
		for (line in input) {
			if (cycles == 20 || cycles == 60 || cycles == 100 || cycles == 140 || cycles == 180 || cycles == 220) {
				println("begin: x: $x cycles: $cycles ")
				sum += x * cycles
			}
			if (line == "noop") {
				cycles++
				continue
			}
			val value = line.split(" ")[1].toInt()
			cycles++
			if (cycles == 20 || cycles == 60 || cycles == 100 || cycles == 140 || cycles == 180 || cycles == 220) {
				println("mellan: x: $x cycles: $cycles ")
				sum += x * cycles
			}
			x += value
			cycles++
		}
		return sum
	}

	override fun b(): Any {
		var cycles = 1
		var x = 1
		for (line in input) {
			// Draw for current cycle
			if ((cycles - 1)%40 == x || (cycles - 1)%40 == (x + 1) || (cycles - 1)%40 == (x - 1)) {
				print('#')
			} else {
				print('.')
			}
			if (cycles % 40 == 0) {
				println()
			}
			if (line == "noop") {
				cycles++
				continue
			}
			cycles++
			val value = line.split(" ")[1].toInt()

			// Draw for next cycle
			if ((cycles - 1)%40 == x || (cycles - 1)%40 == (x + 1) || (cycles - 1)%40 == (x - 1)) {
				print('#')
			} else {
				print('.')
			}
			if (cycles % 40 == 0) {
				println()
			}
			cycles++
			x += value
		}
		return "PGPHBEAB"
	}
}