package y2016

import Task

//--- Day 10: Balance Bots ---
class Task10(val input: List<String>, private val lowVal:Int, private val highVal:Int):Task {

	data class Bot(val id: String) {
		val chips = mutableListOf<Int>()
		var low: Int? = null
		var high: Int? = null
		fun addChip(chip: Int) {
			chips.add(chip)
			if (chips.size == 2) {
				val (low, high) = chips.sorted()
				this.low = low
				this.high = high
			}
		}
	}
	private val bots = mutableMapOf<String, Bot>()
	private val outputs = mutableMapOf<String, Int>()
	private data class Instruction(val bot: String, val lowType: String, val low: String, val highType :String, val high: String)
	private val instructions = mutableListOf<Instruction>()

	override fun a(): Any {
		for (line in input) {
			val parts = line.split(" ")
			if (parts[0] == "value") {
				val value = parts[1].toInt()
				val bot = parts[5]
				bots.getOrPut(bot) { Bot(bot) }.addChip(value)
			} else {
				val bot = parts[1]
				val lowType = parts[5]
				val low = parts[6]
				val highType = parts[10]
				val high = parts[11]
				instructions.add(Instruction(bot, lowType, low, highType , high))
			}
		}
		var iterator = instructions.iterator()
		while(iterator.hasNext()) {
			val (bot, lowType, low, highType,  high) = iterator.next()
			val lowValue = bots[bot]?.low
			val highValue = bots[bot]?.high
			if(lowValue == null || highValue == null) {
				if(!iterator.hasNext()) {
					iterator = instructions.iterator()
				}
				continue
			}
			if(bots[bot]?.chips?.contains(lowVal) == true && bots[bot]?.chips?.contains(highVal) == true) {
				println("bot $bot compares $lowValue and $highValue")
				println("bot $bot compares $lowVal and $highVal")
				return bot
			}

			if(lowType == "bot") {
				bots.getOrPut(low) { Bot(low) }.addChip(lowValue)
				if(bots[low]?.chips?.contains(lowVal) == true && bots[low]?.chips?.contains(highVal) == true) {
					println("bot $low compares $lowValue and $highValue")
					println("bot $low compares $lowVal and $highVal")
					return low
				}
			}else {
				outputs[low] = lowValue
			}
			if(highType == "bot") {
				bots.getOrPut(high) { Bot(high) }.addChip(highValue)
				if(bots[high]?.chips?.contains(lowVal) == true && bots[high]?.chips?.contains(highVal) == true) {
					println("bot $high compares $lowValue and $highValue")
					println("bot $high compares $lowVal and $highVal")
					return high
				}
			}else {
				outputs[high] = highValue
			}
			bots[bot]?.chips?.clear()
			bots[bot]?.low = null
			bots[bot]?.high = null
			iterator.remove()
			if(!iterator.hasNext()) {
				iterator = instructions.iterator()
			}
		}
		return "-1"
	}

	override fun b(): Any {
		for (line in input) {
			val parts = line.split(" ")
			if (parts[0] == "value") {
				val value = parts[1].toInt()
				val bot = parts[5]
				bots.getOrPut(bot) { Bot(bot) }.addChip(value)
			} else {
				val bot = parts[1]
				val lowType = parts[5]
				val low = parts[6]
				val highType = parts[10]
				val high = parts[11]
				instructions.add(Instruction(bot, lowType, low, highType , high))
			}
		}
		var iterator = instructions.iterator()
		while(iterator.hasNext()) {
			val (bot, lowType, low, highType,  high) = iterator.next()
			val lowValue = bots[bot]?.low
			val highValue = bots[bot]?.high
			if(lowValue == null || highValue == null) {
				if(!iterator.hasNext()) {
					iterator = instructions.iterator()
				}
				continue
			}
			if(bots[bot]?.chips?.contains(lowVal) == true && bots[bot]?.chips?.contains(highVal) == true) {
				println("bot $bot compares $lowValue and $highValue")
				println("bot $bot compares $lowVal and $highVal")
//				return bot
			}

			if(lowType == "bot") {
				bots.getOrPut(low) { Bot(low) }.addChip(lowValue)
				if(bots[low]?.chips?.contains(lowVal) == true && bots[low]?.chips?.contains(highVal) == true) {
					println("bot $low compares $lowValue and $highValue")
					println("bot $low compares $lowVal and $highVal")
//					return low
				}
			}else {
				outputs[low] = lowValue
			}
			if(highType == "bot") {
				bots.getOrPut(high) { Bot(high) }.addChip(highValue)
				if(bots[high]?.chips?.contains(lowVal) == true && bots[high]?.chips?.contains(highVal) == true) {
					println("bot $high compares $lowValue and $highValue")
					println("bot $high compares $lowVal and $highVal")
//					return high
				}
			}else {
				outputs[high] = highValue
			}
			bots[bot]?.chips?.clear()
			bots[bot]?.low = null
			bots[bot]?.high = null
			iterator.remove()
			if(!iterator.hasNext()) {
				iterator = instructions.iterator()
			}
		}
		return outputs["0"].toString().toLong() * outputs["1"]!!.toLong() * outputs["2"]!!.toLong()
	}
}