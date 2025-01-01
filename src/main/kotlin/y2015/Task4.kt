package y2015

import Task
import java.security.MessageDigest

/*--- Day 4: The Ideal Stocking Stuffer ---*/
class Task4(val input: String) : Task {

	override fun a(): Any {
		return simpleAndClearA2()
	}

	fun simpleAndClearA2(): Any {
		val md = MessageDigest.getInstance("MD5")
		var i = 0
		var array: ByteArray
		while (true) {
			array = md.digest(("yzbqklnj" + (i++).toString()).toByteArray()) // Put input here
			if (array[0].toInt() == 0 && array[1].toInt() == 0 && (array[2].toInt() shr 4 and 0xf) == 0) {
				break
			}
		}
		println("Lowest value needed: " + (i - 1))
		return i - 1
	}

	override fun b(): Any {
		return simpleAndClearB2()
	}

	fun simpleAndClearB2(): Any {
		val md = MessageDigest.getInstance("MD5")
		var i = 0
		var array: ByteArray
		while (true) {
			array = md.digest(("yzbqklnj" + (i++).toString()).toByteArray())
			if (array[0].toInt() == 0 && array[1].toInt() == 0 && (array[2].toInt() shr 4 and 0xf) == 0) {
				if (array[2].toInt() == 0)
					break
			}
		}
		println("Lowest value needed: " + (i - 1))
		return i - 1
	}

	//reads better but 40x slower
	private fun simpleAndClearB(): Any {
		val md = MessageDigest.getInstance("MD5")
		for (i in 0..10_000_000) {
			val hash = md.digest("$input$i".toByteArray()).joinToString("") { "%02x".format(it) }
			if (hash.startsWith("000000")) {
				return i
			}
		}
		return 0
	}

	private fun simpleA() {
		//this reads better but much slower
		//		val md = MessageDigest.getInstance("MD5")
		//		for (i in 0..1_000_000) {
		//			val hash = md.digest("$input$i".toByteArray()).joinToString("") { "%02x".format(it) }
		//			if (hash.startsWith("00000")) {
		//				return i
		//			}
		//		}
		//		return 0
	}
}