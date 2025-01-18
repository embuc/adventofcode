package y2017

import Task

class Task17(val input:Int):Task{

	override fun a(): Any {
		val list = mutableListOf(0)
		var pos = 0
		for (i in 1..2017) {
			pos = (pos + input) % list.size + 1
			list.add(pos, i)
		}
		return list[(pos + 1) % list.size]
	}

	override fun b(): Any {
		var pos = 0
		var afterZero = 0
		for (i in 1..50_000_000) {
			pos = (pos + input) % i + 1
			if (pos == 1) {
				afterZero = i
			}
		}
		return afterZero
	}
}