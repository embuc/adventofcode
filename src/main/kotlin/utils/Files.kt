package utils

import java.io.FileInputStream

fun readInputAsString(task: String): String {
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
	return inputStream.bufferedReader().use { it.readLines() }.map { it.trim() }
}