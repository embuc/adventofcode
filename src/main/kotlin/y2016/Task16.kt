package y2016

import Task

//--- Day 16: Dragon Checksum ---
class Task16(val input: String, val length: Int) : Task {

	override fun a(): String {
		var file = input
		while (file.length < length) {
			file = expandFile(file)
		}
		return checksum(file.substring(0, length))
	}

	private fun checksum(file: String): String {
		var checksum = file
		val builder = StringBuilder()
		while (checksum.length % 2 == 0) {
			builder.clear()
			for (i in checksum.indices step 2) {
				if (checksum[i] == checksum[i + 1]) {
					builder.append("1")
				} else {
					builder.append("0")
				}
			}
			checksum = builder.toString()
		}
		return checksum
	}

	private fun expandFile(file: String): String {
		//dragon curve \||/
		val b = StringBuilder()
		val final = StringBuilder()
		for (c in file) {
			b.append(if (c == '1') '0' else '1')
		}
		return final.append(file).append('0').append(b.reverse()).toString()
	}

	override fun b(): Any {
		return -1
	}
}