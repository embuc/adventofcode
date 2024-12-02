package y2023

import Task
import utils.readInputAsListOfStrings
import utils.readInputAsString
import java.lang.StringBuilder
import java.util.LinkedList

// --- Day 12: Hot Springs ---
object Task12 : Task {
	private val input = readInputAsListOfStrings("~/git/aoc-inputs/2023/2023_12.txt").map { line -> line.split(" ").let { (l, r) -> l to r } }
	private data class Input(val condition: String, val groups: List<Int>)

	private val memoizationMap = mutableMapOf<Input, Long>()

	override fun a(): Long {
		return input.sumOf { (s, counts) ->
			getArrangements(s, counts.split(",").map(String::toInt))
		}
		//Initial implementation below, but it's too slow
//		return parseInput().sumOf { (springs, sizes) ->
//			var count = 0
//			val q = LinkedList<String>()
//			q.add(springs)
//
//			while (q.isNotEmpty()) {
//				val spring = q.pop()
//				val index = spring.indexOf('?')
//
//				if (index == -1) {
//					if (isSolution(spring, sizes)) {
//						count++
//					}
//
//					continue
//				}
//
//				q.add(spring.replaceAt(index, '.'))
//				q.add(spring.replaceAt(index, '#'))
//			}
//
//			count
//		}
	}

	//	private fun String.replaceAt(index: Int, char: Char): String {
//		val sb = StringBuilder(this)
//		sb.setCharAt(index, char)
//		return sb.toString()
//	}
//
//	private fun isSolution(spring: String, sizes: List<Int>): Boolean {
//		val splits = spring.split('.').filter { it.isNotEmpty() }.map { it.count() }
//		return splits == sizes
//	}
//
//	private fun parseInput() = readInputAsString("2023_12.txt").split("\n").map {
//		val (springs, sizes) = it.split(' ')
//		springs to sizes.split(',').map { it.toInt() }


	override fun b(): Any {
		return input.sumOf { (s, counts) ->
			getArrangements("$s?$s?$s?$s?$s", "$counts,$counts,$counts,$counts,$counts".split(",").map(String::toInt))
		}
	}

// Much better and faster solution from https://github.com/ClouddJR/

	private fun getArrangements(s: String, ls: List<Int>): Long {
		val memo = IntArray(s.length) { i -> s.drop(i).takeWhile { c -> c != '.' }.length }
		val dp = mutableMapOf<Pair<Int, Int>, Long>()

		fun canTake(i: Int, l: Int) = memo[i] >= l && (i + l == s.length || s[i + l] != '#')
		fun helper(si: Int, lsi: Int): Long =
			when {
				lsi == ls.size -> if (s.drop(si).none { c -> c == '#' }) 1L else 0
				si >= s.length -> 0L
				else -> {
					if (dp[si to lsi] == null) {
						val take = if (canTake(si, ls[lsi])) helper(si + ls[lsi] + 1, lsi + 1) else 0L
						val dontTake = if (s[si] != '#') helper(si + 1, lsi) else 0L
						dp[si to lsi] = take + dontTake
					}
					dp[si to lsi]!!
				}
			}
		return helper(0, 0)
	}

}

