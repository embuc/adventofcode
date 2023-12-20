package y2023

import Task
import utils.readInput

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
		val (workflows) = parseInput(lines)
		val workflowMap = workflows.associateBy { it.name }
		var currentWorkflow = workflowMap["in"]!!
		return evaluatePartB(workflowMap, currentWorkflow, PartB());
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
		val x: LongRange = LongRange(1, 4000),
		val m: LongRange = LongRange(1, 4000),
		val a: LongRange = LongRange(1, 4000),
		val s: LongRange = LongRange(1, 4000)
	)

	fun evaluatePartB(
		workflowMap: Map<String, Task19.Workflow>,
		currentWorkflow: Task19.Workflow,
		partB: Task19.PartB,
		sum: Long = 0
	): Long {

		var currentPartB = partB
//		in{s<1351:px,qqz}
//		var partialSum = sum
		println("Current node: ${currentWorkflow.name} current sum:"+ (getProductOfRanges(currentPartB)) + " currentPartB: $currentPartB")

		var partialSum = 0L
		for (rule in currentWorkflow.rules) {
			when (rule.name) {
				"x" -> {
					if (rule.operator == "<") {
						val newRange = adjustRangeForLessThanCondition(currentPartB.x, rule.conditionValue)
						if (!newRange.isEmpty()) {
							val overPart = currentPartB.copy(x = newRange)
							if(rule.target == "A") {
								partialSum += getProductOfRanges(overPart)
							} else if (rule.target == "R") {
//								return 0L
							} else {
								partialSum += evaluatePartB(workflowMap, workflowMap[rule.target]!!, overPart, partialSum)
							}
						} else {
							//this range can not continue
//							return 0L
						}
						val overSecondaryPart2 = adjustRangeForMoreThanCondition(currentPartB.x, rule.conditionValue, true)
						if (!overSecondaryPart2.isEmpty()) {
							currentPartB = currentPartB.copy(x = overSecondaryPart2)
						} else {
							//this range can not continue
//							return 0L
						}
					} else { //">"
						val newRange = adjustRangeForMoreThanCondition(currentPartB.x, rule.conditionValue)
						if (!newRange.isEmpty()) {
							val overPart = currentPartB.copy(x = newRange)
							if(rule.target == "A") {
								partialSum += getProductOfRanges(overPart)
							} else if (rule.target == "R") {
//								return 0L
							} else {
								partialSum += evaluatePartB(workflowMap, workflowMap[rule.target]!!, overPart, partialSum)
							}
						} else {
							//this range can not continue
//							return 0L
						}
						val overSecondaryPart2 = adjustRangeForLessThanCondition(currentPartB.x, rule.conditionValue, true)
						if (!overSecondaryPart2.isEmpty()) {
							currentPartB = currentPartB.copy(x = overSecondaryPart2)
						} else {
							//this range can not continue
//							return 0L
						}
					}
				}

				"m" -> {
					if (rule.operator == "<") {
						val newRange = adjustRangeForLessThanCondition(currentPartB.m, rule.conditionValue)
						if (!newRange.isEmpty()) {
							val overPart = currentPartB.copy(m = newRange)
							if(rule.target == "A") {
								partialSum += getProductOfRanges(overPart)
							} else if (rule.target == "R") {
//								return 0L
							} else {
								partialSum += evaluatePartB(workflowMap, workflowMap[rule.target]!!, overPart, partialSum)
							}
						} else {
							//this range can not continue
//							return 0L
						}
						val overSecondaryPart2 = adjustRangeForMoreThanCondition(currentPartB.m, rule.conditionValue, true)
						if (!overSecondaryPart2.isEmpty()) {
							currentPartB = currentPartB.copy(m = overSecondaryPart2)
						} else {
							//this range can not continue
//							return 0L
						}
					} else { //">"
						val newRange = adjustRangeForMoreThanCondition(currentPartB.m, rule.conditionValue)
						if (!newRange.isEmpty()) {
							val overPart = currentPartB.copy(m = newRange)
							if(rule.target == "A") {
								partialSum += getProductOfRanges(overPart)
							} else if (rule.target == "R") {
//								return 0L
							} else {
								partialSum += evaluatePartB(workflowMap, workflowMap[rule.target]!!, overPart, partialSum)
							}
						} else {
							//this range can not continue
//							return 0L
						}
						val overSecondaryPart2 = adjustRangeForLessThanCondition(currentPartB.m, rule.conditionValue, true)
						if (!overSecondaryPart2.isEmpty()) {
							currentPartB = currentPartB.copy(m = overSecondaryPart2)
						} else {
							//this range can not continue
//							return 0L
						}
					}
				}

				"a" -> {
					if (rule.operator == "<") {
						val newRange = adjustRangeForLessThanCondition(currentPartB.a, rule.conditionValue)
						if (!newRange.isEmpty()) {
							val overPart = currentPartB.copy(a = newRange)
							if(rule.target == "A") {
								partialSum += getProductOfRanges(overPart)
							} else if (rule.target == "R") {
//								return 0L
							} else {
								partialSum += evaluatePartB(workflowMap, workflowMap[rule.target]!!, overPart, partialSum)
							}
						} else {
							//this range can not continue
//							return 0L
						}
						val overSecondaryPart2 = adjustRangeForMoreThanCondition(currentPartB.a, rule.conditionValue, true)
						if (!overSecondaryPart2.isEmpty()) {
							currentPartB = currentPartB.copy(a = overSecondaryPart2)
						} else {
							//this range can not continue
//							return 0L
						}
					} else { //">"
						val newRange = adjustRangeForMoreThanCondition(currentPartB.a, rule.conditionValue)
						if (!newRange.isEmpty()) {
							val overPart = currentPartB.copy(a = newRange)
							if(rule.target == "A") {
								partialSum += getProductOfRanges(overPart)
							} else if (rule.target == "R") {
//								return 0L
							} else {
								partialSum += evaluatePartB(workflowMap, workflowMap[rule.target]!!, overPart, partialSum)
							}
						} else {
							//this range can not continue
//							return 0L
						}
						val overSecondaryPart2 = adjustRangeForLessThanCondition(currentPartB.a, rule.conditionValue, true)
						if (!overSecondaryPart2.isEmpty()) {
							currentPartB = currentPartB.copy(a = overSecondaryPart2)
						} else {
							//this range can not continue
//							return 0L
						}
					}
				}

				"s" -> {
					if (rule.operator == "<") {
						val newRange = adjustRangeForLessThanCondition(currentPartB.s, rule.conditionValue)
						if (!newRange.isEmpty()) {
							val overPart = currentPartB.copy(s = newRange)
							if(rule.target == "A") {
								partialSum += getProductOfRanges(overPart)
							} else if (rule.target == "R") {
//								return 0L
							} else {
								partialSum += evaluatePartB(workflowMap, workflowMap[rule.target]!!, overPart, partialSum)
							}
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
							if(rule.target == "A") {
								partialSum += getProductOfRanges(overPart)
							} else if (rule.target == "R") {
//								return 0L
							}else{
								partialSum += evaluatePartB(workflowMap, workflowMap[rule.target]!!, overPart, partialSum)
							}
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
//			println("partialSum + getProductOfRanges(currentPartB)" + (partialSum + getProductOfRanges(currentPartB)))
			partialSum += getProductOfRanges(currentPartB)
//			partialSum += getProductOfRanges(currentPartB)
		} else if (currentWorkflow.nextOrEndTarget == "R") {
//			return 0L
		} else if (currentWorkflow.nextOrEndTarget != "") {
//			return evaluatePartB(workflowMap, workflowMap[currentWorkflow.nextOrEndTarget]!!, currentPartB, partialSum)
			partialSum += evaluatePartB(workflowMap, workflowMap[currentWorkflow.nextOrEndTarget]!!, currentPartB, partialSum)
		}
//		println("partialSum bottom: $partialSum")
		return partialSum
	}

	fun getProductOfRanges(overPart: Task19.PartB): Long {
		return (overPart.x.last - overPart.x.start + 1 ) *
				(overPart.m.last - overPart.m.start + 1) *
				(overPart.a.last - overPart.a.start + 1) *
				(overPart.s.last - overPart.s.start + 1)

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
			term = threshold
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