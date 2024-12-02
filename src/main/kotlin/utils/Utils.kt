// @author embuc
package utils

import java.io.File
import java.io.FileInputStream

fun readInputAsString(task: String): String {
//	val inputStream = File("src/main/resources/$task").inputStream()
	return getFileInputStream(task).bufferedReader().use { it.readText() }.trimIndent()
}

fun getFileInputStream(relativePath: String): FileInputStream {
	val homeDirectory = System.getProperty("user.home")
	val path = relativePath.replaceFirst("~", homeDirectory)
	return FileInputStream(path)
}

fun readInputAsListOfStrings(fileName: String): List<String> {
	val inputStream = getFileInputStream(fileName)
	return inputStream.bufferedReader().use { it.readLines() }
}

fun readTestInputAsListOfStrings(fileName: String): List<String> {
	val inputStream = Thread.currentThread().contextClassLoader.getResourceAsStream(fileName)
		?: throw IllegalArgumentException("File not found")
	return inputStream.bufferedReader().use { it.readLines() }
}

fun <T> Collection<T>.allEquals(value: T): Boolean {
	for (i in this) if (i != value) return false
	return true
}

fun <T> Collection<T>.forEachPair(action: (T, T) -> Unit) {
	val list = this.toList()
	for (i in list.indices) {
		for (j in i + 1 until list.size) {
			action(list[i], list[j])
		}
	}
}

//positive modulus of an integer.
infix fun Int.pm(other: Int): Int {
	val mod = this % other
	return if (mod < 0) mod + other else mod
}

fun Collection<Long>.lcm() = reduce { a, b -> lcm(a, b) }

fun lcm(a: Long, b: Long): Long = (a * b) / gcd(a, b)

fun gcd(a: Long, b: Long): Long = if (a == 0L) b else gcd(b % a, a)

fun IntRange.size() = last - start + 1

fun die(): Nothing = throw RuntimeException("boom crash bang")

operator fun <T> List<T>.component6(): T {
	return this[5]
}