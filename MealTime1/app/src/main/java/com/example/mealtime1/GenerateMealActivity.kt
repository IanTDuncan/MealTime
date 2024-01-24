package com.example.mealtime1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity

class GenerateMealActivity: ComponentActivity() {
    private lateinit var buttonBackToMainMenu: Button
    private lateinit var editTextCostLimit: EditText
    private lateinit var editTextCalorieLimit: EditText
    private lateinit var editTextLimitAmount: EditText
    private lateinit var buttonSaveOptions: Button
    private lateinit var buttonGenerateMeal: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.generate_meal_activity)

         buttonBackToMainMenu = findViewById(R.id.buttonBackToMainMenu)

        buttonBackToMainMenu.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }
    }
}
