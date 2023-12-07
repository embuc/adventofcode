val seeds: LongArray = longArrayOf(
	515785082L, 87905039L, 2104518691L, 503149843L, 720333403L,
	385234193L, 1357904101L, 283386167L, 93533455L, 128569683L,
	2844655470L, 24994629L, 3934515023L, 67327818L, 2655687716L,
	8403417L, 3120497449L, 107756881L, 4055128129L, 9498708L
)

const val SEED_TO_SOIL = "seed-to-soil"
const val SOIL_TO_FERTILIZER = "soil-to-fertilizer"
const val FERTILIZER_TO_WATER = "fertilizer-to-water"
const val WATER_TO_LIGHT = "water-to-light"
const val LIGHT_TO_TEMPERATURE = "light-to-temperature"
const val TEMPERATURE_TO_HUMIDITY = "temperature-to-humidity"
const val HUMIDITY_TO_LOCATION = "humidity-to-location"


fun main(args: Array<String>) {
	uppg5a();
}

fun uppg5a() {
	val allMappings = parseFileToMappings("Input5.txt")
	println(allMappings)
	var low = 9999999999L
	for (num in seeds) {
		val loc = getLocation(allMappings, num)
		if (loc < low) {
			low = loc
		}
		println("Seed: $num, Location: $loc")
	}
	print ("Lowest location: $low")
}

fun getLocation(allMappings: Map<String, List<MappingGroup>>, number: Long): Long {
	var mappedValue = number;
	println(allMappings[SEED_TO_SOIL])
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
		println(source.first)
		println("offset: $offset")
		println(destination.first)
		return destination.first + offset
	}
	override fun toString(): String {
		return "MappingGroup(source=$source, destination=$destination) length: ${source.last - source.first}"
	}
}

fun parseFileToMappings(fileName: String): Map<String, List<MappingGroup>> {
	val lines = getLinesFromFile(fileName)
	val result = mutableMapOf<String, List<MappingGroup>>()
	var currentSection = ""
	var mappings = mutableListOf<MappingGroup>()

	for (line in lines) {
		if (line.endsWith(":")) {
			if (currentSection.isNotEmpty()) {
//				result[currentSection] = mappings.toList()
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

private fun getLinesFromFile(fileName: String): List<String> {
	val inputStream = Thread.currentThread().contextClassLoader.getResourceAsStream(fileName)
		?: throw IllegalArgumentException("File not found")
	val lines = inputStream.bufferedReader().use { it.readLines() }
	return lines
}