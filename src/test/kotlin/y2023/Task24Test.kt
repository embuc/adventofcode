package y2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task24Test {

	val input = """
			19, 13, 30 @ -2,  1, -2
			18, 19, 22 @ -1, -1, -2
			20, 25, 34 @ -2, -2, -4
			12, 31, 28 @ -1, -2, -1
			20, 19, 15 @  1, -5, -3
			""".trimIndent().lines()

	@Nested
	inner class Part1 {

		@Test
		fun `Matches example`() {
			val answer = Task24(input, 7.0, 27.0).a()
			assertEquals(2, answer)
		}
		@Test
		fun `Matches actual`() {
			val answer = Task24(readInputAsListOfStrings("2023_24.txt"), 200000000000000.0, 400000000000000.0).a()
			assertEquals(21785, answer)
		}
	}

	@Nested
	inner class Part2 {

		fun setupClass() {
			val homeDir = System.getProperty("user.home")
			val libraryPath = "~/Library/Java/Extensions/libz3.dylib".replaceFirst("^~".toRegex(), homeDir)
			System.load(libraryPath)
		}

		@Test
		fun `Matches example`() {
			setupClass()
			val answer = Task24(input, 7.0, 27.0).b()
			assertEquals(47L, answer)
		}
		@Test
		fun `Matches actual`() {
			setupClass()
			val answer = Task24(readInputAsListOfStrings("2023_24.txt"), 200000000000000.0, 400000000000000.0).b()
			assertEquals(554668916217145L, answer)
		}
	}
}