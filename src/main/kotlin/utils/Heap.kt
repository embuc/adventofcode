package utils

fun printCurrentHeapSize() {
	val runtime = Runtime.getRuntime()
	val totalMemory = runtime.totalMemory() // total memory currently in the Java Virtual Machine
	val freeMemory = runtime.freeMemory() // an approximation to the total amount of memory currently available for future allocated objects
	val usedMemory = totalMemory - freeMemory

	println("Total Memory: ${totalMemory / 1024 / 1024} MB Free Memory: ${freeMemory / 1024 / 1024} MB Used Memory: ${usedMemory / 1024 / 1024} MB")
}