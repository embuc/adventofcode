package y2023

import Task
import utils.readInputAsString

// --- Day 19: Aplenty ---
// Realizing that some sort of ranged arithmetic is needed, you set up a simple workflow language to describe the
// process. However, I had some one-off error here that took some hours to debug. Nice puzzle though!
object Task19 : Task {

	override fun a(): Any {
		val input = readInputAsString("~/git/aoc-inputs/2023/2023_19.txt")
		return solveA(input)
	}

	override fun b(): Any {
		val input = readInputAsString("~/git/aoc-inputs/2023/2023_19.txt")
		return solveB(input)
	}

	data class Workflow(val name: String, val rules: ArrayList<Rule>, val nextOrEndTarget: String)
	data class Part(val x: Long, val m: Long, val a: Long, val s: Long)

	data class RangePart(
		val x: LongRange = LongRange(1, 4000),
		val m: LongRange = LongRange(1, 4000),
		val a: LongRange = LongRange(1, 4000),
		val s: LongRange = LongRange(1, 4000)
	)

	data class Rule(
		val name: String,
		val condition: (Int) -> Boolean,
		val conditionValue: Int,
		val operator: String,
		val target: String
	)

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
		return evaluateRange(workflowMap, currentWorkflow, RangePart());
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

	fun evaluateRange(
		workflowMap: Map<String, Task19.Workflow>,
		currentWorkflow: Task19.Workflow,
		rangePart: Task19.RangePart,
		sum: Long = 0
	): Long {
		var currentPartB = rangePart

		var partialSum = 0L
		for (rule in currentWorkflow.rules) {
			val (updatedPartB, updatedSum) = processPart(rule, workflowMap, currentPartB, partialSum)
			currentPartB = updatedPartB
			partialSum = updatedSum
		}


		if (currentWorkflow.nextOrEndTarget == "A") {
			partialSum += getProductOfRanges(currentPartB)
		}else if (currentWorkflow.nextOrEndTarget != "R") {
			partialSum += evaluateRange(workflowMap, workflowMap[currentWorkflow.nextOrEndTarget]!!, currentPartB, partialSum)
		}
		return partialSum
	}

	fun processPart(
		rule: Rule,
		workflowMap: Map<String, Workflow>,
		currentPartB: RangePart,
		partialSum: Long
	): Pair<RangePart, Long> {
		val partRange = getRange(currentPartB, rule.name)
		val isLessThan = rule.operator == "<"
		val newRange = if (isLessThan) {
			adjustRangeForLessThanCondition(partRange, rule.conditionValue)
		} else {
			adjustRangeForMoreThanCondition(partRange, rule.conditionValue)
		}

		var updatedPartialSum = partialSum
		if (!newRange.isEmpty()) {
			val overPart = when (rule.name) {
				"x" -> currentPartB.copy(x = newRange)
				"m" -> currentPartB.copy(m = newRange)
				"s" -> currentPartB.copy(s = newRange)
				"a" -> currentPartB.copy(a = newRange)
				else -> currentPartB
			}
			updatedPartialSum += when (rule.target) {
				"A" -> getProductOfRanges(overPart)
				"R" -> 0L // Assuming this is the intended logic
				else -> evaluateRange(workflowMap, workflowMap[rule.target]!!, overPart, updatedPartialSum)
			}
		}

		val secondaryRange = if (isLessThan) {
			adjustRangeForMoreThanCondition(partRange, rule.conditionValue, true)
		} else {
			adjustRangeForLessThanCondition(partRange, rule.conditionValue, true)
		}

		val updatedPartB = if (!secondaryRange.isEmpty()) {
			when (rule.name) {
				"x" -> currentPartB.copy(x = secondaryRange)
				"m" -> currentPartB.copy(m = secondaryRange)
				"s" -> currentPartB.copy(s = secondaryRange)
				"a" -> currentPartB.copy(a = secondaryRange)
				else -> currentPartB
			}
		} else {
			currentPartB
		}

		return Pair(updatedPartB, updatedPartialSum)
	}

	fun getRange(partB: RangePart, name: String): LongRange {
		return when (name) {
			"x" -> partB.x
			"m" -> partB.m
			"s" -> partB.s
			"a" -> partB.a
			else -> LongRange.EMPTY
		}
	}

	fun getProductOfRanges(overPart: Task19.RangePart): Long {
		return (overPart.x.last - overPart.x.start + 1 ) *
				(overPart.m.last - overPart.m.start + 1) *
				(overPart.a.last - overPart.a.start + 1) *
				(overPart.s.last - overPart.s.start + 1)
	}

	fun adjustRangeForLessThanCondition(range: LongRange, threshold: Int, inverted: Boolean = false): LongRange {
		// If the entire range is below the threshold, return it as is
		if (range.last < threshold) return range

		// If the range starts above the threshold, return an empty range
		if (range.first >= threshold) return LongRange.EMPTY

		// Adjust the range so that it ends at the threshold - 1
		var term = 0
		if (inverted) {
			term += 1
		}

		return range.first until threshold + term
	}

	fun adjustRangeForMoreThanCondition(range: LongRange, threshold: Int, inverted: Boolean = false): LongRange {
		// If the range starts above the threshold, return as is
		if (range.first >= threshold) return range

		if (range.last < threshold) return LongRange.EMPTY

		// Adjust the range so that it starts at the threshold + 1 (or threshold if secondary is true)
		var term = threshold + 1
		if (inverted) {
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