package com.example.mealtime1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.mealtime1.ui.theme.RegistrationActivity

class MainMenuActivity: ComponentActivity() {
    private lateinit var buttonBack: Button
    private lateinit var buttonGenerateMeal: Button
    private lateinit var buttonGroceryList: Button
    private lateinit var buttonMyBookmarks: Button
    private lateinit var buttonMyAccount: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu_activity)

        buttonBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        buttonGenerateMeal.setOnClickListener {
            val intent = Intent(this,GenerateMealActivity::class.java)
            startActivity(intent)
        }

        buttonGroceryList.setOnClickListener {
            val intent= Intent(this, GroceryListActivity::class.java)
            startActivity(intent)
        }

        buttonMyBookmarks.setOnClickListener {
            val intent= Intent(this, MyBookmarksActivity::class.java)
            startActivity(intent)
        }

        buttonMyAccount.setOnClickListener {
            val intent = Intent(this, MyAccountActivity::class.java)
            startActivity(intent)
        }



        }
}