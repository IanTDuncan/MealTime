package com.example.mealtime1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class AccountAboutActivity: ComponentActivity() {

    private lateinit var buttonBack: Button
    private lateinit var buttonPrivacyPolicy: Button
    private lateinit var buttonForTerms: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_about_activity)

        buttonBack = findViewById(R.id.buttonBackToMyAccount)
        buttonForTerms = findViewById(R.id.buttonForTerms)
        buttonPrivacyPolicy = findViewById(R.id.buttonPrivacyPolicy)

        buttonBack.setOnClickListener{
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }

        buttonForTerms.setOnClickListener {
            val url = "https://publuu.com/flip-book/403736/913832"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        buttonPrivacyPolicy.setOnClickListener {
            val url = "https://publuu.com/flip-book/403736/913836"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

    }
}
