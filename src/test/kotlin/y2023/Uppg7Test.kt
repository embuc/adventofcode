package y2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Uppg7Test {
	val uppg7 = Uppg7()

	@Test
	fun testPokerHandSorting() {
		val hands = listOf(
			Uppg7.Hand("23456", 100),
			Uppg7.Hand("A23A4", 200),
			Uppg7.Hand("23432", 300),
			Uppg7.Hand("TTT98", 400),
			Uppg7.Hand("23332", 500),
			Uppg7.Hand("AA8AA", 600),
			Uppg7.Hand("AAAAA", 700)
		)
		val expectedSortedHands = listOf(
			Uppg7.Hand("AAAAA", 700),
			Uppg7.Hand("AA8AA", 600),
			Uppg7.Hand("23332", 500),
			Uppg7.Hand("TTT98", 400),
			Uppg7.Hand("23432", 300),
			Uppg7.Hand("A23A4", 200),
			Uppg7.Hand("23456", 100)
		).reversed()

		val sortedHands = uppg7.sortPokerHands(hands)

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
			Uppg7.Hand("32T3K", 765),
			Uppg7.Hand("T55J5", 684),
			Uppg7.Hand("KK677", 28),
			Uppg7.Hand("KTJJT", 220),
			Uppg7.Hand("QQQJA", 483),
		)
		val expectedSortedHands = listOf(
			Uppg7.Hand("32T3K", 765),
			Uppg7.Hand("KTJJT", 220),
			Uppg7.Hand("KK677", 28),
			Uppg7.Hand("T55J5", 684),
			Uppg7.Hand("QQQJA", 483),
		)

		val sortedHands = uppg7.sortPokerHands(hands)
		assertEquals(expectedSortedHands, sortedHands)
		val sum = uppg7.getSum(sortedHands)
		assertEquals(6440, sum);
	}

	@Test
	fun testEdgeCases() {
		val testHands = getTestHands()
		val sortedHands = uppg7.sortPokerHands(testHands)
		val expectedSortedHands = getExpectedSortedHands()
		assertEquals(expectedSortedHands, sortedHands)
		val sum = uppg7.getSum(sortedHands)
		assertEquals(6592, sum);
	}

	@Test
	fun testEdgeCasesPartB() {
		val testHands = getTestHands()
		val sortedHands = uppg7.sortPokerHandsPartB(testHands)
		val expectedSortedHands = getExpectedSortedHandsPartB()
		assertEquals(expectedSortedHands, sortedHands)
		val sum = uppg7.getSum(sortedHands)
		assertEquals(6839, sum);
	}

	private fun getTestHands(): List<Uppg7.Hand> {
		return listOf(
			Uppg7.Hand("2345A", 1),
			Uppg7.Hand("Q2KJJ", 13),
			Uppg7.Hand("Q2Q2Q", 19),
			Uppg7.Hand("T3T3J", 17),
			Uppg7.Hand("T3Q33", 11),
			Uppg7.Hand("2345J", 3),
			Uppg7.Hand("J345A", 2),
			Uppg7.Hand("32T3K", 5),
			Uppg7.Hand("T55J5", 29),
			Uppg7.Hand("KK677", 7),
			Uppg7.Hand("KTJJT", 34),
			Uppg7.Hand("QQQJA", 31),
			Uppg7.Hand("JJJJJ", 37),
			Uppg7.Hand("JAAAA", 43),
			Uppg7.Hand("AAAAJ", 59),
			Uppg7.Hand("AAAAA", 61),
			Uppg7.Hand("2AAAA", 23),
			Uppg7.Hand("2JJJJ", 53),
			Uppg7.Hand("JJJJ2", 41)
		)
	}

	private fun getExpectedSortedHands(): List<Uppg7.Hand> {
		return listOf(
			Uppg7.Hand("2345J", 3),
			Uppg7.Hand("2345A", 1),
			Uppg7.Hand("J345A", 2),
			Uppg7.Hand("32T3K", 5),
			Uppg7.Hand("Q2KJJ", 13),
			Uppg7.Hand("T3T3J", 17),
			Uppg7.Hand("KTJJT", 34),
			Uppg7.Hand("KK677", 7),
			Uppg7.Hand("T3Q33", 11),
			Uppg7.Hand("T55J5", 29),
			Uppg7.Hand("QQQJA", 31),
			Uppg7.Hand("Q2Q2Q", 19),
			Uppg7.Hand("2JJJJ", 53),
			Uppg7.Hand("2AAAA", 23),
			Uppg7.Hand("JJJJ2", 41),
			Uppg7.Hand("JAAAA", 43),
			Uppg7.Hand("AAAAJ", 59),
			Uppg7.Hand("JJJJJ", 37),
			Uppg7.Hand("AAAAA", 61)
		)
	}

	private fun getExpectedSortedHandsPartB(): List<Uppg7.Hand> {
		return listOf(
			Uppg7.Hand("2345A", 1L),
			Uppg7.Hand("J345A", 2L),
			Uppg7.Hand("2345J", 3L),
			Uppg7.Hand("32T3K", 5L),
			Uppg7.Hand("KK677", 7L),
			Uppg7.Hand("T3Q33", 11L),
			Uppg7.Hand("Q2KJJ", 13L),
			Uppg7.Hand("T3T3J", 17L),
			Uppg7.Hand("Q2Q2Q", 19L),
			Uppg7.Hand("2AAAA", 23L),
			Uppg7.Hand("T55J5", 29L),
			Uppg7.Hand("QQQJA", 31L),
			Uppg7.Hand("KTJJT", 34L),
			Uppg7.Hand("JJJJJ", 37L),
			Uppg7.Hand("JJJJ2", 41L),
			Uppg7.Hand("JAAAA", 43L),
			Uppg7.Hand("2JJJJ", 53L),
			Uppg7.Hand("AAAAJ", 59L),
			Uppg7.Hand("AAAAA", 61L)
		)
	}
}