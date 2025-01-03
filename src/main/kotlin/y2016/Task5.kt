package y2016

import Task
import java.security.MessageDigest

//--- Day 5: How About a Nice Game of Chess? ---
class Task5(val input: String) : Task {

	override fun a(): Any {
		var password = ""
		val md = MessageDigest.getInstance("MD5")
		var i = 0
		var array: ByteArray
		while (password.length < 8) {
			array = md.digest((input + (i++).toString()).toByteArray()) // Put input here
			if (array[0].toInt() == 0 && array[1].toInt() == 0 && (array[2].toInt() shr 4 and 0xf) == 0) {
				password += array.joinToString("") { "%02x".format(it) }[5]
			}
		}
		return password
	}

	override fun b(): Any {
		val md = MessageDigest.getInstance("MD5")
		var i = 0
		var array: ByteArray
		val bPassword = charArrayOf('-', '-', '-', '-', '-', '-', '-', '-')
//		println("bPassword: ${bPassword.joinToString("")}")
		var j = 0
		while (j < 8) {
			array = md.digest((input + (i++).toString()).toByteArray())
			if (array[0].toInt() == 0 && array[1].toInt() == 0 && (array[2].toInt() shr 4 and 0xf) == 0) {
				val five0MD5 = array.joinToString("") { "%02x".format(it) }
				if(five0MD5[5].isDigit() && five0MD5[5].toString().toInt() < 8) {
					val index = five0MD5[5].toString().toInt()
					if(bPassword[index] == '-') {
						bPassword[index] = five0MD5[6]
						j++
//						println("bPassword: ${bPassword.joinToString("")}")
					}
				}
			}
		}
		return bPassword.joinToString("")
	}
}