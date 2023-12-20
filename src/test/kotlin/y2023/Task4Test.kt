package y2023

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Task4Test {

	//	you end up with
	//	1 instance of card 1,
	//	2 instances of card 2,
	//	4 instances of card 3,
	//	8 instances of card 4,
	//	14 instances of card 5, and
	//	1 instance of card 6.
	//	In total, this example pile of scratchcards causes you to ultimately have 30 scratchcards!
	@Test
	fun test4b(){
		val testInputs = listOf(
			"Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
			"Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
			"Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
			"Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
			"Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
			"Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"
		)

		val uppg4 = Uppg4()
		val copies = uppg4.getCopies(testInputs)
		println(copies)
		assertEquals(6, copies.size)
		assertEquals(1, copies[1])
		assertEquals(2, copies[2])
		assertEquals(4, copies[3])
		assertEquals(8, copies[4])
		assertEquals(14, copies[5])
		assertEquals(1, copies[6])
	}

	@Test
	fun testCalculateCardScore() {
		val uppg4 = Uppg4()
		val cardString = "Card 1: 4 16 87 61 11 37 43 25 49 17 | 54 36 14 55 83 58 43 15 87 17 97 11 62 75 37 4 49 80 42 61 20 79 25 24 16"
		val card = uppg4.parseCardString(cardString)
		val score = uppg4.calculateCardScore(card)

		// Calculate the expected score based on the test string
		val expectedScore = 1 + 1 + 2 + 4 + 8 + 16 + 32 + 64 + 128 + 256
		assertEquals(expectedScore, score)
	}
}