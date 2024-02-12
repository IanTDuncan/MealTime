package com.example.mealtime1
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import androidx.activity.ComponentActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class UserProfileActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_profile_activity)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true) // Optional for offline capabilities

        // Initialize Firebase Database reference
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("users")

        // Create a new user and push to the database
        val newUser = User(
            "uniqueUserID",
            "JohnDoe",
            //password: #,
            "john.doe@example.com",
            // age: 25,
            // gender: "male",
            173.0,
            5.11,
            "active",
            1750,
            2000.0,
            "diabetes",
            "cats"

        )
        usersRef.child(newUser.userID).setValue(newUser)

        // Retrieve a user from the database
        usersRef.child("uniqueUserID").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val retrievedUser = dataSnapshot.getValue(User::class.java)
                    // User exists
                } else {
                    // User does not exist
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })

    }
}


