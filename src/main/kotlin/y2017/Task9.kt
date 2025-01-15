package y2017

import Task
//--- Day 9: Stream Processing ---
class Task9(val input: String) : Task {

	override fun a(): Any {
		val replaced = input.replace(Regex("!."), "")
		val builder = StringBuilder()
		var i = 0
		while (i < replaced.length) {
			val c = replaced[i]
			if(c == '<') {
				while(replaced[i] != '>') {
					i++
				}
			} else {
				builder.append(c)
			}
			i++
		}

		val cleaned = builder.toString()
		var score = 0
		var depth = 0
		for (i in cleaned.indices) {
			val c = cleaned[i]
			if(c == '{') {
				depth++
			} else if(c == '}') {
				score += depth
				depth--
			}
		}
		return score
	}

	override fun b(): Any {
		val cleaned = input.replace(Regex("!."), "")
		var i = 0
		var count = 0
		while (i < cleaned.length) {
			if (cleaned[i] == '<') {
				while (cleaned[i] != '>') {
					i++
					count++
				}
				count--
			}
			i++
		}
		return count
	}
}