package y2017

import Task

//--- Day 8: I Heard You Like Registers ---
class Task8 (val input: List<String>):Task {
	override fun a(): Any {
		val registerNames = mutableSetOf<String>()
		for (line in input) {
			registerNames.add(line.split(" ")[0])
		}
		val dict = registerNames.associateWith { 0 }.toMutableMap()
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
		}
		return dict.values.max()
	}

	override fun b(): Any {
		return -1
	}
}