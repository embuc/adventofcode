package y2016

import Task
import java.security.MessageDigest
import java.util.concurrent.ConcurrentHashMap

//--- Day 14: One-Time Pad ---
class Task14(val input: String) : Task {

	private data class Candidate(val index: Int, val triple: Char, var iteration: Int)

	// Lookup table for byte to hex chars conversion
	private val hexChars = "0123456789abcdef".toCharArray()
	private val hexBuffer = CharArray(32)  // Reusable buffer for hex conversion
	private val md: MessageDigest = MessageDigest.getInstance("MD5")

	private fun bytesToHexChars(bytes: ByteArray) {
		var j = 0
		for (byte in bytes) {
			hexBuffer[j++] = hexChars[(byte.toInt() and 0xF0) ushr 4]
			hexBuffer[j++] = hexChars[byte.toInt() and 0x0F]
		}
	}

	private fun findTripleInHexBuffer(): Char? {
		for (i in 0..29) {  // 32 - 3
			if (hexBuffer[i] == hexBuffer[i + 1] &&
				hexBuffer[i] == hexBuffer[i + 2]
			) {
				return hexBuffer[i]
			}
		}
		return null
	}

	private fun findQuintupleInHexBuffer(): Char? {
		for (i in 0..27) {  // 32 - 5
			if (hexBuffer[i] == hexBuffer[i + 1] &&
				hexBuffer[i] == hexBuffer[i + 2] &&
				hexBuffer[i] == hexBuffer[i + 3] &&
				hexBuffer[i] == hexBuffer[i + 4]
			) {
				return hexBuffer[i]
			}
		}
		return null
	}

	override fun a(): Any {
		val candidates = mutableListOf<Candidate>()

		var index = 0
		val keys = mutableListOf<Int>()
		val saltArray = input.toByteArray()

		while (keys.size < 64) {
			val indexArray = index.toString().toByteArray()
			val array = saltArray.plus(indexArray)
			val hashArray = md.digest(array)

			bytesToHexChars(hashArray)

			candidates.forEach { it.iteration++ }

			findTripleInHexBuffer()?.let { triple ->
				candidates.add(Candidate(index, triple, 0))
			}

			findQuintupleInHexBuffer()?.let { quintuple ->
				candidates.removeIf { candidate ->
					if (candidate.triple == quintuple &&
						candidate.iteration < 1000 &&
						candidate.index < index
					) {
						keys.add(candidate.index)
						true
					} else {
						false
					}
				}
			}

			candidates.removeIf { it.iteration >= 1000 }
			index++
		}

		return keys.sorted()[63]
	}

	private val hashCache = ConcurrentHashMap<ByteArray, ByteArray>()
	override fun b(): Any {
		val candidates = mutableListOf<Candidate>()

		var index = 0
		val keys = mutableListOf<Int>()
		val saltArray = input.toByteArray()

		while (keys.size < 64) {
			val indexArray = index.toString().toByteArray()
			val array = saltArray.plus(indexArray)

			val hashArray = getFinalHash(array)

			bytesToHexChars(hashArray)

			candidates.forEach { it.iteration++ }

			findTripleInHexBuffer()?.let { triple ->
				candidates.add(Candidate(index, triple, 0))
			}

			findQuintupleInHexBuffer()?.let { quintuple ->
				candidates.removeIf { candidate ->
					if (candidate.triple == quintuple &&
						candidate.iteration < 1000 &&
						candidate.index < index
					) {
						keys.add(candidate.index)
						true
					} else {
						false
					}
				}
			}

			candidates.removeIf { it.iteration >= 1000 }
			index++
		}

		return keys.sorted()[63]
	}

	private fun getFinalHash(input: ByteArray): ByteArray {
		return hashCache.computeIfAbsent(input) {
			var hashArray = md.digest(it)
			val nextInput = ByteArray(hashArray.size * 2)
			for (i in 0 until 2016) {
				for (j in hashArray.indices) {
					nextInput[j*2] = ((hashArray[j].toInt() shr 4 and 0x0F)).toByte()
					nextInput[j*2+1] = (hashArray[j].toInt() and 0x0F).toByte()
				}
				hashArray = md.digest(toHexBytes(nextInput))
			}
			hashArray
		}
	}
	private fun toHexBytes(bytes: ByteArray): ByteArray {
		val result = ByteArray(bytes.size)
		for(i in bytes.indices){
			val value = (bytes[i].toInt() and 0xFF)
			if (value < 10) {
				result[i] = ('0'.code + value).toByte()
			} else {
				result[i] = ('a'.code + value - 10).toByte()
			}

		}
		return result
	}

}


