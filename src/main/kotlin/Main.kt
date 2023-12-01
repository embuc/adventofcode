import java.io.File

fun extractFirstAndLastNumber(input: String): Pair<Int?, Int?> {
	val numberRegex = "\\d".toRegex()
	val matches = numberRegex.findAll(input).map { it.value.toInt() }.toList()

	val firstNumber = matches.firstOrNull()
	val lastNumber = matches.lastOrNull()

	return Pair(firstNumber, lastNumber)
}

fun main(args: Array<String>) {
	// Get the file from the resources folder
	val inputStream = Thread.currentThread().contextClassLoader.getResourceAsStream("Input1.txt")
		?: throw IllegalArgumentException("File not found")

	// Read lines from the file
	val lines = inputStream.bufferedReader().use { it.readLines() }

	// Process the lines
	var sum = 0
	lines.forEach { line ->
		val (firstNumber, lastNumber) = extractFirstAndLastNumber(line)
		val combinedAsString = "$firstNumber$lastNumber"
		val combinedAsInt = combinedAsString.toIntOrNull() ?: 0
		println(line)
		println(combinedAsInt)
		sum+=combinedAsInt
	}
	println("Sum: $sum")
}