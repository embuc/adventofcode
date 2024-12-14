package y2015

import Task

/*--- Day 2: I Was Told There Would Be No Math ---*/
class Task2(val input:List<String>):Task {

	override fun a():Any {
		return input.map { it.split("x") }.sumBy {
			val (l, w, h) = it.map { it.toInt() }
			val lw = l * w
			val wh = w * h
			val hl = h * l
			2 * lw + 2 * wh + 2 * hl + minOf(lw, wh, hl)
		}
	}

	override fun b():Any {
		return input.map { it.split("x") }.sumBy {
			val (l, w, h) = it.map { it.toInt() }
			listOf(l, w, h).sorted().subList(0, 2).sum() * 2 + l * w * h
		}
	}
}