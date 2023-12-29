package y2023

import Task
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import utils.readInputAsListOfStrings

// --- Day 5: If You Give A Seed A Fertilizer ---
object Task5:Task {
	const val SEED_TO_SOIL = "seed-to-soil"
	const val SOIL_TO_FERTILIZER = "soil-to-fertilizer"
	const val FERTILIZER_TO_WATER = "fertilizer-to-water"
	const val WATER_TO_LIGHT = "water-to-light"
	const val LIGHT_TO_TEMPERATURE = "light-to-temperature"
	const val TEMPERATURE_TO_HUMIDITY = "temperature-to-humidity"
	const val HUMIDITY_TO_LOCATION = "humidity-to-location"

	val seeds: LongArray = longArrayOf(
		515785082L, 87905039L,
		2104518691L, 503149843L,
		720333403L,	385234193L,
		1357904101L, 283386167L,
		93533455L, 128569683L,
		2844655470L, 24994629L,
		3934515023L, 67327818L,
		2655687716L, 8403417L,
		3120497449L, 107756881L,
		4055128129L, 9498708L
	)

	val seedRanges = seeds.toList().windowed(2, 2).map { (start, length) ->
		start..<start + length
	}

	override fun a(): Any {
		val allMappings = parseFileToMappings("2023_5.txt")
		var low = 9999999999L
		for (num in seeds) {
			val loc = getLocation(allMappings, num)
			if (loc < low) {
				low = loc
			}
		}
		return low

	}

	override fun b(): Any {
		return uppg5b()
	}

	fun uppg5b() = runBlocking {
		val allMappings = parseFileToMappings("2023_5.txt")
		val deferredResults = mutableListOf<Deferred<Long>>()

		seedRanges.forEach { range ->
			val deferred = async {
				processRange(range, allMappings)
			}
			deferredResults.add(deferred)
		}

		// Wait for all coroutines to complete and collect their results
		val results = deferredResults.map { it.await() }

		// Do something with the results
		var low = 999999999999L
		results.forEach {
			if (it < low) {
				low = it
			}
		}
		return@runBlocking low
	}

	suspend fun processRange(range: LongRange, allMappings: Map<String, List<MappingGroup>>): Long {
		var low = 999999999999L
		for (value in range) {
			val loc = getLocation(allMappings, value)
			if (loc < low) {
				low = loc
			}
		}
		return low
	}

	fun getLocation(allMappings: Map<String, List<MappingGroup>>, number: Long): Long {
		var mappedValue = number;
		mappedValue = transformForMappingType(allMappings[SEED_TO_SOIL]!!, mappedValue)
		mappedValue = transformForMappingType(allMappings[SOIL_TO_FERTILIZER]!!, mappedValue)
		mappedValue = transformForMappingType(allMappings[FERTILIZER_TO_WATER]!!, mappedValue)
		mappedValue = transformForMappingType(allMappings[WATER_TO_LIGHT]!!, mappedValue)
		mappedValue = transformForMappingType(allMappings[LIGHT_TO_TEMPERATURE]!!, mappedValue)
		mappedValue = transformForMappingType(allMappings[TEMPERATURE_TO_HUMIDITY]!!, mappedValue)
		return transformForMappingType(allMappings[HUMIDITY_TO_LOCATION]!!, mappedValue)
	}

	private fun transformForMappingType(mappings: List<MappingGroup>, number: Long): Long {
		for (mappingGroup in mappings) {
			if (mappingGroup.isInSourceRange(number)) {
				return mappingGroup.getMappedValue(number)
			}
		}
		return number;
	}

	class MappingGroup(destinationStart: Long, sourceStart: Long, length: Long) {
		val source: LongRange = sourceStart..<(sourceStart + length)
		val destination: LongRange = destinationStart..<(destinationStart + length)

		fun isInSourceRange(number: Long): Boolean {
			return number in source
		}

		fun getMappedValue(number: Long): Long {
			val offset = number - source.first
			return destination.first + offset
		}
		override fun toString(): String {
			return "MappingGroup(source=$source, destination=$destination) length: ${source.last - source.first}"
		}
	}

	fun parseFileToMappings(fileName: String): Map<String, List<MappingGroup>> {
		val lines = readInputAsListOfStrings(fileName)
		val result = mutableMapOf<String, List<MappingGroup>>()
		var currentSection = ""
		var mappings = mutableListOf<MappingGroup>()

		for (line in lines) {
			if (line.endsWith(":")) {
				if (currentSection.isNotEmpty()) {
					result[currentSection.removeSuffix(" map")] = mappings.toList()
					mappings = mutableListOf()
				}
				currentSection = line.removeSuffix(":")
			} else {
				val parts = line.split(" ").mapNotNull { it.toLongOrNull() }
				if (parts.size == 3) {
					mappings.add(MappingGroup(parts[0], parts[1], parts[2]))
				}
			}
		}

		// Add the last section
		if (currentSection.isNotEmpty() && mappings.isNotEmpty()) {
			result[currentSection.removeSuffix(" map")] = mappings.toList()
		}

		return result
	}

}