import kotlin.system.measureTimeMillis


fun main(args: Array<String>) {
	val executionTime = measureTimeMillis {
		val uppg = Uppg12();
		uppg.a();
	}
	println("Execution time: $executionTime ms")
}

fun getLinesFromFile(fileName: String): List<String> {
	val inputStream = Thread.currentThread().contextClassLoader.getResourceAsStream(fileName)
		?: throw IllegalArgumentException("File not found")
	return inputStream.bufferedReader().use { it.readLines() }
}