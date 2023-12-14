package y2023

import utils.getLinesFromFile

class Uppg7 {

	fun a() {
		val lines = getLinesFromFile("Input7.txt")
		val allHands = parseInput(lines)
//		println(allHands)
		val sortedPokerHands = sortPokerHands(allHands);
		val sum = getSum(sortedPokerHands)
		println(sum)
	}
	fun b() {
		val lines = getLinesFromFile("Input7.txt")
		val allHands = parseInput(lines)
//		println(allHands)
		val sortedPokerHands = sortPokerHandsPartB(allHands);
		val sum = getSum(sortedPokerHands)
		println(sum)
	}

	fun getSum(sortedPokerHands: List<Hand>) =
		sortedPokerHands.mapIndexed { index, hand -> hand.bid * (index + 1) }.sum()


	data class Hand(val hand: String, val bid: Long) {
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

	private fun parseHand(line: String): Hand {
		val parts = line.split(" ")
		val hand = parts.subList(0, parts.size - 1).joinToString(" ")
		val bid = parts.last().toLong()
		return Hand(hand, bid)
	}

	fun evaluateHand(hand: Hand): Pair<Int, List<Char>> {
		println()
		println(hand)
		val cardCounts = hand.hand.groupingBy { it }.eachCount()
		val cardValueOrder = "AKQJT98765432".toList().reversed()
		println("cardCounts $cardCounts")
		// Sort cards based on frequency, then by their poker value
		val sortedCards = cardCounts.entries.sortedWith(
			compareByDescending<Map.Entry<Char, Int>> { it.value }
				.thenByDescending { cardValueOrder.indexOf(it.key) }
		).map { it.key }


		println("sortedCards $sortedCards")

		val score = when {
			cardCounts.any { it.value == 5 } -> 7 // Five of a kind
			cardCounts.any { it.value == 4 } -> 6 // Four of a kind
			cardCounts.size == 2 && cardCounts.any { it.value == 3 } -> 5 // Full house
			cardCounts.any { it.value == 3 } -> 4 // Three of a kind
			cardCounts.size == 3 -> 3 // Two pair
			cardCounts.size == 4 -> 2 // One pair
			else -> 1 // High card
		}

		return Pair(score, sortedCards)
	}

	fun sortPokerHands(hands: List<Hand>): List<Hand> {
		val cardValueOrder = "AKQJT98765432".toList()

		return hands.map { hand -> Pair(hand, evaluateHand(hand)) }
			.sortedWith(compareByDescending<Pair<Hand, Pair<Int, List<Char>>>> { it.second.first }
				.thenComparator { a, b ->
					if (a.second.first == b.second.first) { // Same score
						// Compare based on the lexicographical order of the cards
						compareLexicographically(a.first.hand, b.first.hand, cardValueOrder)
					} else {
						0
					}
				}
			).map { it.first }.reversed()
	}

	fun sortPokerHandsPartB(hands: List<Hand>): List<Hand> {
		val cardValueOrder = "AKQT98765432J".toList()

		return hands.map { hand -> Pair(hand, evaluateHandWithJokers(hand)) }
			.sortedWith(compareByDescending<Pair<Hand, Pair<Int, List<Char>>>> { it.second.first }
				.thenComparator { a, b ->
					if (a.second.first == b.second.first) { // Same score
						// Compare based on the lexicographical order of the cards
						compareLexicographically(a.first.hand, b.first.hand, cardValueOrder)
					} else {
						0
					}
				}
			).map { it.first }.reversed()
	}

	fun compareLexicographically(handA: String, handB: String, cardValueOrder: List<Char>): Int {
		handA.forEachIndexed { index, charA ->
			val charB = handB[index]
			val diff = cardValueOrder.indexOf(charA) - cardValueOrder.indexOf(charB)
			if (diff != 0) return diff
		}
		return 0
	}

	fun evaluateHandWithJokers(hand: Hand): Pair<Int, List<Char>> {
		val cardCounts = hand.hand.groupingBy { it }.eachCount().toMutableMap()
		val numJokers = cardCounts.remove('J') ?: 0
		val cardValueOrder = "AKQT98765432J".toList()

		fun findBestScore(): Int {
			val maxCount = cardCounts.values.maxOrNull() ?: 0
			return when {
				numJokers + maxCount >= 5 -> 7 // Five of a kind
				numJokers + maxCount >= 4 -> 6 // Four of a kind
				cardCounts.size <= 2 && numJokers >= 0 -> 5 // Full house
				cardCounts.size <= 3 && numJokers + maxCount >= 3 -> 4 // Three of a kind
				cardCounts.size <= 4 && numJokers + cardCounts.filter { it.value >= 2 }.size >= 2 -> 3 // Two pair
				numJokers + maxCount >= 2 -> 2 // One pair
				else -> 1 // High card
			}
		}

		val score = findBestScore()
		val sortedCards = hand.hand.toList().sortedByDescending { cardValueOrder.indexOf(it) }

		println()
		println(hand)
		val maxCount = cardCounts.values.maxOrNull() ?: 0
		println("maxcount $maxCount")
		println("sortedCards $sortedCards")
		println("cardCounts $cardCounts")
		println("score $score")
		println("Pair(score, sortedCards) ${Pair(score, sortedCards)})")
		return Pair(score, sortedCards)
	}



}