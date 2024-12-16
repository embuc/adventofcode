package y2015

import Task
import java.security.MessageDigest

/*--- Day 4: The Ideal Stocking Stuffer ---*/
class Task4(val input:String):Task {

	override fun a(): Any {
		val md = MessageDigest.getInstance("MD5")
		for (i in 0..1_000_000){
			val hash = md.digest("$input$i".toByteArray()).joinToString("") { "%02x".format(it) }
			if (hash.startsWith("00000")) {
				return i
			}
		}
		return 0
	}

	override fun b(): Any {
		val md = MessageDigest.getInstance("MD5")
		for (i in 0..10_000_000){
			val hash = md.digest("$input$i".toByteArray()).joinToString("") { "%02x".format(it) }
			if (hash.startsWith("000000")) {
				return i
			}
		}
		return 0
	}
}