package utils

object Collections {

	fun <T> Collection<T>.forEachPair(action: (T, T) -> Unit) {
		val list = this.toList()
		for (i in list.indices) {
			for (j in i + 1 until list.size) {
				action(list[i], list[j])
			}
		}
	}

}