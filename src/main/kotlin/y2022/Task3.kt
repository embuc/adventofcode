package y2022

import Task

//--- Day 3: Rucksack Reorganization ---
class Task3(val input: List<String>) : Task {
	override fun a(): Any {
		var sum = 0
		for (line in input) {
//			println(line)
			val s1 = line.substring(0, line.length / 2).toList()
			val s2 = line.substring(line.length / 2 ).toList()
			val common = s1.intersect(s2)
//			println("line length: ${line.length} s1 length: ${s1.size} s2 length: ${s2.size} common length: ${common.size}")
//			println(common)
			sum += getPriority(common)
		}
		return sum
	}

	fun getPriority(chars: Set<Char>): Int {
		for (c in chars) {
			val code = c.code
			return if (code >= 97) {
				code % 96
			} else {
				code % 64 + 26
			}
		}
		return -1
	}

	override fun b(): Any {
		var sum = 0
		for (index in input.indices step 3) {
			val line1 = input[index].toSet()
			val line2 = input[index + 1].toSet()
			val line3 = input[index + 2].toSet()
//			println(line)

			val common = line1.intersect(line2).intersect(line3)
//			println("line length: ${line.length} s1 length: ${s1.size} s2 length: ${s2.size} common length: ${common.size}")
//			println(common)
			sum += getPriority(common)
		}
		return sum
	}
}