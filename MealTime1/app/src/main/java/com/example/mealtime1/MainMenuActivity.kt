package com.example.mealtime1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import android.widget.TextView

class MainMenuActivity: ComponentActivity() {
    private lateinit var buttonGenerateMeal: Button
    private lateinit var buttonGroceryList: Button
    private lateinit var buttonMyBookmarks: Button
    private lateinit var buttonMyAccount: Button
    private lateinit var textView: TextView
    private lateinit var textView2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu_activity)

        buttonGenerateMeal = findViewById(R.id.buttonGenerateMeal)
        buttonGroceryList = findViewById(R.id.buttonGroceryList)
        buttonMyBookmarks = findViewById(R.id.buttonMyBookmarks)
        buttonMyAccount = findViewById(R.id.buttonMyAccount)
        textView = findViewById(R.id.textView) //terms and conditions
        textView2 = findViewById(R.id.textView2) //privacy policy


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

        //terms and conditions
        textView.setOnClickListener {
            val url = "https://publuu.com/flip-book/403736/913832"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        //privacy policy
        textView2.setOnClickListener {
            val url = "https://publuu.com/flip-book/403736/913836"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }



    }
}
