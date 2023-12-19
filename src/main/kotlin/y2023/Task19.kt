package y2023

import Task
import utils.readInput
import y2023.Task19.evaluatePart

object Task19 : Task {

	override fun a(): Any {
		val input = readInput("2023_19.txt")
		return solveA(input)
	}

	override fun b(): Any {
		val input = readInput("2023_19.txt")
		return solveB(input)
	}

	data class Workflow(val name: String, val rules: ArrayList<Rule>, val nextOrEndTarget: String)

	data class Rule(
		val name: String,
		val condition: (Int) -> Boolean,
		val conditionValue: Int,
		val operator: String,
		val target: String
	)

	data class Part(val x: Long, val m: Long, val a: Long, val s: Long)

	fun solveA(input: String): Long {
		val (workflows, parts) = parseInput(input)
		val workflowMap = workflows.associateBy { it.name }
		var sum = 0L
		for (part in parts) {
			var currentWorkflow = workflowMap["in"]!!
			var currentPart = part
			sum += evaluatePart(workflowMap, currentWorkflow, currentPart);
		}
		return sum
	}

	private fun solveB(lines: String): Long {
		val (workflows, parts) = parseInput(lines)
		val workflowMap = workflows.associateBy { it.name }
		var sum = 0L
		for (part in parts) {
			var currentWorkflow = workflowMap["in"]!!
			var currentPart = part
			sum += evaluatePart(workflowMap, currentWorkflow, currentPart);
		}
		return sum
	}

	fun evaluatePart(
		workflowMap: Map<String, Task19.Workflow>,
		currentWorkflow: Task19.Workflow,
		currentPart: Task19.Part
	): Long {
		var target: String? = null

		for (rule in currentWorkflow.rules) {
			val targetValue = when (rule.name) {
				"x" -> currentPart.x.toInt()
				"m" -> currentPart.m.toInt()
				"a" -> currentPart.a.toInt()
				"s" -> currentPart.s.toInt()
				else -> continue
			}

			if (rule.condition(targetValue)) {
				target = rule.target
				break
			}
		}

		target = target ?: currentWorkflow.nextOrEndTarget

		return when (target) {
			"A" -> currentPart.a + currentPart.s + currentPart.m + currentPart.x
			"R", "" -> 0
			else -> workflowMap[target]?.let { evaluatePart(workflowMap, it, currentPart) } ?: 0
		}
	}

	data class PartB(
		val x: LongRange = LongRange(0, 4000),
		val m: LongRange = LongRange(0, 4000),
		val a: LongRange = LongRange(0, 4000),
		val s: LongRange = LongRange(0, 4000)
	)

	fun evaluatePartB(
		workflowMap: Map<String, Task19.Workflow>,
		currentWorkflow: Task19.Workflow,
		partB: Task19.PartB,
		sum: Long = 0
	): Long {

		var currentPartB = partB
//		in{s<1351:px,qqz}
		var partialSum = sum

		for (rule in currentWorkflow.rules) {
			when (rule.name) {
				"x" -> {
					if (rule.operator == "<") currentPartB.copy(
						x = adjustRangeForLessThanCondition(
							currentPartB.x,
							rule.conditionValue
						)
					)
					else currentPartB.copy(x = adjustRangeForMoreThanCondition(currentPartB.x, rule.conditionValue))
				}

				"m" -> {
					if (rule.operator == "<") currentPartB.copy(
						m = adjustRangeForLessThanCondition(
							currentPartB.m,
							rule.conditionValue
						)
					)
					else currentPartB.copy(m = adjustRangeForMoreThanCondition(currentPartB.m, rule.conditionValue))
				}

				"a" -> {
					if (rule.operator == "<") currentPartB.copy(
						m = adjustRangeForLessThanCondition(
							currentPartB.m,
							rule.conditionValue
						)
					)
					else currentPartB.copy(m = adjustRangeForMoreThanCondition(currentPartB.m, rule.conditionValue))
				}

				"s" -> {
					if (rule.operator == "<") {
						val newRange = adjustRangeForLessThanCondition(currentPartB.s, rule.conditionValue)
						if (!newRange.isEmpty()) {
							val overPart = currentPartB.copy(s = newRange)
							if(rule.name == "A") {
								return sum + getProductOfRanges(overPart)
							} else if (rule.name == "R") {
								return 0L
							}
							partialSum += evaluatePartB(workflowMap, workflowMap[rule.target]!!, overPart, partialSum)
						} else {
							//this range can not continue
//							return 0L
						}
						val overSecondaryPart2 = adjustRangeForMoreThanCondition(currentPartB.s, rule.conditionValue, true)
						if (!overSecondaryPart2.isEmpty()) {
							currentPartB = currentPartB.copy(s = overSecondaryPart2)
						} else {
							//this range can not continue
//							return 0L
						}
					} else { //">"
						val newRange = adjustRangeForMoreThanCondition(currentPartB.s, rule.conditionValue)
						if (!newRange.isEmpty()) {
							val overPart = currentPartB.copy(s = newRange)
							partialSum += evaluatePartB(workflowMap, workflowMap[rule.target]!!, overPart, partialSum)
						} else {
							//this range can not continue
//							return 0L
						}
						val overSecondaryPart2 = adjustRangeForLessThanCondition(currentPartB.s, rule.conditionValue, true)
						if (!overSecondaryPart2.isEmpty()) {
							currentPartB = currentPartB.copy(s = overSecondaryPart2)
						} else {
							//this range can not continue
//							return 0L
						}
					}
				}
			}
		}


		if (currentWorkflow.nextOrEndTarget == "A") {
			return partialSum + getProductOfRanges(currentPartB)
		} else if (currentWorkflow.nextOrEndTarget == "R") {
			return 0L
		} else if (currentWorkflow.nextOrEndTarget != "") {
			return evaluatePartB(workflowMap, workflowMap[currentWorkflow.nextOrEndTarget]!!, currentPartB, partialSum)
		}
		return partialSum
	}

