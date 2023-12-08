class Uppg7 {

	fun a() {
		val lines = getLinesFromFile("Input7.txt")
		val allHands = parseInput(lines)
		println(allHands)
	}


	class Hand(val hand:String, val bid:Long) {
		override fun toString(): String {
			return "Hand(hand=$hand, bid=$bid)"
		}
	}

	private fun parseInput(input: List<String>): MutableList<Hand> {
		val hands = mutableListOf<Hand>()
		for (line in input) {
			val hand = parseHand(line)
			println(hand)
			hands.add(hand)
		}
		return hands
	}

	private fun parseHand(line: String): Uppg7.Hand {
		val parts = line.split(" ")
		val hand = parts.subList(0, parts.size - 1).joinToString(" ")
		val bid = parts.last().toLong()
		return Hand(hand, bid)
	}

	fun evaluateHand(hand: String): Pair<Int, List<Char>> {
		val cardCounts = hand.groupingBy { it }.eachCount()
		val sortedByCountThenValue = cardCounts.toList().sortedWith(
			compareByDescending<Pair<Char, Int>> { it.second }.thenByDescending { it.first }
		).map { it.first }

		val score = when {
			cardCounts.any { it.value == 5 } -> 7 // Five of a kind
			cardCounts.any { it.value == 4 } -> 6 // Four of a kind
			cardCounts.size == 2 && cardCounts.any { it.value == 3 } -> 5 // Full house
			cardCounts.any { it.value == 3 } -> 4 // Three of a kind
			cardCounts.size == 3 -> 3 // Two pair
			cardCounts.size == 4 -> 2 // One pair
			else -> 1 // High card
		}

		return Pair(score, sortedByCountThenValue)
	}

	fun sortPokerHands(hands: List<String>): List<String> {
		val cardValueOrder = "AKQJT98765432".toList()

		// Map each hand to its evaluation, but also keep the original hand string
		val evaluatedHands = hands.map { hand -> Pair(hand, evaluateHand(hand)) }

		// Sort the evaluated hands
		val sortedEvaluatedHands = evaluatedHands.sortedWith(
			compareByDescending<Pair<String, Pair<Int, List<Char>>>> { it.second.first }
				.thenComparator { a, b ->
					a.second.second.zip(b.second.second).map { (x, y) ->
						cardValueOrder.indexOf(x) - cardValueOrder.indexOf(y)
					}.find { it != 0 } ?: 0
				}
		)

		// Return the original hand strings in the sorted order
		return sortedEvaluatedHands.map { it.first }
	}
}