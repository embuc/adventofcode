package y2024

import Task

/* --- Day 1: Historian Hysteria --- */
class Task1(val input: List<String>): Task {

	override fun a(): Any {
		val list1 = ArrayList<Int>();
		val list2 = ArrayList<Int>();
		for (line in input) {
			val (first, second) = line.split("   ")
			list1.add(first.toInt())
			list2.add(second.toInt())
		}
		list1.sort()
		list2.sort()
		var sum = 0
		for (i in 0 until list1.size) {
			sum += Math.abs(list2[i] - list1[i])
		}
		return sum
	}

	override fun b(): Any {
		val list1 = ArrayList<Int>();
		val list2 = ArrayList<Int>();
		for (line in input) {
			val (first, second) = line.split("   ")
			list1.add(first.toInt())
			list2.add(second.toInt())
		}
		val dict  = mutableMapOf<Int,Int>();
		for (num in list2) {
			dict[num] = dict.getOrDefault(num, 0) + 1
		}
		var sum = 0
		for (num in list1) {
			sum += num * dict.getOrDefault(num, 0)
		}

		return sum
	}
}