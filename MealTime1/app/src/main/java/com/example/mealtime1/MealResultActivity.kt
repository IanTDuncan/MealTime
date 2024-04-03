package com.example.mealtime1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.spoonacular.client.model.GetRecipeInformation200Response
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MealResultActivity: ComponentActivity() {
    private lateinit var text_meal_name1: TextView
    private lateinit var text_meal_name2: TextView
    private lateinit var text_meal_name3: TextView

    private lateinit var image_meal1: ImageView
    private lateinit var image_meal2: ImageView
    private lateinit var image_meal3: ImageView

    private lateinit var btn_go_back: Button
    private lateinit var btn_save_meal: Button
    private lateinit var backtoMainMenu: Button

    private lateinit var mealIds: Set<Int>
    private val recipeImages: MutableMap<Int, String> = mutableMapOf()
    private val recipeTitles: MutableMap<Int, String> = mutableMapOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.meal_result_activity)

        text_meal_name1 = findViewById(R.id.text_meal_name1)
        text_meal_name2 = findViewById(R.id.text_meal_name2)
        text_meal_name3 = findViewById(R.id.text_meal_name3)

        image_meal1 = findViewById(R.id.image_meal1)
        image_meal2 = findViewById(R.id.image_meal2)
        image_meal3 = findViewById(R.id.image_meal3)

        btn_go_back = findViewById(R.id.btn_go_back)
        btn_save_meal = findViewById(R.id.btn_save_meal)
        backtoMainMenu = findViewById(R.id.backButton)

        val mealIds = intent.getIntArrayExtra("mealIds") ?: intArrayOf()



        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(MealPlanningService::class.java)
        val apiKey = "faadc412663942a8909197924745241d"

        mealIds.forEachIndexed { index, id ->
            val call = service.getRecipes(id, false,apiKey)
            call.enqueue(object : Callback<GetRecipeInformation200Response> {
                override fun onResponse(call: Call<GetRecipeInformation200Response>, response: Response<GetRecipeInformation200Response>) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        result?.let {
                            val image = it.image
                            image?.let { img ->
                                recipeImages[id] = img
                                when (index) {
                                    0 -> {
                                        Picasso.get().load(img).into(image_meal1)
                                        text_meal_name1.text = it.title
                                    }
                                    1 -> {
                                        Picasso.get().load(img).into(image_meal2)
                                        text_meal_name2.text = it.title
                                    }
                                    2 -> {
                                        Picasso.get().load(img).into(image_meal3)
                                        text_meal_name3.text = it.title
                                    }
                                }
                            }
                            recipeTitles[id] = it.title
                        }
                    } else {
                        Log.e("MealResults", "Failed to get recipe information for ID: $id")
                    }
                }

                override fun onFailure(call: Call<GetRecipeInformation200Response>, t: Throwable) {
                    Log.e("MealResults", "Error fetching recipe information for ID: $id", t)
                }
            })
        }

        backtoMainMenu.setOnClickListener {
            val intent = Intent(this, GenerateMealActivity::class.java)
            startActivity(intent)
        }

    }
}