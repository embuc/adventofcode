package y2024

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Task9Test {

	var small_input = "12345" //expanded 0..111....22222

//	So, a disk map like 12345 would represent a one-block file,
//	two blocks of free space, a three-block file, four blocks of free space, and then a five-block file.
//
//	Each file on disk also has an ID number based on the order of the files as they appear before they are rearranged,
//	starting with ID 0. So, the disk map 12345 has three files:
//	a one-block file with ID 0,
//	a three-block file with ID 1,
//	and a five-block file with ID 2.
//	Using one character for each block where digits are the file ID and . is free space,
//	the disk map 12345 represents these individual blocks: 0..111....22222

//	move file blocks one at a time from the end of the disk to the leftmost free space block
//	(until there are no gaps remaining between file blocks).
//	For the disk map 12345, the process looks like this:
//	0..111....22222
//	02.111....2222.
//	022111....222..
//	0221112...22...
//	02211122..2....
//	022111222......

//	The final step of this file-compacting process is to update the filesystem checksum.
//	To calculate the checksum, add up the result of multiplying each of these blocks'
//	position with the file ID number it contains.
//	The leftmost block is in position 0. If a block contains free space, skip it instead.
//	Here are the checksums for the above steps:
//	0*0 + 2*1 + 2*2 + 1*3 + 1*4 + 1*5 + 2*6 + 2*7 + 2*8 = 60


	var test_input = "2333133121414131402"

	@Test
	fun a_small_input() {
		assertEquals(60L, Task9(small_input).a())
	}

	@Test
	fun a_test_input() {
		assertEquals(1928L, Task9(test_input).a())
	}

	@Test
	fun a() {
		val input = utils.readInputAsString("~/git/aoc-inputs/2024/2024_9.txt")
		val task = Task9(input)
		assertEquals(6346871685398L, task.a())
	}

	@Test
	fun b_test_input() {
		assertEquals(2858L, Task9(test_input).b())
	}

	@Test
	fun b() {
		val input = utils.readInputAsString("~/git/aoc-inputs/2024/2024_9.txt")
		val task = Task9(input)
		assertEquals(6373055193464L, task.b())
	}
}