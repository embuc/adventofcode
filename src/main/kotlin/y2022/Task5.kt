package y2022

import Task
import kotlin.math.max

//--- Day 5: Supply Stacks ---
class Task5(val input: List<String>):Task {

	private data class Stack(val id: Int, var items: MutableList<String>)
	private data class Instruction(val from: Int, val to: Int, val count: Int)

	override fun a(): Any {
		val stacks = mutableListOf<Stack>()
		val instructions = mutableListOf<Instruction>()
		for (i in 1..9) {
			val items = mutableListOf<String>()
			for (j in 7 downTo 0 ) {
//				println(input[j][1+4*(i-1)])
				if (input[j][1+4*(i-1)] == ' ') {
					continue
				}
				items.add(input[j][1+4*(i-1)].toString())
			}
			stacks.add(Stack(i, items))
		}
		for (i in input.subList(10, input.size)) {
//			println(i)
			i.split(" ").let {
				instructions.add(Instruction(it[3].toInt(), it[5].toInt(), it[1].toInt()))
			}
		}
		for(i in instructions) {
			println()
			println()
			println(i)
			val from = stacks.find { it.id == i.from }!!
			val to = stacks.find { it.id == i.to }!!
			println("from: $from")
			println("to: $to")
			var start = max(from.items.size - i.count, 0 )
			val items = from.items.subList(start, from.items.size)
			println("start: $start end: ${from.items.size} count: ${i.count} items: $items")
			to.items.addAll(items.reversed())
			from.items = from.items.subList(0, start).toMutableList()
			println("from: $from")
			println("to: $to")
		}
		return stacks.joinToString("") { if(it.items.isEmpty()) "" else it.items.last() }
	}

	override fun b(): Any {
		return ""
	}
}