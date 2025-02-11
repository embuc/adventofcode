package y2022

import Task
import utils.manhattanDistance
import kotlin.math.abs

//--- Day 15: Beacon Exclusion Zone ---
class Task15(val input: List<String>, val targetRow: Int) : Task {

	private data class Sensor(val x: Int, val y: Int, val beaconX:Int, val beaconY:Int, val r: Int)

	override fun a(): Any {
		val sensors = mutableListOf<Sensor>()
		for (line in input) {
			val parts = line.split(" ")
			val x = parts[2].replace("x=", "").replace(",","").toInt()
			val y = parts[3].replace("y=", "").replace(":","").toInt()
			val beaconX = parts[8].replace("x=", "").replace(",","").toInt()
			val beaconY = parts[9].replace("y=", "").toInt()
			val r = manhattanDistance(x to y, beaconX to beaconY)
			val sensor = Sensor(x, y, beaconX, beaconY, r)
//			println(line)
//			println(sensor)
			sensors.add(sensor)
		}

		val forbiddenRanges = mutableListOf<IntRange>()
		for (sensor in sensors) {
			val vDistance = abs(sensor.y - targetRow)
			if (sensor.r >= vDistance) {
				val xMinus = sensor.x - (sensor.r - vDistance)
				val xPlus = sensor.x + (sensor.r - vDistance)
				forbiddenRanges.add(xMinus..xPlus)
			}
		}

		fun mergeRanges(ranges: List<IntRange>): List<IntRange> {
			val sortedRanges = ranges.sortedBy { it.first }
			val merged = mutableListOf<IntRange>()
			var currentRange = sortedRanges[0]
			for (range in sortedRanges.drop(1)) {
				if (range.first <= currentRange.last + 1) {
					// Overlapping or adjacent ranges
					currentRange = minOf(currentRange.first, range.first)..maxOf(currentRange.last, range.last)
				} else {
					merged.add(currentRange)
					currentRange = range
				}
			}
			merged.add(currentRange)
			return merged
		}

		val mergedRanges = mergeRanges(forbiddenRanges)
		val totalForbidden = mergedRanges.sumOf { it.last - it.first + 1 }

		val beaconsOnTargetRow = sensors
			.filter { it.beaconY == targetRow }
			.map { it.beaconX }
			.toSet()

		val adjustedForbidden = totalForbidden - beaconsOnTargetRow.count { x ->
			mergedRanges.any { range -> x in range }
		}

//		var forbidden = mutableSetOf<Int>()
//		for (i in sensors.indices) {
//			val sensor1 = sensors[i]
//			val vDistance = abs(sensor1.y - targetRow)
//			if(sensor1.r >= vDistance) {
//				val xMinus = sensor1.x - (sensor1.r - vDistance)
//				val xPlus = sensor1.x + (sensor1.r - vDistance)
//				for (x in xMinus..xPlus) {
//					forbidden.add(x)
//				}
//			}
//		}
//		return forbidden.size - beaconsOnTargetRow.count { x ->
//			mergedRanges.any { range -> x in range }
//		}
		return adjustedForbidden
	}

	override fun b(): Any {
		return 0
	}
}