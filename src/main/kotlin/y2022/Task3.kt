package y2022

import Task

//--- Day 3: Rucksack Reorganization ---
class Task3(val input: List<String>) : Task {
	override fun a(): Any {
		return input.sumOf { line ->
			val (s1, s2) = line.chunked(line.length / 2)
			getPriority(s1.toSet().intersect(s2.toSet()))
		}
	}

	fun getPriority(chars: Set<Char>): Int {
		val c = chars.firstOrNull() ?: return -1
		val code = c.code
		return if (code >= 97) {
			code % 96
		} else {
			code % 64 + 26
		}
	}

	override fun b(): Any {
		return input.chunked(3).sumOf { group ->
			val common = group.map { it.toSet() }.reduce { acc, set -> acc.intersect(set) }
			getPriority(common)
		}
	}
}