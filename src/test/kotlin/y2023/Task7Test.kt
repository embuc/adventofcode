package y2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Task7Test {
	val task7 = Task7

	@Test
	fun testPokerHandSorting() {
		val hands = listOf(
			Task7.Hand("23456", 100),
			Task7.Hand("A23A4", 200),
			Task7.Hand("23432", 300),
			Task7.Hand("TTT98", 400),
			Task7.Hand("23332", 500),
			Task7.Hand("AA8AA", 600),
			Task7.Hand("AAAAA", 700)
		)
		val expectedSortedHands = listOf(
			Task7.Hand("AAAAA", 700),
			Task7.Hand("AA8AA", 600),
			Task7.Hand("23332", 500),
			Task7.Hand("TTT98", 400),
			Task7.Hand("23432", 300),
			Task7.Hand("A23A4", 200),
			Task7.Hand("23456", 100)
		).reversed()

		val sortedHands = task7.sortPokerHands(hands)

		assertEquals(expectedSortedHands, sortedHands)
	}

	@Test
	fun testSum() {
//		32T3K 765
//		T55J5 684
//		KK677 28
//		KTJJT 220
//		QQQJA 483
		val hands = listOf(
			Task7.Hand("32T3K", 765),
			Task7.Hand("T55J5", 684),
			Task7.Hand("KK677", 28),
			Task7.Hand("KTJJT", 220),
			Task7.Hand("QQQJA", 483),
		)
		val expectedSortedHands = listOf(
			Task7.Hand("32T3K", 765),
			Task7.Hand("KTJJT", 220),
			Task7.Hand("KK677", 28),
			Task7.Hand("T55J5", 684),
			Task7.Hand("QQQJA", 483),
		)

		val sortedHands = task7.sortPokerHands(hands)
		assertEquals(expectedSortedHands, sortedHands)
		val sum = task7.getSum(sortedHands)
		assertEquals(6440, sum);
	}

	@Test
	fun testEdgeCases() {
		val testHands = getTestHands()
		val sortedHands = task7.sortPokerHands(testHands)
		val expectedSortedHands = getExpectedSortedHands()
		assertEquals(expectedSortedHands, sortedHands)
		val sum = task7.getSum(sortedHands)
		assertEquals(6592, sum);
	}

	@Test
	fun testEdgeCasesPartB() {
		val testHands = getTestHands()
		val sortedHands = task7.sortPokerHandsPartB(testHands)
		val expectedSortedHands = getExpectedSortedHandsPartB()
		assertEquals(expectedSortedHands, sortedHands)
		val sum = task7.getSum(sortedHands)
		assertEquals(6839, sum);
	}

	private fun getTestHands(): List<Task7.Hand> {
		return listOf(
			Task7.Hand("2345A", 1),
			Task7.Hand("Q2KJJ", 13),
			Task7.Hand("Q2Q2Q", 19),
			Task7.Hand("T3T3J", 17),
			Task7.Hand("T3Q33", 11),
			Task7.Hand("2345J", 3),
			Task7.Hand("J345A", 2),
			Task7.Hand("32T3K", 5),
			Task7.Hand("T55J5", 29),
			Task7.Hand("KK677", 7),
			Task7.Hand("KTJJT", 34),
			Task7.Hand("QQQJA", 31),
			Task7.Hand("JJJJJ", 37),
			Task7.Hand("JAAAA", 43),
			Task7.Hand("AAAAJ", 59),
			Task7.Hand("AAAAA", 61),
			Task7.Hand("2AAAA", 23),
			Task7.Hand("2JJJJ", 53),
			Task7.Hand("JJJJ2", 41)
		)
	}

	private fun getExpectedSortedHands(): List<Task7.Hand> {
		return listOf(
			Task7.Hand("2345J", 3),
			Task7.Hand("2345A", 1),
			Task7.Hand("J345A", 2),
			Task7.Hand("32T3K", 5),
			Task7.Hand("Q2KJJ", 13),
			Task7.Hand("T3T3J", 17),
			Task7.Hand("KTJJT", 34),
			Task7.Hand("KK677", 7),
			Task7.Hand("T3Q33", 11),
			Task7.Hand("T55J5", 29),
			Task7.Hand("QQQJA", 31),
			Task7.Hand("Q2Q2Q", 19),
			Task7.Hand("2JJJJ", 53),
			Task7.Hand("2AAAA", 23),
			Task7.Hand("JJJJ2", 41),
			Task7.Hand("JAAAA", 43),
			Task7.Hand("AAAAJ", 59),
			Task7.Hand("JJJJJ", 37),
			Task7.Hand("AAAAA", 61)
		)
	}

	private fun getExpectedSortedHandsPartB(): List<Task7.Hand> {
		return listOf(
			Task7.Hand("2345A", 1L),
			Task7.Hand("J345A", 2L),
			Task7.Hand("2345J", 3L),
			Task7.Hand("32T3K", 5L),
			Task7.Hand("KK677", 7L),
			Task7.Hand("T3Q33", 11L),
			Task7.Hand("Q2KJJ", 13L),
			Task7.Hand("T3T3J", 17L),
			Task7.Hand("Q2Q2Q", 19L),
			Task7.Hand("2AAAA", 23L),
			Task7.Hand("T55J5", 29L),
			Task7.Hand("QQQJA", 31L),
			Task7.Hand("KTJJT", 34L),
			Task7.Hand("JJJJJ", 37L),
			Task7.Hand("JJJJ2", 41L),
			Task7.Hand("JAAAA", 43L),
			Task7.Hand("2JJJJ", 53L),
			Task7.Hand("AAAAJ", 59L),
			Task7.Hand("AAAAA", 61L)
		)
	}
}