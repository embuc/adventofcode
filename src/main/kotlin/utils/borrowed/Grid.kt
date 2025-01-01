package utils.borrowed

data class Grid<T>(val initialRows: Int, val initialColumns: Int){
	constructor() : this(0, 0)

	var data = mutableMapOf<Point, T>()
	val rows: Int get() = maxX - minX + 1
	val columns: Int get() = maxY - minY + 1
	private val minX: Int get() = data.keys.minOfOrNull { it.x } ?: 0
	private val minY: Int get() = data.keys.minOfOrNull { it.y } ?: 0
	private val maxX: Int get() = data.keys.maxOfOrNull { it.x } ?: 0
	private val maxY: Int get() = data.keys.maxOfOrNull { it.y } ?: 0

	operator fun get(point: Point): T? = data[point]
	operator fun get(value: T): Point? = data.entries.find { it.value == value }?.key

	companion object {
		@JvmName("ofStringList")
		fun of(rows: List<String>): Grid<Char> {
			val data = rows.flatMapIndexed { rowIndex, row ->
				row.mapIndexed { colIndex, element ->
					Point(rowIndex, colIndex) to element
				}
			}.filter { it.second != ' ' }.toMap().toMutableMap()
			return Grid<Char>().apply {
				this.data = data

			}
		}
	}
}