package y2017

import Task

//--- Day 8: I Heard You Like Registers ---
class Task8 (val input: List<String>):Task {

	override fun a(): Any {
		val dict = parseRegisters()
		return processInstructions(dict).first
	}

	override fun b(): Any {
		val dict = parseRegisters()
		return processInstructions(dict).second
	}

	private fun parseRegisters(): MutableMap<String, Int> {
		val registerNames = mutableSetOf<String>()
		for (line in input) {
			registerNames.add(line.split(" ")[0])
		}
		val dict = registerNames.associateWith { 0 }.toMutableMap()
		return dict
	}

	private fun processInstructions(dict: MutableMap<String, Int>): Pair<Int, Int> {
		var max = Int.MIN_VALUE
		for (line in input) {
			val parts = line.split(" ")
			val register = parts[0]
			val operation = parts[1]
			val value = parts[2].toInt()
			val conditionRegister = parts[4]
			val condition = parts[5]
			val conditionValue = parts[6].toInt()
			if (condition == ">" && dict[conditionRegister]!! > conditionValue) {
				if (operation == "inc") {
					dict[register] = dict[register]!! + value
				} else {
					dict[register] = dict[register]!! - value
				}
			} else if (condition == "<" && dict[conditionRegister]!! < conditionValue) {
				if (operation == "inc") {
					dict[register] = dict[register]!! + value
				} else {
					dict[register] = dict[register]!! - value
				}
			} else if (condition == "==" && dict[conditionRegister]!! == conditionValue) {
				if (operation == "inc") {
					dict[register] = dict[register]!! + value
				} else {
					dict[register] = dict[register]!! - value
				}
			} else if (condition == "!=" && dict[conditionRegister]!! != conditionValue) {
				if (operation == "inc") {
					dict[register] = dict[register]!! + value
				} else {
					dict[register] = dict[register]!! - value
				}
			} else if (condition == ">=" && dict[conditionRegister]!! >= conditionValue) {
				if (operation == "inc") {
					dict[register] = dict[register]!! + value
				} else {
					dict[register] = dict[register]!! - value
				}
			} else if (condition == "<=" && dict[conditionRegister]!! <= conditionValue) {
				if (operation == "inc") {
					dict[register] = dict[register]!! + value
				} else {
					dict[register] = dict[register]!! - value
				}
			}
			val currMax = dict.values.max()
			if (currMax > max) {
				max = currMax
			}
		}
		return Pair(dict.values.max(), max)
	}

}