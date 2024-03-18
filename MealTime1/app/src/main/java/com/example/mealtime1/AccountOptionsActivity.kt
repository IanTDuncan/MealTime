package com.example.mealtime1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class AccountOptionsActivity: ComponentActivity() {

    private lateinit var backButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_info_activity)

        backButton = findViewById(R.id.button)

        backButton.setOnClickListener {
            val intent = Intent(this, MyAccountActivity::class.java)
            startActivity(intent)
        }

    }
}
