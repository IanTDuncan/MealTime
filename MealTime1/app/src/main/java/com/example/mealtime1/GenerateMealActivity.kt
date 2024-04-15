package com.example.mealtime1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material3.AlertDialog
import com.spoonacular.MealPlanningApi
import com.spoonacular.client.ApiException
import com.spoonacular.client.Configuration
import com.spoonacular.client.auth.ApiKeyAuth
import com.spoonacular.client.model.GenerateMealPlan200Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigDecimal

class GenerateMealActivity: ComponentActivity() {
    private lateinit var buttonBackToMainMenu: Button
    private lateinit var editTextCostLimit: EditText
    private lateinit var editTextCalorieLimit: EditText
    private lateinit var editTextLimitAmount: EditText
    private lateinit var buttonSaveOptions: Button
    private lateinit var buttonGenerateMeal: Button
    private lateinit var radioTimeFrame: RadioGroup
    private lateinit var radioDiet: RadioGroup
    private lateinit var mealIds: Set<Int>
    private lateinit var mealTitles: Set<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.meal_activity)

        buttonBackToMainMenu = findViewById(R.id.backButton)
        buttonGenerateMeal = findViewById(R.id.generateMealButton)
        editTextCalorieLimit = findViewById(R.id.lowRange)
        radioTimeFrame = findViewById(R.id.weekOptions)
        radioDiet = findViewById(R.id.dietRadioGroup)


        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(MealPlanningService::class.java)


        buttonBackToMainMenu.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }

        buttonGenerateMeal.setOnClickListener {
           if(editTextCalorieLimit.text == null){
               Toast.makeText(applicationContext, "Please enter a calorie limit!", Toast.LENGTH_SHORT).show()
           }
            else {
               try {
                   // Get selected radio button for time frame
                   val selectedTimeFrameId = radioTimeFrame.checkedRadioButtonId
                   val radioButtonTimeFrame = findViewById<RadioButton>(selectedTimeFrameId)
                   val timeFrame = radioButtonTimeFrame?.text?.toString()?.lowercase() ?: "day"

                   // Get selected radio button for diet
                   val selectedDietId = radioDiet.checkedRadioButtonId
                   val radioButtonDiet = findViewById<RadioButton>(selectedDietId)
                   val diet = if (selectedDietId != -1) radioButtonDiet?.text?.toString()
                       ?.lowercase() else null

                   val targetCalories = BigDecimal(editTextCalorieLimit.text.toString())
                   val exclude = "shellfish, olives"
                   val apiKey = "2b78d859d01c4e9c84d93a87691bf450"

                   // Call the API to generate meal plan
                   val call =
                       service.generateMealPlan(timeFrame, targetCalories, diet, exclude, apiKey)
                   call.enqueue(object : Callback<GenerateMealPlan200Response> {
                       override fun onResponse(
                           call: Call<GenerateMealPlan200Response>,
                           response: Response<GenerateMealPlan200Response>
                       ) {
                           if (response.isSuccessful) {
                               val result = response.body()
                               result?.let {
                                   // Extract meal info and store as global variables
                                   mealIds = it.meals.map { meal -> meal.id }.toSet()
                                   mealTitles = it.meals.map { meal -> meal.title }.toSet()

                                   // Log the meal info after receiving the response
                                   Log.d(
                                       "MainActivity",
                                       "Meal IDs: $mealIds, Meal Titles: $mealTitles"
                                   )

                                   // Start MealResults activity
                                   val intent = Intent(
                                       this@GenerateMealActivity,
                                       MealResultActivity::class.java
                                   )
                                   intent.putExtra("mealIds", mealIds.toIntArray())
                                   startActivity(intent)
                               }
                           } else {
                               Log.e(
                                   "MainActivity",
                                   "Failed to retrieve meal plan: ${response.code()}"
                               )
                           }
                       }

                       override fun onFailure(
                           call: Call<GenerateMealPlan200Response>,
                           t: Throwable
                       ) {
                           Log.e("MainActivity", "Error occurred while fetching meal plan", t)
                       }
                   })
               } catch (e: Exception) {
                   // Handle other exceptions
                   Log.e("MainActivity", "Exception: ${e.message}", e)
                   e.printStackTrace()
               }
           }
        }


    }
}
