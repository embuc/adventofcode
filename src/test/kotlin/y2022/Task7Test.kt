package y2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task7Test {
	val testInput = """
		${'$'} cd /
		${'$'} ls
		dir a
		14848514 b.txt
		8504156 c.dat
		dir d
		${'$'} cd a
		${'$'} ls
		dir e
		29116 f
		2557 g
		62596 h.lst
		${'$'} cd e
		${'$'} ls
		584 i
		${'$'} cd ..
		${'$'} cd ..
		${'$'} cd d
		${'$'} ls
		4060174 j
		8033020 d.log
		5626152 d.ext
		7214296 k
""".trim().trimIndent().lines().map { it.trim() }

	@Test
	fun a() {
		assertEquals(95437, Task7(testInput).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2022/2022_7.txt")
		assertEquals(1, Task7(input).a())
	}

	@Test
	fun b() {
	}

}