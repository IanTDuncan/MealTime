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
        setContentView(R.layout.account_options_activity)

        buttonBackToMainMenu = findViewById(R.id.buttonBackToMainMenu)
        buttonAccount = findViewById(R.id.buttonAccount)
        buttonNotifications = findViewById(R.id.buttonNotifications)
        buttonHelpSupport = findViewById(R.id.buttonHelpSupport)

        buttonBackToMainMenu.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }

        buttonHelpSupport.setOnClickListener {
            val intent = Intent(this, AccountAboutActivity::class.java)
            startActivity(intent)
        }
    }
}