// -------------------------------------------------------------------------------------------------
//bellow solution is a bit slower than the one above (~5x) but reads the best
//	private data class Candidate(val index: Int, val hash: String, val triple: Char, var iteration: Int)
//
//	override fun a(): Any {
//		val candidates = mutableListOf<Candidate>()
//		val md = MessageDigest.getInstance("MD5")
//		var index = 0
//		val keys = mutableListOf<Int>()
//
//		while (keys.size < 64 && index < 100000) {  // Changed condition to find 64 keys
//			val hash = generateHash(md, input + index.toString())
//
//			// Increment all candidates by 1
//			candidates.forEach { it.iteration++ }
//
//			// Check for triple and add to candidates
//			val triple = hash.findTriple()
//			if (triple != null) {
//				candidates.add(Candidate(index, hash, triple, 0))
//			}
//
//			// Check for quintuple and validate candidates
//			val quintuple = hash.findQuintuple()
//			if (quintuple != null) {
//				candidates.removeIf { candidate ->
//					if (candidate.triple == quintuple && candidate.iteration < 1000 && candidate.index < index) {
//						keys.add(candidate.index)
//						return@removeIf true
//					}
//					false
//				}
//			}
//
//			// Remove expired candidates
//			candidates.removeIf { it.iteration >= 1000 }
//
//			index++
//		}
//
//		return keys.sorted()[63]  // Return the 64th key
//	}
//
//	private fun generateHash(md: MessageDigest, input: String): String {
//		val bytes = md.digest(input.toByteArray())
//		return bytes.joinToString("") { "%02x".format(it) }
//	}
//private fun String.findQuintuple(): Char? {
//	for (i in 0..length - 5) {
//		if (this[i] == this[i + 1] &&
//			this[i] == this[i + 2] &&
//			this[i] == this[i + 3] &&
//			this[i] == this[i + 4]) {
//			return this[i]
//		}
//	}
//	return null
//}
//
//private fun String.findTriple(): Char? {
//	for (i in 0..length - 3) {
//		if (this[i] == this[i + 1] && this[i] == this[i + 2]) {
//			return this[i]
//		}
//	}
//	return null
//}


// -------------------------------------------------------------------------------------------------

// I tryed to implement the solution with ByteArray but it was actually a bit slower(~50%)
//	private data class Candidate(val index: Int, val triple: Int, var iteration: Int)
//
//	private fun ByteArray.findTripleNibble(): Int? {
//		// Look through each byte except the last one (we need room for matching across bytes)
//		for (i in 0 until size - 1) {
//			// Get all possible nibbles from current and next byte
//			val nibbles = getNibblesAtPosition(i)
//
//			// Check each possible starting position for a triple
//			for (j in 0..nibbles.size - 3) {
//				if (nibbles[j] == nibbles[j + 1] && nibbles[j] == nibbles[j + 2]) {
//					return nibbles[j]
//				}
//			}
//		}
//		return null
//	}
//
//	// Helper function to get all nibbles at a byte position
//	private fun ByteArray.getNibblesAtPosition(i: Int): List<Int> {
//		val currentByte = this[i].toInt()
//		val nextByte = this[i + 1].toInt()
//
//		return listOf(
//			(currentByte and 0xF0) ushr 4,  // Upper nibble of current byte
//			currentByte and 0x0F,           // Lower nibble of current byte
//			(nextByte and 0xF0) ushr 4,     // Upper nibble of next byte
//			nextByte and 0x0F               // Lower nibble of next byte
//		)
//	}
//
//	private fun ByteArray.findQuintupleNibble(): Int? {
//		// Look through bytes, need room for 5 nibbles (3 bytes is enough)
//		for (i in 0 until size - 2) {
//			val nibbles = getNibblesAtPosition(i)
//			// Add one more byte worth of nibbles
//			val nextNextByte = this[i + 2].toInt()
//			val extendedNibbles = nibbles + listOf(
//				(nextNextByte and 0xF0) ushr 4,  // Upper nibble of next next byte
//				nextNextByte and 0x0F            // Lower nibble of next next byte
//			)
//
//			// Check each possible starting position for a quintuple
//			for (j in 0..extendedNibbles.size - 5) {
//				val target = extendedNibbles[j]
//				if (extendedNibbles.subList(j, j + 5).all { it == target }) {
//					return target
//				}
//			}
//		}
//		return null
//	}
//
//	override fun a(): Any {
//		val candidates = mutableListOf<Candidate>()
//		val md = MessageDigest.getInstance("MD5")
//		var index = 0
//		val keys = mutableListOf<Int>()
//		val saltArray = input.toByteArray()
//
//		while (keys.size < 64) {
//			// Generate MD5 hash
//			val hashArray = md.digest(saltArray + index.toString().toByteArray())
//
//			// Increment iteration counter for all candidates
//			candidates.forEach { it.iteration++ }
//
//			// Look for new triples
//			hashArray.findTripleNibble()?.let { triple ->
//				candidates.add(Candidate(index, triple, 0))
//			}
//
//			// Check for quintuples that validate existing candidates
//			hashArray.findQuintupleNibble()?.let { quintuple ->
//				candidates.removeIf { candidate ->
//					if (candidate.triple == quintuple &&
//						candidate.iteration < 1000 &&
//						candidate.index < index) {
//						keys.add(candidate.index)
//						true
//					} else {
//						false
//					}
//				}
//			}
//
//			// Remove expired candidates
//			candidates.removeIf { it.iteration >= 1000 }
//
//			index++
//		}
//
//		return keys.sorted()[63]
//	}


