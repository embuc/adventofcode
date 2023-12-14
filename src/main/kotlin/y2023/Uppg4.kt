package y2023

import Task
import utils.getLinesFromFile


class Uppg4:Task {

	override fun a():Any {
		val lines = getLinesFromFile("Input4.txt")
		val cards = lines.map { parseCardString(it) }
		var sum = 0
		for (card in cards) {
			println(card)
			val score = calculateCardScore(card)
			println("Score: $score")
			sum += score
		}
		return sum
	}

	override fun b():Any {
		val lines = getLinesFromFile("Input4.txt")
		val copies = getCopies(lines)
		return copies.map { it.value }.sum()
	}
	data class Card(val counter: Int, val winningNumbers: List<Int>, val lotteryNumbers: List<Int>)

	fun parseCardString(cardString: String): Card {
		val parts = cardString.split(":")
		val counter = parts[0].trim().substringAfter("Card").trim().toInt()

		val numbers = parts[1].split("|").map { it.trim() }
		val winningNumbers = numbers[0].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
		val lotteryNumbers = numbers[1].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }

		return Card(counter, winningNumbers, lotteryNumbers)
	}

	fun calculateCardScore(card: Card): Int {
		var score = 0
		var matchedCount = 0 // Count of matched numbers

		val matching = mutableListOf<Int>();
		for (number in card.lotteryNumbers) {
			if (number in card.winningNumbers) {
				matchedCount++
				if (matchedCount > 2) {
					score *= 2 // Double the score from the third match onwards
				} else {
					score += 1
				}
				matching.add(number)
			}
		}
		println(matching)
		return score
	}

	fun calculateCardScore2(card: Card): Int {
		var score = 0
		var matchedCount = 0 // Count of matched numbers

		val matching = mutableListOf<Int>();
		for (number in card.lotteryNumbers) {
			if (number in card.winningNumbers) {
				matchedCount++
				if (matchedCount > 2) {
					score *= 2 // Double the score from the third match onwards
				} else {
					score += 1
				}
				matching.add(number)
			}
		}
		println(matching)
		return matching.size
	}

	fun getCopies(lines: List<String>): MutableMap<Int, Int> {
		val cards = lines.map { parseCardString(it) }

		val copies: MutableMap<Int, Int> = mutableMapOf()

		for (card in cards) {
			copies[card.counter] = 1
		}
		for (card in cards) {
			println(card)
			val score = calculateCardScore2(card)
			for (i in 1..score) {
				println("Adding $i to ${card.counter}")
				copies[card.counter + i] = copies[card.counter + i]!! + copies[card.counter]!!
			}
		}

		for (copy in copies) {
			println("Copy: ${copy.key} Count: ${copy.value}")
		}
		return copies
	}
}