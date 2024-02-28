package com.example.mealtime1

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*

class GroceryListActivity2 : ComponentActivity() {

    private val groceryListRef: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("groceryList")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Create a new grocery list and push it to the database
        val newGroceryList = GroceryList(
            "uniqueListID",
            "uniqueUserID",
            "uniqueFoodID",
            System.currentTimeMillis(),
            "Groceries",
            200
        )
        groceryListRef.child(newGroceryList.GroceryListID).setValue(newGroceryList)

        // Retrieve a grocery list from the database
        groceryListRef.child("uniqueListID").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val retrieveGroceryList = dataSnapshot.getValue(MealData::class.java)
                    // Grocery List exists
                } else {
                    // Grocery List does not exist
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
    }
}
