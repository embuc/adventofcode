package y2015

import Task

//--- Day 25: Let It Snow ---
class Task25(val input:String):Task {
//	|    1         2         3         4         5         6
//	---+---------+---------+---------+---------+---------+---------+
//	1 | 20151125  18749137  17289845  30943339  10071777  33511524
//	2 | 31916031  21629792  16929656   7726640  15514188   4041754
//	3 | 16080970   8057251   1601130   7981243  11661866  16474243
//	4 | 24592653  32451966  21345942   9380097  10600672  31527494
//	5 |    77061  17552253  28094349   6899651   9250759  31663883
//	6 | 33071741   6796745  25397450  24659492   1534922  27995004

	override fun a(): Any {
		val inputs = Regex("""row (\d+), column (\d+)""").find(input)!!
		val row = inputs.groups[1]!!.value.toInt()
		val col = inputs.groups[2]!!.value.toInt()
		val first = 20151125L
		val mul = 252533L
		val mod = 33554393L

		val n = row + col - 1
		val k = n * (n + 1) / 2 - row + 1
		var code = first
		for (i in 1 until k) {
			code = code * mul % mod
		}
		return code
	}

	override fun b(): Any {
//		not needed
		return 0
	}
}