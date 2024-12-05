package y2024

import Task

/* --- Day 5: Print Queue --- */
class Task5(val input: List<String>) : Task {

	override fun a(): Any {
		val (rulesMap, printMap) = getRulesAndPrints()
		val (correctPrints, _) = filterPrints(printMap, rulesMap)
		return calculateMiddleSum(correctPrints)
	}

	private fun calculateMiddleSum(correctPrints: ArrayList<ArrayList<String>>): Int {
		var sum = 0
		for (print in correctPrints) {
			sum += print.get(print.size / 2).toInt()
		}
		return sum
	}

	private fun filterPrints(
		printMap: List<List<String>>,
		rulesMap: HashMap<Int, MutableSet<Int>>
	): Pair<ArrayList<ArrayList<String>>, ArrayList<ArrayList<String>>> {
		val correctPrints = ArrayList<ArrayList<String>>()
		val incorrectPrints = ArrayList<ArrayList<String>>()
		for (print in printMap) {
			var correct = true
			for (i in print.indices) {
				val key = print[i].toInt()
				if (!correct) {
					break
				}
				for (j in i + 1 until print.size) {
					val key2 = print[j].toInt()
					val set = rulesMap.getOrDefault(key2, HashSet())
					if (set.contains(key)) {
						correct = false
						break
					}
				}
			}
			if (correct) {
				correctPrints.add(print as ArrayList<String>)
			} else {
				incorrectPrints.add(print as ArrayList<String>)
			}
		}
		return Pair(correctPrints, incorrectPrints)
	}

	private fun getRulesAndPrints(): Pair<HashMap<Int, MutableSet<Int>>, List<List<String>>> {
		val (rules, prints) = parseInput()
		val rulesArrays = rules.map { it.split("|") }
		val rulesMap = HashMap<Int, MutableSet<Int>>()
		for (rule in rulesArrays) {
			val key = rule[0].toInt()
			val set = rulesMap.getOrPut(key) { HashSet() }
			set.add(rule[1].toInt())
		}
		val printMap = prints.map { it.split(",") }
		return Pair(rulesMap, printMap)
	}

	private fun parseInput(): Pair<List<String>, List<String>> {
		val rulesLines = mutableListOf<String>()
		val printLines = mutableListOf<String>()
		for (line in input) {
			when {
				line.isBlank() -> continue
				line.contains("|") -> rulesLines.add(line.trim())
				else -> printLines.add(line.trim())
			}
		}
		return Pair(rulesLines, printLines)
	}

	override fun b(): Any {
		val (rulesMap, printMap) = getRulesAndPrints()
		val (_, incorrectPrints) = filterPrints(printMap, rulesMap)
		for (print in incorrectPrints) {
			var incorrect = true
			while (incorrect) {
				incorrect = false
				for (i in print.indices) {
					val key = print[i].toInt()
					for (j in i + 1 until print.size) {
						val key2 = print[j].toInt()
						val set = rulesMap.getOrDefault(key2, HashSet())
						if (set.contains(key)) {
							print[i] = print[j].also { print[j] = print[i] } // swap places
							incorrect = true
							break
						}
					}
					if (incorrect) break
				}
			}
		}
		return calculateMiddleSum(incorrectPrints)
	}
}