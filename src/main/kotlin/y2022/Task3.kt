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
//		println('a'.code % 96)
//		println('p'.code % 96)
//		println('z'.code)
//		println('A'.code % 64 + 26)
//		println('Z'.code)
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
		return 0
	}
}