	fun getProductOfRanges(overPart: Task19.PartB): Long {
		return (overPart.x.last - overPart.x.first ) *
				(overPart.m.last - overPart.m.first) *
				(overPart.a.last - overPart.a.first) *
				(overPart.s.last - overPart.s.first)

	}

	fun adjustRangeForLessThanCondition(range: LongRange, threshold: Int, secondary: Boolean = false): LongRange {
		// If the entire range is below the threshold, return it as is
		if (range.last < threshold) return range

		// If the range starts above the threshold, return an empty range
		if (range.first >= threshold) return LongRange.EMPTY

		// Adjust the range so that it ends at the threshold - 1
		var term = 0
		if (secondary) {
			term += 1
		}

		return range.first until threshold + term
	}

	fun adjustRangeForMoreThanCondition(range: LongRange, threshold: Int, secondary: Boolean = false): LongRange {
		// If the range starts above the threshold, return as is
		if (range.first >= threshold) return range

		if (range.last < threshold) return LongRange.EMPTY

		// Adjust the range so that it starts at the threshold + 1 (or threshold if secondary is true)
		var term = threshold + 1
		if (secondary) {
			term = threshold - 1
		}
		return term until range.last + 1
	}

	fun parseInput(input: String): Pair<List<Task19.Workflow>, List<Task19.Part>> {
		val inputParts = input.split("\n\n");
		val workflows = inputParts[0].lines().map { s -> parseWorkflow(s) }
		val parts = inputParts[1].lines().map { s -> parseParts(s) }
		return Pair(workflows, parts)
	}

	fun parseParts(str: String): Task19.Part {
		val pattern = Regex("\\{x=(\\d+),m=(\\d+),a=(\\d+),s=(\\d+)}")
		val matchResult = pattern.matchEntire(str)!!
		val (x, m, a, s) = matchResult.destructured
		return Task19.Part(x.toLong(), m.toLong(), a.toLong(), s.toLong())
	}

	fun parseWorkflow(input: String): Task19.Workflow {
		val pattern = Regex("([a-zA-Z]+)\\{(.+)}")
		val matchResult = pattern.matchEntire(input)!!
		val (name, rest) = matchResult.destructured
		val rulesStr = rest.split(',').map { it.trim() }
		val rules = ArrayList(rulesStr.dropLast(1).map { parseRule(it) })
		val nextOrEndTarget = rulesStr.last()
		return Task19.Workflow(name, rules, nextOrEndTarget)
	}

	fun parseRule(input: String): Task19.Rule {
		val (conditionStr, target) = input.split(':')
		val pattern = Regex("([a-zA-Z])([<>])(\\d+)")
		val matchResult = pattern.matchEntire(conditionStr)!!

		val (key, operator, valueStr) = matchResult.destructured
		val value = valueStr.toInt()

		val condition = when (operator) {
			"<" -> { number: Int -> number < value }
			">" -> { number: Int -> number > value }
			else -> throw IllegalArgumentException("Unknown operator")
		}

		return Task19.Rule(key, condition, valueStr.toInt(), operator, target)
	}

}