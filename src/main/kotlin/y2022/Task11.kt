package y2022

import Task

//--- Day 11: Monkey in the Middle ---
class Task11(val input: List<String>) : Task {

	private data class Monkey(
		val name: String,
		val items: MutableList<Int>,
		val testDivisible: Int,
		var operation: (Int) -> Int,
		val ifTrue: String,
		val ifFalse: String,
		var inspected:Int = 0
	)

	override fun a(): Any {
		val monkeys = mutableListOf<Monkey>()
		for ((ix, line) in input.withIndex()) {
			if (line.isEmpty()) {
				continue
			}
			if (line.startsWith("Monkey")) {
				val name = line.split(" ")[1].replace(":", "")
				val items = mutableListOf<Int>()
				input[ix + 1].split(" ").map { it.replace(",", "") }.filter { it.toIntOrNull() != null }.forEach { items.add(it.toInt()) }
				val testDivisible = input[ix + 3].trim().split(" ")[3].toInt()
				val op = input[ix + 2].trim().split(" ")
				val b = op[5].toIntOrNull()
				val operand = op[4]
				val operation = when (operand) {
					"*" -> { x: Int -> x * (b ?: x) }
					"+" -> { x: Int -> x + (b ?: x) }
					else -> { x: Int -> x }
				}
				val ifTrue = input[ix + 4].trim().split(" ")[5]
				val ifFalse = input[ix + 5].trim().split(" ")[5]
				val monkey = Monkey(name, items, testDivisible, operation, ifTrue, ifFalse)
				monkeys.add(monkey)
			}
		}
		//map monkeys to their names
		val monkeyMap = monkeys.associateBy { it.name }
		repeat(20) {
			for (monkey in monkeys) {
				for (item in monkey.items) {
					monkey.inspected++
					var newItem = monkey.operation(item)
					newItem = newItem / 3
					if (newItem % monkey.testDivisible == 0) {
						monkeyMap[monkey.ifTrue]!!.items.add(newItem)
					} else {
						monkeyMap[monkey.ifFalse]!!.items.add(newItem)
					}
				}
				monkey.items.clear()
			}
		}
		monkeys.sortByDescending { it.inspected }
//		monkeys.forEach { println(it) }

		return monkeys[0].inspected * monkeys[1].inspected
	}

	override fun b(): Any {
		return 0
	}
}