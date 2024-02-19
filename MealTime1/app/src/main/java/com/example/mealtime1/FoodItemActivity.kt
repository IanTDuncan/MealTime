package com.example.mealtime1

import FoodItem
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*

class FoodItemActivity : ComponentActivity() {

    private val itemRef: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("foodItems")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Create a new food item and push it to the database
        val newFoodItem = FoodItem(
            "uniqueFoodID",
            "Broccoli",
            55.0,
            3.7,
            11.2,
            0.6,
            5.1,
            100.0,
            //"grams"
        )
        itemRef.child(newFoodItem.foodID).setValue(newFoodItem)

        // Retrieve a food item from the database
        itemRef.child("uniqueFoodID").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val retrievedFoodItem = dataSnapshot.getValue(FoodItem::class.java)
                    // Process retrievedFoodItem
                } else {
                    // Handle case when food item does not exist
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
    }
}
