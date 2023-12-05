import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CardScoreTest {

	@Test
	fun testCalculateCardScore() {
		val cardString = "Card 1: 4 16 87 61 11 37 43 25 49 17 | 54 36 14 55 83 58 43 15 87 17 97 11 62 75 37 4 49 80 42 61 20 79 25 24 16"
		val card = parseCardString(cardString)
		val score = calculateCardScore(card)

		// Calculate the expected score based on the test string
		val expectedScore = 1 + 1 + 2 + 4 + 8 + 16 + 32 + 64 + 128 + 256
		assertEquals(expectedScore, score)
	}
}