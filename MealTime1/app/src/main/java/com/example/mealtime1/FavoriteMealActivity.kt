package com.example.mealtime1

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*

class FavoriteMealActivity : ComponentActivity() {

    private val favoriteMealRef: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("favoriteMeal")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Create a new meal and push it to the database
        val newFavoriteMeal = FavoriteMeals(
            "uniqueMealID",
            "uniqueUserID"
        )
        favoriteMealRef.child(newFavoriteMeal.mealID).setValue(newFavoriteMeal)

        // Retrieve a meal from the database
        favoriteMealRef.child("uniqueUserID").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val retrieveUser = dataSnapshot.getValue(MealData::class.java)
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


