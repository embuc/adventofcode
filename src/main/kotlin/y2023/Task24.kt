package y2023

import Task
import utils.Collections.forEachPair
import utils.Line
import utils.PointDouble
import utils.Utils.die
import utils.Utils.rl
import java.io.File
import com.microsoft.z3.Context
import com.microsoft.z3.Status

class Task24(private val input: List<String>, private val minBoundary: Double, private val maxBoundary: Double) : Task {

	override fun a(): Any {
		val lines = parseInput(input)
		val intersections = findValidIntersections(lines);
		return intersections.size
	}

	fun parseInput(input: List<String>): List<Line> {
		val lines = mutableListOf<Line>()
		for (line in input) {
//			19, 13, 30 @ -2,  1, -2
			val split = line.split("@")
			val point = split[0].trim().split(",").map { it.trim().toDouble() }
			val velocity = split[1].trim().split(",").map { it.trim().toDouble() }
			lines.add(
				Line(
					PointDouble(point[0], point[1]),
					PointDouble(velocity[0], velocity[1]),
					line
				)
			)
		}
		return lines
	}

	private fun findValidIntersections(lines: List<Line>): List<PointDouble> {
		val intersections = mutableListOf<PointDouble>()
		lines.forEachPair() { line1, line2 ->
			val intersection = line1.intersectsWith(line2)
			if (intersection != null) {
				if (insideBoundary(intersection, line1, line2)) {
//					println("Intersection: $intersection between ${line1.name} and ${line2.name}")
					intersections.add(intersection)
				}
			}
		}
		return intersections
	}

	private fun insideBoundary(intersection: PointDouble, line1: Line, line2: Line): Boolean {
		return intersection.x in minBoundary..maxBoundary && intersection.y in minBoundary..maxBoundary &&
				(if (line1.velocity.x <= 0) intersection.x <= line1.point.x else intersection.x > line1.point.x) &&
				(if (line1.velocity.y <= 0) intersection.y <= line1.point.y else intersection.y > line1.point.y) &&
				(if (line2.velocity.x <= 0) intersection.x <= line2.point.x else intersection.x > line2.point.x) &&
				(if (line2.velocity.y <= 0) intersection.y <= line2.point.y else intersection.y > line2.point.y)
	}


	override fun b(): Any {
		return -1
	}

	fun solvePart2(input: File): Any {
		val hail = input.rl().map { it.split(" @ ", ", ").map { it.trim().toLong() } }
		val ctx = Context()
		val solver = ctx.mkSolver()
		val mx = ctx.mkRealConst("mx")
		val m = ctx.mkRealConst("m")
		val mz = ctx.mkRealConst("mz")
		val mxv = ctx.mkRealConst("mxv")
		val mv = ctx.mkRealConst("mv")
		val mzv = ctx.mkRealConst("mzv")
		repeat(3) {
			val (sx, sy, sz, sxv, syv, szv) = hail[it]
			val t = ctx.mkRealConst("t$it")
			solver.add(ctx.mkEq(ctx.mkAdd(mx, ctx.mkMul(mxv, t)), ctx.mkAdd(ctx.mkReal(sx.toString()), ctx.mkMul(ctx.mkReal(sxv.toString()), t))))
			solver.add(ctx.mkEq(ctx.mkAdd(m, ctx.mkMul(mv, t)), ctx.mkAdd(ctx.mkReal(sy.toString()), ctx.mkMul(ctx.mkReal(syv.toString()), t))))
			solver.add(ctx.mkEq(ctx.mkAdd(mz, ctx.mkMul(mzv, t)), ctx.mkAdd(ctx.mkReal(sz.toString()), ctx.mkMul(ctx.mkReal(szv.toString()), t))))
		}
		if (solver.check() == Status.SATISFIABLE) {
			val model = solver.model
			val solution = listOf(mx, m, mz).sumOf { model.eval(it, false).toString().toDouble() }
			return solution.toLong()
		}
		die()
	}
}