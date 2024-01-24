package com.example.mealtime1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MyBookmarksActivity: ComponentActivity() {
    private lateinit var buttonBackToMainMenu: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bookmars_activity)

         buttonBackToMainMenu = findViewById(R.id.buttonBackToMainMenu)

        buttonBackToMainMenu.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }
    }
}
