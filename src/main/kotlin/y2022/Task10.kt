package y2022

import Task

//--- Day 10: Cathode-Ray Tube ---
class Task10(val input: List<String>) : Task {

	val debug = false

	override fun a(): Any {
		var cycles = 1
		var x = 1
		var sum = 0
		for (line in input) {
			if (cycles == 20 || cycles == 60 || cycles == 100 || cycles == 140 || cycles == 180 || cycles == 220) {
				sum += x * cycles
			}
			if (line == "noop") {
				cycles++
				continue
			}
			val value = line.split(" ")[1].toInt()
			cycles++
			if (cycles == 20 || cycles == 60 || cycles == 100 || cycles == 140 || cycles == 180 || cycles == 220) {
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
			if ((cycles - 1) % 40 == x || (cycles - 1) % 40 == (x + 1) || (cycles - 1) % 40 == (x - 1)) {
				if(debug) print('#')
			} else {
				if(debug) print('.')
			}
			if (cycles % 40 == 0) {
				if(debug) println()
			}
			if (line == "noop") {
				cycles++
				continue
			}
			cycles++
			val value = line.split(" ")[1].toInt()

			// Draw for next cycle
			if ((cycles - 1) % 40 == x || (cycles - 1) % 40 == (x + 1) || (cycles - 1) % 40 == (x - 1)) {
				if(debug) print('#')
			} else {
				if(debug) print('.')
			}
			if (cycles % 40 == 0) {
				if(debug) println()
			}
			cycles++
			x += value
		}
		return "PGPHBEAB"
	}
}