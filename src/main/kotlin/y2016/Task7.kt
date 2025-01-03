package y2016

import Task

//--- Day 7: Internet Protocol Version 7 ---
class Task7(val input: List<String>) : Task {

	override fun a(): Any {
		return input.count { line->
			val (inside, outside) = partitionInsideOutside(line)
			!hasAbba(inside) && hasAbba(outside)
		}
	}

	private fun hasAbba(list: List<String>): Boolean {
		for (i in list) {
			val chars = i.toCharArray()
			for (j in 0 until chars.size - 3) {
				if (chars[j] == chars[j + 3] && chars[j + 1] == chars[j + 2] && chars[j] != chars[j + 1]) {
					return true
				}
			}
		}
		return false
	}

	fun partitionInsideOutside(line:String) :Pair<List<String>, List<String>> {
		val parts = line.split("[", "]")
		val inside = parts.filterIndexed { index, _ -> index % 2 == 1 }
		val outside = parts.filterIndexed { index, _ -> index % 2 == 0 }
		return Pair(inside, outside)
	}

	override fun b(): Any {
		var sum = 0
		for (line in input) {
			val (inside, outside) = partitionInsideOutside(line)
			val abas = mutableListOf<String>()
			for (i in inside) {
				val chars = i.toCharArray()
				for (j in 0 until chars.size - 2) {
					if (chars[j] == chars[j + 2] && chars[j] != chars[j + 1]) {
						abas.add(chars[j].toString() + chars[j + 1] + chars[j + 2])
					}
				}
			}
			val babs = mutableListOf<String>()
			for (i in outside) {
				val chars = i.toCharArray()
				for (j in 0 until chars.size - 2) {
					if (chars[j] == chars[j + 2] && chars[j] != chars[j + 1]) {
						babs.add(chars[j].toString() + chars[j + 1] + chars[j + 2])
					}
				}
			}
			if(abas.any { aba -> babs.any { bab -> aba[0] == bab[1] && aba[1] == bab[0] } }) {
				sum++
			}
		}
		return sum
	}

}