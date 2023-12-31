package y2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Task3Test {

	val task = Task3

	//@Test TODO fix
	fun testExtractNumber() {

		val matrix = arrayOf(
			arrayOf('4', '6', '7', '.', '.', '1', '1', '4', '.', '.'),
			// ... other rows
		)
		assertEquals("467", task.extractNumber(matrix, 0, 0))
		assertEquals("114", task.extractNumber(matrix, 0, 5))
	}

	@Test
	fun testIsAdjacentToSymbol() {
		val matrix = arrayOf(
			arrayOf('4', '6', '7', '*', '.', '1', '1', '4', '.', '.'),
			arrayOf('.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
			// ... other rows
		)
		assertEquals(false, task.isAdjacentToSymbol(matrix, 0, 3)) // not Adjacent to symbol (symbol itselft)
		assertEquals(true, task.isAdjacentToSymbol(matrix, 0, 2)) // Adjacent to '*'
		assertEquals(false, task.isAdjacentToSymbol(matrix, 0, 5)) // Not adjacent to any symbol
	}

	//@Test TODO fix
	fun testValidNumbers() {
		val matrix = arrayOf(
			arrayOf('4', '6', '7', '.', '.', '1', '1', '4', '.', '.'),
			arrayOf('.', '.', '.', '*', '.', '.', '.', '.', '.', '.'),
			// ... other rows
		)
		val expected = listOf("114") // Add expected valid numbers
		val actual = task.findValidNumbers(matrix)
		assertEquals(expected, actual)
	}

	@Test
	fun testValidNumbers2() {
		val testInput = """
    ..........................................389.314.................206......................449.523..................138..................512
    .........+.....954......723..........................................*.............687.....*..........692..........*.......................*
    121......992...............*.......%585....814............936.......102..#353.........*.....140.........*..434..301..................%..315.
    .../....................877................*...523............489.................*....380.......174..263.@..............824......710.......
    ...........$..733*758.......435...656...483.....................*..%855........154.779.....674...............320+....+........373...........
""".trimIndent().split("\n").toTypedArray()
		val matrix = testInput.map { it.toCharArray().toTypedArray() }.toTypedArray()
		val expected = listOf("206", "449", "138", "512", "723", "687", "692", "121", "992", "585", "814", "102", "353", "140", "434", "301",
			"315", "877", "489", "380", "263", "710", "733", "758", "483", "855", "154", "779", "320")
		val actual = task.findValidNumbers(matrix)
		assertEquals(expected, actual)
	}

	@Test
	fun testValidNumbers3() {
		val testInput = """
    ..........................................389.314.................206......................449.523..................138..................512
    .........+.....954......723..........................................*.............687.....*..........692..........*.......................*
    121......992...............*.......%585....814............936.......102..#353.........*.....140.........*..434..301..................%..315.
""".trimIndent().split("\n").toTypedArray()
		val matrix = testInput.map { it.toCharArray().toTypedArray() }.toTypedArray()

		val expected: List<Long> = listOf("21012", "62860", "41538", "161280").map{ it.toLong()}
		val actual = task.findProducts(matrix)
		assertEquals(expected, actual)
	}

}