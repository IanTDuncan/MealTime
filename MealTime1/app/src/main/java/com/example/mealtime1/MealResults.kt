package com.example.mealtime1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.squareup.picasso.Picasso
import com.spoonacular.RecipesApi
import com.spoonacular.client.ApiClient
import com.spoonacular.client.ApiException
import com.spoonacular.client.Configuration
import com.spoonacular.client.auth.ApiKeyAuth
import com.spoonacular.client.model.GetRecipeInformation200Response

class MealResults : ComponentActivity() {
    private lateinit var text_meal_name1: TextView
    private lateinit var text_meal_name2: TextView
    private lateinit var text_meal_name3: TextView

    private lateinit var image_meal1: ImageView
    private lateinit var image_meal2: ImageView
    private lateinit var image_meal3: ImageView

    private lateinit var btn_go_back: Button
    private lateinit var btn_save_meal: Button

    private lateinit var btn_back: Button

    private lateinit var mealIds: Set<Int>
    private val recipeImages: MutableMap<Int, String> = mutableMapOf()
    private val recipeTitles: MutableMap<Int, String> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.meal_results_activity)

        text_meal_name1 = findViewById(R.id.text_meal_name1)
        text_meal_name2 = findViewById(R.id.text_meal_name2)
        text_meal_name3 = findViewById(R.id.text_meal_name3)

        image_meal1 = findViewById(R.id.image_meal1)
        image_meal2 = findViewById(R.id.image_meal2)
        image_meal3 = findViewById(R.id.image_meal3)

        btn_go_back = findViewById(R.id.btn_go_back)
        btn_save_meal = findViewById(R.id.btn_save_meal)
        btn_back = findViewById(R.id.btn_back)

        // Configure API client
        val defaultClient = Configuration.getDefaultApiClient()
        defaultClient.basePath = "https://api.spoonacular.com"

        // Configure API key authorization
        val apiKeyScheme = defaultClient.getAuthentication("apiKeyScheme") as ApiKeyAuth
        apiKeyScheme.apiKey = "faadc412663942a8909197924745241d"

        // Instantiate RecipesApi
        val apiInstance = RecipesApi(defaultClient)

        mealIds.forEachIndexed { index, id ->
            try {
                val result = apiInstance.getRecipeInformation(id, false)
                // Store recipe image URL
                result.image?.let { image ->
                    recipeImages[id] = image
                    // Set text and images to corresponding meal ids
                    when (index) {
                        0 -> {
                            Picasso.get().load(image).into(image_meal1)
                            text_meal_name1.text = result.title
                        }
                        1 -> {
                            Picasso.get().load(image).into(image_meal2)
                            text_meal_name2.text = result.title
                        }
                        2 -> {
                            Picasso.get().load(image).into(image_meal3)
                            text_meal_name3.text = result.title
                        }
                    }
                }
                // Store meal title
                recipeTitles[id] = result.title
            } catch (e: ApiException) {
                println("Exception when calling RecipesApi#getRecipeInformation")
                println("Status code: ${e.code}")
                println("Reason: ${e.responseBody}")
                println("Response headers: ${e.responseHeaders}")
                e.printStackTrace()
            }
        }

        btn_go_back.setOnClickListener {
            val intent = Intent(this, GenerateMealActivity::class.java)
            startActivity(intent)
        }

        btn_back.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }
    }
}
