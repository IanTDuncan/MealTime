package com.example.mealtime1

class MealData (
    var mealID: String = "",
    var userID: String = "",
    var mealName: String = "",
    var dateTime: Long = System.currentTimeMillis(),
    var description: String = "",
    var calories: Int,
    var ingredients: List<String> = listOf(),
    // Add more fields as needed (check with others)
)
