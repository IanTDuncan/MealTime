package com.example.mealtime1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MyAccountActivity: ComponentActivity() {
    private lateinit var buttonBackToMainMenu: Button
    //Account option buttons
    private lateinit var buttonAccount: Button
    private lateinit var buttonNotifications: Button
    private lateinit var buttonPrivacySecurity: Button
    private lateinit var buttonHelpSupport: Button
    private lateinit var buttonAbout: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_account_activity)

        buttonBackToMainMenu.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }
    }
}