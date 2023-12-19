package y2023

import Task
import utils.readInput

object Task19 : Task {

	override fun a(): Any {
		val input = readInput("2023_19.txt")
		return solveA(input)
	}

	override fun b(): Any {
		TODO("Not yet implemented")
	}

	data class Workflow(val name: String, val rules: ArrayList<Rule>, val nextOrEndTarget: String)
	data class Rule(val name: String, val condition: (Int) -> Boolean, val target: String)
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

	fun evaluatePart(
		workflowMap: Map<String, Task19.Workflow>,
		currentWorkflow: Task19.Workflow,
		currentPart: Task19.Part
	): Long {
		var target = ""
		for (rule in currentWorkflow.rules) {
			target = ""
			when (rule.name) {
				"x" -> if (rule.condition(currentPart.x.toInt())) {
					target = rule.target
				}
				"m" -> if (rule.condition(currentPart.m.toInt())) {
					target = rule.target
				}
				"a" -> if (rule.condition(currentPart.a.toInt())) {
					target = rule.target
				}
				"s" -> if (rule.condition(currentPart.s.toInt())) {
					target = rule.target
				}
			}
			if (target == "A") {
				break
			} else if (target == "R") {
				break
			} else if (target == "") {
				//SKIP
			} else {
				break
			}
		}
		if (target == "A") {
			return currentPart.a + currentPart.s + currentPart.m + currentPart.x
		} else if (target == "R") {
			return 0
		} else if (target == "") {
			// do nothing
		} else {
			return evaluatePart(workflowMap, workflowMap[target]!!, currentPart)
		}


		when (currentWorkflow.nextOrEndTarget) {
			"A" -> {
				return currentPart.a + currentPart.s + currentPart.m + currentPart.x
			}
			"R" -> {
				return 0
			}
		}
		return evaluatePart(workflowMap, workflowMap[currentWorkflow.nextOrEndTarget]!!, currentPart)
	}

	fun parseInput(input: String): Pair<List<Task19.Workflow>, List<Task19.Part>> {
		val inputParts = input.split("\n\n");
		val workflows = inputParts[0].lines().map { s -> parseWorkflow(s) }
		val parts = inputParts[1].lines().map { s -> parseParts(s) }
		return Pair(workflows, parts)
	}

	fun parseParts(s: String): Task19.Part {
		val pattern = Regex("\\{x=(\\d+),m=(\\d+),a=(\\d+),s=(\\d+)}")
		val matchResult = pattern.matchEntire(s)!!
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

		return Task19.Rule(key, condition, target)
	}

}