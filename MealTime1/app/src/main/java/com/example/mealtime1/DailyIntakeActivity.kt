package com.example.mealtime1

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*

class DailyIntakeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_info_activity)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        // Initialize Firebase Database reference
        val database = FirebaseDatabase.getInstance()
        val dailyIntakesRef = database.getReference("dailyIntake")

        val newDailyIntake = DailyIntake(
            "uniqueDailyIntakeID",
            "uniqueUserID",
            System.currentTimeMillis(),
            2000.0
        )

        dailyIntakesRef.child(newDailyIntake.dailyIntakeID).setValue(newDailyIntake)
        dailyIntakesRef.child("uniqueDailyIntakeID")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val retrievedDailyIntake = dataSnapshot.getValue(DailyIntake::class.java)
                        // Process retrievedDailyIntake
                    } else {
                        // Handle case when daily intake does not exist
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle error
                }
            })
    }
}
