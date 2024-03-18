package com.example.mealtime1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.spoonacular.client.model.GenerateMealPlan200Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MealActivity: ComponentActivity() {
    private lateinit var buttonBackToMainMenu: Button
    private lateinit var editTextCostLimit: EditText
    private lateinit var editTextCalorieLimit: EditText
    private lateinit var editTextLimitAmount: EditText
    private lateinit var buttonSaveOptions: Button
    private lateinit var buttonGenerateMeal: Button

    private val service: MealPlanningService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(MealPlanningService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.meal_activity)

        buttonBackToMainMenu = findViewById(R.id.backButton)
        ///editTextCostLimit = findViewById(R.id.costLimitEditText)
        ///editTextCalorieLimit = findViewById(R.id.calorieLimitEditText)
        ///editTextLimitAmount = findViewById(R.id.limitAmountEditText)
        ///buttonSaveOptions = findViewById(R.id.saveOptionsButton)
        buttonGenerateMeal = findViewById(R.id.generateMealButton)


        buttonBackToMainMenu.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }

        buttonSaveOptions.setOnClickListener {
            val costLimit = editTextCostLimit.text.toString()
            val calorieLimit = editTextCalorieLimit.text.toString()
            val limitAmount = editTextLimitAmount.text.toString()
            // Save user options
            // Implement your logic here to save user options
        }

        buttonGenerateMeal.setOnClickListener {
            val costLimit = editTextCostLimit.text.toString()
            val calorieLimit = editTextCalorieLimit.text.toString()
            val limitAmount = editTextLimitAmount.text.toString()

            // Make API call asynchronously
            val call = service.generateMealPlan(
                "day",  // timeFrame
                2000.toBigDecimal(),  // targetCalories
                "vegetarian",  // diet
                "shellfish,olives",  // exclude
                "faadc412663942a8909197924745241d" // Replace with your actual API key
            )

            call.enqueue(object : Callback<GenerateMealPlan200Response> {
                override fun onResponse(
                    call: Call<GenerateMealPlan200Response>,
                    response: Response<GenerateMealPlan200Response>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        println(result)
                    } else {
                        println("Failed to generate meal plan. Code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<GenerateMealPlan200Response>, t: Throwable) {
                    println("Failed to generate meal plan. Error: ${t.message}")
                }
            })
        }
    }
}