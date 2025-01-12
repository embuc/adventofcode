package y2017

import Task

//--- Day 2: Corruption Checksum ---
class Task2(val input: List<String>) : Task {

	override fun a(): Any {
		var sum = 0
		for (line in input) {
			val parts = line.split("\t").filter { it.isNotBlank() }.map { it.trim().toInt() }
			sum += parts.max() - parts.min()
		}
		return sum
	}

	override fun b(): Any {
		var sum = 0
		for (line in input) {
			val parts = line.split("\t").filter { it.isNotBlank() }.map { it.trim().toInt() }
			var found = false
			for (i in parts.indices) {
				for (j in (i + 1)..<parts.size) {
					if (parts[i] > parts[j] && parts[i] % parts[j] == 0) {
						sum += parts[i] / parts[j]
						found = true
						break
					} else if (parts[j] % parts[i] == 0) {
						sum += parts[j] / parts[i]
						found = true
						break
					}
				}
				if (found) break
			}
		}
		return sum
	}
}
