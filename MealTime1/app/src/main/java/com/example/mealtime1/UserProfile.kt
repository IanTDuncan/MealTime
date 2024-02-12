package com.example.mealtime1

// Create a Kotlin data class to represent your user model
// The User class contains the data structure for a user
class User (
    var userID: String = "",
    var username: String = "",
    // var password: Firebase Authentication will handle this
    var email: String = "",
    //var age: Int = 0,
    //var gender: String = "",
    var weight: Double = 0.0,
    var height: Double = 0.0,
    var activityLevel: String = "",
    var targetCalories: Int,
    var budget: Double = 0.0,
    var healthConcerns: String = "",
    var allergies: String = ""

)


