package com.example.mealtime1

// Create a Kotlin data class to represent your daily intake data
data class DailyIntake(
    var dailyIntakeID: String = "",
    var userID: String = "",
    var date: Long = System.currentTimeMillis(),
    var totalCaloriesConsumed: Double = 0.0
)
