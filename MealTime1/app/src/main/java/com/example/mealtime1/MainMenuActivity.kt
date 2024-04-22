package com.example.mealtime1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.mealtime1.DatabaseFiles.DatabaseHelper
import com.example.mealtime1.DatabaseFiles.DeviceIdManager

import com.google.android.material.appbar.AppBarLayout

class MainMenuActivity: ComponentActivity() {
    private lateinit var buttonGenerateMeal: Button
    private lateinit var buttonGroceryList: Button
 //   private lateinit var buttonMyBookmarks: Button
    private lateinit var buttonMyAccount: Button
    private lateinit var appBar: AppBarLayout
  //  private lateinit var textView: TextView
   // private lateinit var textView2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu_activity)

        buttonGenerateMeal = findViewById(R.id.buttonGenerateMeal)
        buttonGroceryList = findViewById(R.id.buttonGroceryList)
   //     buttonMyBookmarks = findViewById(R.id.buttonMyBookmarks)
        buttonMyAccount = findViewById(R.id.buttonMyAccount)
       // appBar = findViewById(R.id.appbar)
      //  textView = findViewById(R.id.textView) //terms and conditions
       // textView2 = findViewById(R.id.textView2) //privacy policy

        val dbHelper = DatabaseHelper(this)
        // Check if the device has an associated ID in the database
        val deviceId = DeviceIdManager.getDeviceId(this)
        if (deviceId == null) {
            // If not, generate a new ID and store it in SharedPreferences
            val newDeviceId = DeviceIdManager.generateDeviceId()
            DeviceIdManager.saveDeviceId(this, newDeviceId)
        }
        else {
            println("Meal ID Retrieved")
        }


        val hasResults = dbHelper.hasResults()
        if (hasResults) {
            // If results exist, navigate to a different activity
            buttonGenerateMeal.setOnClickListener {
                val intent = Intent(this, MealResultActivity::class.java)
                startActivity(intent)
            }
        } else {
            // If no results, navigate to GenerateMealActivity
            buttonGenerateMeal.setOnClickListener {
                val intent = Intent(this, GenerateMealActivity::class.java)
                startActivity(intent)
            }
        }

        buttonGroceryList.setOnClickListener {
            val intent= Intent(this, ShoppingListActivity::class.java)
            startActivity(intent)
        }

      //  buttonMyBookmarks.setOnClickListener {
        //    val intent= Intent(this, MyBookmarksActivity::class.java)
      //      startActivity(intent)
     //   }

        buttonMyAccount.setOnClickListener {
            val intent = Intent(this, AccountAboutActivity::class.java)
            startActivity(intent)
        }

        //terms and conditions
   //     textView.setOnClickListener {
       //     val url = "https://publuu.com/flip-book/403736/913832"
      //      val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
         //   startActivity(intent)
      //  }

        //privacy policy
      //  textView2.setOnClickListener {
      //      val url = "https://publuu.com/flip-book/403736/913836"
      //      val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
       //     startActivity(intent)
      //  }



    }
}
