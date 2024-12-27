package y2015

import Task
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode

//--- Day 12: JSAbacusFramework.io ---
class Task12(val input: String) : Task {

	override fun a(): Any {
		val regex = Regex("-?\\d+")
		val numbers = regex.findAll(input).map { it.value.toInt() }.toList()
		return numbers.sum()
	}

	override fun b(): Any {
		val mapper = ObjectMapper()
		val tree = mapper.readTree(input)
		fun read(node: JsonNode): Int {
			var sum = 0
			if (node.isObject) {
				val objectNode = node as ObjectNode
				val hasRed = objectNode.fields().asSequence().any { it.value.isTextual && it.value.asText() == "red" }
				if (hasRed) {
					return 0
				} else {
					for (f in objectNode.fields()) {
						sum += read(f.value)
					}
				}
			} else if (node.isArray) {
				val arrayNode = node as ArrayNode
				for (a in arrayNode) {
					sum += read(a)
				}
			} else if (node.isInt) {
				sum += node.asInt()
			}
			return sum
		}

		return read(tree)
	}
}