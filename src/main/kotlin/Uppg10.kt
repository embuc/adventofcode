class Uppg10 {

	fun a() {
		val lines = getLinesFromFile("Input10.txt")
		val points = parseToPoints(lines)
		println(points)
	}

	private fun parseToPoints(lines: List<String>): Any {
		return lines.map { parseToPoints(it)
	}
}

	private fun parseToPoints(lines: String): Any {
		return lines.split("").map { it.trim() }
	}
