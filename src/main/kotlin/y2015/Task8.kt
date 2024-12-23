package y2015

import Task

//--- Day 8: Matchsticks ---
class Task8(val input: List<String>) : Task {

	val hex = Regex("""\\x[0-9a-fA-F]{2}""")
	val hexEsc = Regex("""\\\\x[0-9a-fA-F]{2}""")
	val backslashEscaped = Regex("""\\"""")
	val backslashSingle = Regex("""\\\\""")
	val quoteEnd = Regex("\"$")
	val quoteBegining = Regex("^\"")

	override fun a(): Int {
		var sum = 0;
		for (line in input) {
			sum += line.length - removeEscapes(line).length
		}
		return sum
	}

	private fun removeEscapes(line: String): String {
		var newLine = line
		newLine = newLine.replace(hex, "-")
		newLine = newLine.replace(quoteBegining, "")
		newLine = newLine.replace(quoteEnd, "")
		newLine = newLine.replace(backslashEscaped, """-""")
		newLine = newLine.replace(backslashSingle, """-""")
		return newLine
	}

	override fun b(): Int {
		var sum = 0;
		for (line in input) {
			sum += escape(line).length + 2 - line.length
		}
		return sum
	}

	fun escape(line: String): String {
		var newLine = line
		newLine = newLine.replace(quoteBegining, """--""")
		newLine = newLine.replace(quoteEnd, """--""")
		newLine = newLine.replace(backslashEscaped, """----""")
		newLine = newLine.replace(backslashSingle, """----""")
		newLine = newLine.replace(hex, "-----")
		return newLine
	}
}