package y2015

import Task

/*--- Day 1: Not Quite Lisp ---*/
class Task1(val input:String):Task {

	override fun a(): Any {
		return input.count { it == '(' } - input.count { it == ')' }
	}

	override fun b(): Any {
		var floor = 0
		input.forEachIndexed { index, c ->
			if (c == '(') floor++
			else floor--
			if (floor < 0) return index + 1
		}
		return -1
	}
}