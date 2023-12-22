package utils

fun Collection<Long>.lcm() = reduce { a, b -> lcm(a, b) }

fun lcm(a: Long, b: Long): Long = (a * b) / gcd(a, b)

fun gcd(a: Long, b: Long): Long = if (a == 0L) b else gcd(b % a, a)

fun IntRange.size() = last - start + 1