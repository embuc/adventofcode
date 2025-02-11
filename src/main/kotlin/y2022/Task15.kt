package y2022

import Task
import utils.manhattanDistance
import kotlin.math.abs

//--- Day 15: Beacon Exclusion Zone ---
class Task15(val input: List<String>, val targetRow: Int) : Task {

	data class Sensor(val x: Int, val y: Int, val beaconX:Int, val beaconY:Int, val r: Int)

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

		return adjustedForbidden
	}

	fun findDistressBeacon(sensors: List<Sensor>, searchSpace: Int): Pair<Int, Int> {
		for (sensor in sensors) {
			val r = sensor.r
			// Generate candidate positions along the boundaries
			for (dx in -r - 1..r + 1) {
				val x = sensor.x + dx
				val dy = r + 1 - abs(dx)
				val y1 = sensor.y + dy
				val y2 = sensor.y - dy
				// Check if (x, y1) is within the search space and not covered by any sensor
				if (x in 0..searchSpace && y1 in 0..searchSpace && !isCovered(x, y1, sensors)) {
					return x to y1
				}
				// Check if (x, y2) is within the search space and not covered by any sensor
				if (x in 0..searchSpace && y2 in 0..searchSpace && !isCovered(x, y2, sensors)) {
					return x to y2
				}
			}
		}
		throw IllegalStateException("Distress beacon not found")
	}

	fun isCovered(x: Int, y: Int, sensors: List<Sensor>): Boolean {
		for (sensor in sensors) {
			if (abs(x - sensor.x) + abs(y - sensor.y) <= sensor.r) {
				return true
			}
		}
		return false
	}

	fun computeTuningFrequency(x: Int, y: Int): Long {
		return x * 4_000_000L + y
	}
	override fun b(): Any {
		val sensors = mutableListOf<Sensor>()
		for (line in input) {
			val parts = line.split(" ")
			val x = parts[2].replace("x=", "").replace(",","").toInt()
			val y = parts[3].replace("y=", "").replace(":","").toInt()
			val beaconX = parts[8].replace("x=", "").replace(",","").toInt()
			val beaconY = parts[9].replace("y=", "").toInt()
			val r = manhattanDistance(x to y, beaconX to beaconY)
			val sensor = Sensor(x, y, beaconX, beaconY, r)
			sensors.add(sensor)
		}

		val distressBeacon = findDistressBeacon(sensors, 4_000_000)
		val tuningFrequency = computeTuningFrequency(distressBeacon.first, distressBeacon.second)
		println("Tuning Frequency: $tuningFrequency")
		return tuningFrequency
	}
}