

fun main(args: Array<String>) {
	val uppg = Uppg5();
	uppg.uppg5b();
}

fun getLinesFromFile(fileName: String): List<String> {
	val inputStream = Thread.currentThread().contextClassLoader.getResourceAsStream(fileName)
		?: throw IllegalArgumentException("File not found")
	val lines = inputStream.bufferedReader().use { it.readLines() }
	return lines
}