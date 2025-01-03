package y2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Task15Test {

	val testInput = """
		##########
		#..O..O.O#
		#......O.#
		#.OO..O.O#
		#..O@..O.#
		#O#..O...#
		#O..O..O.#
		#.OO.O.OO#
		#....O...#
		##########
		
		<vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
		vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
		><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
		<<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
		^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
		^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
		>^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
		<><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
		^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
		v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^
	""".trimIndent().lines()

	val smallInput = """
		########
		#..O.O.#
		##@.O..#
		#...O..#
		#.#.O..#
		#...O..#
		#......#
		########
		
		<^^>>>vv<v>>v<<
	""".trimIndent().lines()

	val smallInput_b1 = """
		########
		########
		########
		##@.O..#
		########
		########
		########
		########
		
		<>>>>>>>>>>
	""".trimIndent().lines()

	val smallInput_b2 = """
		########
		####.###
		####.###
		####O###
		####.###
		####@###
		########
		########
		
		v^^^^^^^^^^^^
	""".trimIndent().lines()

	val smallInput_b3 = """
		#######
		#...#.#
		#.....#
		#..OO@#
		#..O..#
		#.....#
		#######
		
		<vv<<^^<<^^
	""".trimIndent().lines()

	@Test
	fun a() {
		assertEquals(2028L, Task15(smallInput).a())
		assertEquals(10092L, Task15(testInput).a())
		val input = utils.readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_15.txt")
		assertEquals(1360570L, Task15(input).a())
	}

	@Test
	fun b() {
		assertEquals(1751L, Task15(smallInput).b())
		assertEquals(312L, Task15(smallInput_b1).b())
		assertEquals(108L, Task15(smallInput_b2).b())
		assertEquals(618L, Task15(smallInput_b3).b())
		assertEquals(9021L, Task15(testInput).b())
		val input = utils.readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_15.txt")
		assertEquals(1381446L, Task15(input).b())
	}
}