package com.example.mealtime1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.spoonacular.MealPlanningApi
import com.spoonacular.client.ApiClient
import com.spoonacular.client.ApiException
import com.spoonacular.client.Configuration
import com.spoonacular.client.auth.ApiKeyAuth
import com.spoonacular.client.model.GenerateMealPlan200Response
import java.math.BigDecimal

class GenerateMealActivity : ComponentActivity() {
    private lateinit var buttonBackToMainMenu: Button
    private lateinit var editTextCostLimit: EditText
    private lateinit var editTextCalorieLimit: EditText
    private lateinit var editTextLimitAmount: EditText
    private lateinit var buttonSaveOptions: Button
    private lateinit var buttonGenerateMeal: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.meal_activity)

        buttonBackToMainMenu = findViewById(R.id.backButton)
        //editTextCostLimit = findViewById(R.id.costLimitEditText)
        //editTextCalorieLimit = findViewById(R.id.calorieLimitEditText)
        //editTextLimitAmount = findViewById(R.id.limitAmountEditText)
        ///buttonSaveOptions = findViewById(R.id.saveOptionsButton)
        buttonGenerateMeal = findViewById(R.id.generateMealButton)

        buttonBackToMainMenu.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }

        buttonSaveOptions.setOnClickListener {
            // Save user options
            val costLimit = editTextCostLimit.text.toString()
            val calorieLimit = editTextCalorieLimit.text.toString()
            val limitAmount = editTextLimitAmount.text.toString()

            // Store global variables here if needed
        }

        buttonGenerateMeal.setOnClickListener {
            // Configure API client
            val defaultClient = Configuration.getDefaultApiClient()
            defaultClient.basePath = "https://api.spoonacular.com"

            // Configure API key authorization
            val apiKeyScheme = defaultClient.getAuthentication("apiKeyScheme") as ApiKeyAuth
            apiKeyScheme.apiKey = "YOUR API KEY"

            // Instantiate MealPlanningApi
            val apiInstance = MealPlanningApi(defaultClient)
            val timeFrame = "day"
            val targetCalories = BigDecimal(editTextCalorieLimit.text.toString())
            val diet = "vegetarian"
            val exclude = "shellfish, olives"

            try {
                val result = apiInstance.generateMealPlan(timeFrame, targetCalories, diet, exclude)
                // Return global variables here if needed
            } catch (e: ApiException) {
                println("Exception when calling MealPlanningApi#generateMealPlan")
                println("Status code: ${e.code}")
                println("Reason: ${e.responseBody}")
                println("Response headers: ${e.responseHeaders}")
                e.printStackTrace()
            }
        }
    }
}