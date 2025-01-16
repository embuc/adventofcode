package y2017

import Task
//--- Day 13: Packet Scanners ---
class Task13(val input:List<String>):Task {

	override fun a(): Any {
		//parse firewall
		val firewall = mutableMapOf<Int, Int>()
		for (line in input) {
			val parts = line.split(": ")
			firewall[parts[0].toInt()] = parts[1].toInt()
		}
		var severity = 0
		for (i in firewall.keys) {
			val range = firewall[i]!!
			if (i % (2 * (range - 1)) == 0) {
				severity += i * range
			}
		}
		return severity
	}

	override fun b(): Any {
		return 0
	}
}