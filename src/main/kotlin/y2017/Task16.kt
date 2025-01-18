package y2017

import Task

//--- Day 16: Permutation Promenade ---
class Task16(val input: String) : Task {
	
	override fun a(): Any {
		var programs = ('a'..'p').toList()
		val moves = input.split(",")
		for (move in moves) {
			programs = processMove(programs, move)
		}
		return programs.joinToString("")
	}

	private fun processMove(programs: List<Char>, move: String): List<Char> {
		val type = move[0]
		when (type) {
			's' -> {
				val spin = move.substring(1).toInt()
				val end = programs.subList(programs.size - spin, programs.size)
				val start = programs.subList(0, programs.size - spin)
				return end + start
			}

			'x' -> {
				val programsClone = programs.toMutableList()
				val parts = move.substring(1).split("/")
				val a = parts[0].toInt()
				val b = parts[1].toInt()
				val temp = programs[a]
				programsClone[a] = programs[b]
				programsClone[b] = temp
				return programsClone
			}

			'p' -> {
				val programsClone = programs.toMutableList()
				val parts = move.substring(1).split("/")
				val a = programs.indexOf(parts[0][0])
				val b = programs.indexOf(parts[1][0])
				val temp = programs[a]
				programsClone[a] = programs[b]
				programsClone[b] = temp
				return programsClone

			}
		}
		println(move)
		return programs
	}

	override fun b(): Any {
		var programs = ('a'..'p').toList()
		val moves = input.split(",")
		val seen = mutableListOf<String>()
		var i = 0
		var found = false
		var foundIndex1 = 0
		repeat(1000000000) {
			val current = programs.joinToString("")
			if (seen.contains(current)) {
				if (found && current == seen[foundIndex1]) {
					val cycleLength = i - foundIndex1
					val remainingIterations = (1000000000 - foundIndex1) % cycleLength
					return seen[remainingIterations]
				} else if (!found) {
					foundIndex1 = i
				}
				found = true
			}
			seen.add(current)

			for (move in moves) {
				programs = processMove(programs, move)
			}
			i++
		}
		return programs.joinToString("")
	}
}