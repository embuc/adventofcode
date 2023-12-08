import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.platform.commons.util.ClassLoaderUtils.getLocation

class Uppg5tests {

	@Test
	fun testMappingGroup() {
		// Input: destinationStart = 52, sourceStart = 50, length = 48
		// Expected: Input 70 should be mapped to 81
		val mappingGroup = Uppg5.MappingGroup(52, 50, 48)

		// Check if 70 is within the source range
		assertTrue(mappingGroup.isInSourceRange(79))

		// Check if 70 is correctly mapped to 81
		val mappedValue = mappingGroup.getMappedValue(79)
		assertNotNull(mappedValue)
		assertEquals(81, mappedValue)
	}

	@Test
	fun testInputFile() {
		val seed = 79L
		val location = 82L
		val uppg = Uppg5()
		val allMappings = uppg.parseFileToMappings("Input5Test.txt")
		println("Keys: ${allMappings.keys}")
		println("Values: ${allMappings.values}")
		assertEquals(7, allMappings.size)
		assertEquals(2, allMappings[SEED_TO_SOIL]!!.size)
		assertEquals(location, uppg.getLocation(allMappings, seed))
	}
}


