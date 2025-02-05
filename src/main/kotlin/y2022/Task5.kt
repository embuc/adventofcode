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
				if (input[j][1+4*(i-1)] == ' ') {
					continue
				}
				items.add(input[j][1+4*(i-1)].toString())
			}
			stacks.add(Stack(i, items))
		}
		for (i in input.subList(10, input.size)) {
			i.split(" ").let {
				instructions.add(Instruction(it[3].toInt(), it[5].toInt(), it[1].toInt()))
			}
		}
		for(i in instructions) {
			val from = stacks.find { it.id == i.from }!!
			val to = stacks.find { it.id == i.to }!!
			var start = max(from.items.size - i.count, 0 )
			val items = from.items.subList(start, from.items.size)
			to.items.addAll(items.reversed())
			from.items = from.items.subList(0, start).toMutableList()
		}
		return stacks.joinToString("") { if(it.items.isEmpty()) "" else it.items.last() }
	}

	override fun b(): Any {
		val stacks = mutableListOf<Stack>()
		val instructions = mutableListOf<Instruction>()
		for (i in 1..9) {
			val items = mutableListOf<String>()
			for (j in 7 downTo 0 ) {
				if (input[j][1+4*(i-1)] == ' ') {
					continue
				}
				items.add(input[j][1+4*(i-1)].toString())
			}
			stacks.add(Stack(i, items))
		}
		for (i in input.subList(10, input.size)) {
			i.split(" ").let {
				instructions.add(Instruction(it[3].toInt(), it[5].toInt(), it[1].toInt()))
			}
		}
		for(i in instructions) {
			val from = stacks.find { it.id == i.from }!!
			val to = stacks.find { it.id == i.to }!!
			var start = max(from.items.size - i.count, 0 )
			val items = from.items.subList(start, from.items.size)
			to.items.addAll(items)
			from.items = from.items.subList(0, start).toMutableList()
		}
		return stacks.joinToString("") { if(it.items.isEmpty()) "" else it.items.last() }
	}
}