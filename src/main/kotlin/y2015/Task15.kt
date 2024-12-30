package y2015

import Task

//--- Day 15: Science for Hungry People ---
class Task15(val input: List<String>) : Task {

	data class Ingredient(val name: String, val capacity: Int, val durability: Int, val flavor: Int, val texture: Int, val calories: Int)

	override fun a(): Any {
		val ingredients = getIngredients()
		val distribution = IntArray(ingredients.size) { 0 }
		val maxRecipeValue = mutableListOf(0)
		findRecipes(ingredients, distribution, 100, 0, maxRecipeValue, false)
		return maxRecipeValue[0]
	}

	private fun getIngredients(): Array<Ingredient> {
		val ingredients = mutableListOf<Ingredient>()
		for (line in input) {
			val parts = line.split(":")
			val name = parts[0].trim()
			val properties = parts[1].trim().split(",")
			val capacity = properties[0].trim().removePrefix("capacity ").toInt()
			val durability = properties[1].trim().removePrefix("durability ").toInt()
			val flavor = properties[2].trim().removePrefix("flavor ").toInt()
			val texture = properties[3].trim().removePrefix("texture ").toInt()
			val calories = properties[4].trim().removePrefix("calories ").toInt()
			ingredients.add(Ingredient(name, capacity, durability, flavor, texture, calories))
		}
		return ingredients.toTypedArray()
	}

	private fun calculateRecipeValue(ingredients: Array<Ingredient>, distribution: IntArray, mindCalories:Boolean = false): Int {
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
			calories += ingredients[i].calories * distribution[i]
		}
		if (capacity < 0 || durability < 0 || flavor < 0 || texture < 0 || mindCalories && calories != 500) {
			return 0
		}
		return capacity * durability * flavor * texture
	}

	fun findRecipes(
		ingredients: Array<Ingredient>,
		currentDistribution: IntArray,
		tokensRemaining: Int,
		ingredientIndex: Int,
		maxRecipeValue: MutableList<Int>,
		mindCalories: Boolean
	) {
		if (ingredientIndex == ingredients.size) {
			if (tokensRemaining == 0) {
				val recipeValue = calculateRecipeValue(ingredients, currentDistribution, mindCalories)
				if (recipeValue > maxRecipeValue[0]) {
					maxRecipeValue[0] = recipeValue
				}
			}
			return
		}

		for (tokensToAdd in 0..tokensRemaining) {
			currentDistribution[ingredientIndex] += tokensToAdd
			findRecipes(
				ingredients,
				currentDistribution,
				tokensRemaining - tokensToAdd,
				ingredientIndex + 1,
				maxRecipeValue,
				mindCalories
			)
			currentDistribution[ingredientIndex] -= tokensToAdd
		}
	}

	override fun b(): Any {
		val ingredients = getIngredients()
		val distribution = IntArray(ingredients.size) { 0 }
		val maxRecipeValue = mutableListOf(0)
		findRecipes(ingredients, distribution, 100, 0, maxRecipeValue, true)
		return maxRecipeValue[0]
	}

}