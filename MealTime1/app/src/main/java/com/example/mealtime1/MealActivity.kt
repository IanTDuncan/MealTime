package com.example.mealtime1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.ComponentActivity
import com.spoonacular.MealPlanningApi
import com.spoonacular.client.ApiClient
import com.spoonacular.client.ApiException
import com.spoonacular.client.Configuration
import com.spoonacular.client.auth.ApiKeyAuth
import com.spoonacular.client.model.GenerateMealPlan200Response
import java.math.BigDecimal

class MealActivity : ComponentActivity() {
    private lateinit var buttonBackToMainMenu: Button
    private lateinit var editTextCostLimit: EditText
    private lateinit var editTextCalorieLimit: EditText
    private lateinit var editTextLimitAmount: EditText
    private lateinit var buttonSaveOptions: Button
    private lateinit var buttonGenerateMeal: Button
    private lateinit var radioTimeFrame: RadioGroup
    private lateinit var radioDiet: RadioGroup

    // Global variable to store meal info
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

        buttonBackToMainMenu.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }

        buttonGenerateMeal.setOnClickListener {
            // Configure API client
            val defaultClient = Configuration.getDefaultApiClient()
            defaultClient.basePath = "https://api.spoonacular.com"

            // Configure API key authorization
            val apiKeyScheme = defaultClient.getAuthentication("apiKeyScheme") as ApiKeyAuth
            apiKeyScheme.apiKey = "faadc412663942a8909197924745241d"

            // Instantiate MealPlanningApi
            val apiInstance = MealPlanningApi(defaultClient)

            // Get selected radio button for time frame
            val selectedTimeFrameId = radioTimeFrame.checkedRadioButtonId
            val radioButtonTimeFrame = findViewById<RadioButton>(selectedTimeFrameId)
            val timeFrame = radioButtonTimeFrame?.text?.toString()?.lowercase() ?: "day"

            // Get selected radio button for diet
            val selectedDietId = radioDiet.checkedRadioButtonId
            val radioButtonDiet = findViewById<RadioButton>(selectedDietId)
            val diet = if (selectedDietId != -1) radioButtonDiet?.text?.toString()?.lowercase() else null

            val targetCalories = BigDecimal(editTextCalorieLimit.text.toString())
            val exclude = "shellfish, olives"

            try {
                val result = apiInstance.generateMealPlan(timeFrame, targetCalories, diet, exclude)
                // Extract meal info and store as global variables
                mealIds = result.meals.map { it.id }.toSet()
                mealTitles = result.meals.map { it.title }.toSet()
                val intent = Intent(this, MealResults::class.java)
                startActivity(intent)
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