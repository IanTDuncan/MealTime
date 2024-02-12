package com.example.mealtime1

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*

class MealDataActivity : ComponentActivity() {

    private val mealsRef: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("meals")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Create a new meal and push it to the database
        val newMeal = MealData(
            "uniqueMealID",
            "uniqueUserID",
            "Breakfast",
            System.currentTimeMillis(),
            "Healthy breakfast",
            800,
            listOf("Oats", "Milk", "Banana")  // Example list of ingredients
        )
        mealsRef.child(newMeal.mealID).setValue(newMeal)

        // Retrieve a meal from the database
        mealsRef.child("uniqueMealID").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val retrievedMeal = dataSnapshot.getValue(MealData::class.java)
                    // Meal exists
                } else {
                    // Meal does not exist
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
    }
}

