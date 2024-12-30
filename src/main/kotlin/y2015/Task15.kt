package y2015

import Task

//--- Day 15: Science for Hungry People ---
class Task15(val input: List<String>) : Task {

	data class Ingredient(val name: String, val capacity: Int, val durability: Int, val flavor: Int, val texture: Int, val calories: Int)

	override fun a(): Any {
		//parse input and calculate
		val ingredientsMap = mutableMapOf<String, Ingredient>()
		for (line in input) {
			val parts = line.split(":")
			val name = parts[0].trim()
			val properties = parts[1].trim().split(",")
			val capacity = properties[0].trim().removePrefix("capacity ").toInt()
			val durability = properties[1].trim().removePrefix("durability ").toInt()
			val flavor = properties[2].trim().removePrefix("flavor ").toInt()
			val texture = properties[3].trim().removePrefix("texture ").toInt()
			val calories = properties[4].trim().removePrefix("calories ").toInt()
			ingredientsMap[name] = Ingredient(name, capacity, durability, flavor, texture, calories)
		}
		// explore different values using backtracking and dividing to the total of 100 ingredients
		val ingredients = ingredientsMap.values.toTypedArray()
		val distribution = IntArray(ingredients.size) { 0 }
		val maxRecipeValue = mutableListOf(0)
		findRecipes(ingredients, distribution, 100, 0, maxRecipeValue)
		return maxRecipeValue[0]
	}

	fun calculateRecipeValue(ingredients: Array<Ingredient>, distribution: IntArray): Int {
		var capacity = 0
		var durability = 0
		var flavor = 0
		var texture = 0
		var calories = 0
		for (i in ingredients.indices) {
			capacity += ingredients[i].capacity * distribution[i]
			durability += ingredients[i].durability * distribution[i]
			flavor += ingredients[i].flavor * distribution[i]
			texture += ingredients[i].texture * distribution[i]
//			calories += ingredients[i].calories * distribution[i]
		}
		if (capacity < 0 || durability < 0 || flavor < 0 || texture < 0) {
			return 0
		}
		return capacity * durability * flavor * texture
	}

	fun findRecipes(
		ingredients: Array<Ingredient>,
		distribution: IntArray,
		current: Int,
		i: Int,
		maxRecipeValue: MutableList<Int>
	): Unit {
		if (i == ingredients.size) { // Base case, where we check and see if we can calculate the value, if current == 0
			if (current == 0) {
				val recipeValue = calculateRecipeValue(ingredients, distribution)
				if (recipeValue > maxRecipeValue[0]) {
					maxRecipeValue[0] = recipeValue
				}
			}
			return
		}

		for (tokensToAdd in 0..current) {
			distribution[i] += tokensToAdd // This "tries" adding tokens
			findRecipes(ingredients, distribution, current - tokensToAdd, i + 1, maxRecipeValue)
			distribution[i] -= tokensToAdd // Undo the "try"
		}

	}

	override fun b(): Any {
		TODO("Not yet implemented")
	}
}