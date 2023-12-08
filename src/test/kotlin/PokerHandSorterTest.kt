import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PokerHandSorterTest {
	val uppg7 = Uppg7()

	@Test
	fun testPokerHandSorting() {
		val hands = listOf("23456", "A23A4", "23432", "TTT98", "23332", "AA8AA", "AAAAA")
		val expectedSortedHands = listOf("AAAAA", "AA8AA", "23332", "TTT98", "23432", "A23A4", "23456")

		val sortedHands = uppg7.sortPokerHands(hands)

		assertEquals(expectedSortedHands, sortedHands)
	}
}