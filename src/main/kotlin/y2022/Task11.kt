package y2022

import Task
import utils.gcd

//--- Day 11: Monkey in the Middle ---
class Task11(val input: List<String>) : Task {

	private data class Monkey(
		val name: String,
		val items: MutableList<Long>,
		val testDivisible: Long,
		var operation: (Long) -> Long,
		val ifTrue: String,
		val ifFalse: String,
		var inspected: Long = 0
	)

	override fun a(): Any {
		return monkeyBusiness(20, divideByThree = true);
	}

	override fun b(): Any {
		return monkeyBusiness(10_000, divideByThree = false);
	}

	private fun monkeyBusiness(rounds: Int, divideByThree: Boolean): Long {
		val monkeys = parseMonkeys()
		val monkeyMap = monkeys.associateBy { it.name }
		val superModulo = getLCM(monkeys.map { it.testDivisible })
		repeat(rounds) { iteration ->
			for (monkey in monkeys) {
				for (item in monkey.items) {
					monkey.inspected++
					var newItem = monkey.operation(item)
					if (divideByThree){
						newItem = newItem / 3
					} else {
						newItem = newItem % superModulo
					}
					if (newItem % monkey.testDivisible == 0L) {
						monkeyMap[monkey.ifTrue]!!.items.add(newItem)
					} else {
						monkeyMap[monkey.ifFalse]!!.items.add(newItem)
					}
				}
				monkey.items.clear()
			}
			if((iteration+1) % 1000 == 0 || (iteration+1) == 1 || (iteration+1) == 20){
				println("Round ${iteration+1}")
				for(monkey in monkeys){
					println("${monkey.name} ${monkey.inspected}")
				}
				println()
			}
		}
		monkeys.sortByDescending { it.inspected }
		return monkeys[0].inspected * monkeys[1].inspected
	}

	private fun getLCM(longs: List<Long>): Long {
		var lcm = longs[0]
		for (i in 1 until longs.size) {
			lcm = lcm * longs[i] / gcd(lcm, longs[i])
		}
		return lcm
	}

	private fun parseMonkeys(): MutableList<Monkey> {
		val monkeys = mutableListOf<Monkey>()
		for ((ix, line) in input.withIndex()) {
			if (line.isEmpty()) {
				continue
			}
			if (line.startsWith("Monkey")) {
				val name = line.split(" ")[1].replace(":", "")
				val items = mutableListOf<Long>()
				input[ix + 1].split(" ").map { it.replace(",", "") }.filter { it.toIntOrNull() != null }.forEach { items.add(it.toLong()) }
				val testDivisible = input[ix + 3].trim().split(" ")[3].toLong()
				val op = input[ix + 2].trim().split(" ")
				val b = op[5].toLongOrNull()
				val operand = op[4]
				val operation = when (operand) {
					"*" -> { x: Long -> x * (b ?: x) }
					"+" -> { x: Long -> x + (b ?: x) }
					else -> { x: Long -> x }
				}
				val ifTrue = input[ix + 4].trim().split(" ")[5]
				val ifFalse = input[ix + 5].trim().split(" ")[5]
				val monkey = Monkey(name, items, testDivisible, operation, ifTrue, ifFalse)
				monkeys.add(monkey)
			}
		}
		return monkeys
	}
}