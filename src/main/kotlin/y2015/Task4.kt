package y2015

import Task
import java.security.MessageDigest
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

/*--- Day 4: The Ideal Stocking Stuffer ---*/
class Task4(val input: String) : Task {

	override fun a(): Any {
		val md = MessageDigest.getInstance("MD5")
		for (i in 0..1_000_000) {
			val hash = md.digest("$input$i".toByteArray()).joinToString("") { "%02x".format(it) }
			if (hash.startsWith("00000")) {
				return i
			}
		}
		return 0
	}

	override fun b(): Any {
//		return simpleAndClearB();
		return parallelB();
	}

	private fun parallelB(): Any {
		val numThreads = Runtime.getRuntime().availableProcessors()
		val chunkSize = 200_000
		val result = AtomicInteger(-1)
		val shouldStop = AtomicBoolean(false)
		val threads = mutableListOf<Thread>()
		// Split range into chunks for each thread
		var continuing = true
		while (continuing) {
			for (startRange in 0..10_000_000 step (chunkSize * numThreads)) {
				for (threadId in 0 until numThreads) {
					val threadStart = startRange + (threadId * chunkSize)
					val threadEnd = minOf(threadStart + chunkSize, 10_000_000 + 1)

					val thread = Thread {
						val md = MessageDigest.getInstance("MD5")
						var hashCount = 0L

						for (i in threadStart until threadEnd) {
							if (shouldStop.get()) break

							val hash = md.digest("$input$i".toByteArray())
								.joinToString("") { "%02x".format(it) }
							hashCount++

							if (hash.startsWith("000000")) {
								result.compareAndSet(-1, i)
								shouldStop.set(true)
//								println("Thread $threadId found result after $hashCount hashes")
								break
							}
						}
//						println("Thread $threadId processed $hashCount hashes")
					}
					threads.add(thread)
					thread.start()
				}

				// Wait for current batch of threads to complete before starting next batch
				threads.forEach { it.join() }
				threads.clear()

				// If we found a result, stop processing more chunks
				if (shouldStop.get()) {
					continuing = false
					break
				}
			}
		}
		return if (result.get() == -1) 0 else result.get()
	}

	private fun simpleAndClearB(): Any {
		val md = MessageDigest.getInstance("MD5")
		for (i in 0..10_000_000) {
			val hash = md.digest("$input$i".toByteArray()).joinToString("") { "%02x".format(it) }
			if (hash.startsWith("000000")) {
				return i
			}
		}
		return 0
	}
}