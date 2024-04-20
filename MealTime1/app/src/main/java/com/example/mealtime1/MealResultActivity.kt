package com.example.mealtime1

import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spoonacular.client.model.GetRecipeInformation200Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MealResultActivity : ComponentActivity() {

    private lateinit var backButton: Button
    private lateinit var regenerateButton: Button
    private lateinit var saveMealButton: Button
    private lateinit var mealRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.meal_result_activity)

        backButton = findViewById(R.id.backButton)
        regenerateButton = findViewById(R.id.btn_go_back)
        saveMealButton = findViewById(R.id.btn_save_meal)
        mealRecyclerView = findViewById(R.id.mealRecyclerView)
        mealRecyclerView.layoutManager = LinearLayoutManager(this)

        val mealIds = intent.getIntArrayExtra("mealIds") ?: intArrayOf()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(MealPlanningService::class.java)
        val apiKey = "2b78d859d01c4e9c84d93a87691bf450"

        val meals = mutableListOf<Meal>()
        val adapter = MealAdapter(meals)
        mealRecyclerView.adapter = adapter
        mealIds.forEachIndexed { index, id ->
            val call = service.getRecipes(id, true, apiKey)
            call.enqueue(object : Callback<GetRecipeInformation200Response> {
                override fun onResponse(call: Call<GetRecipeInformation200Response>, response: Response<GetRecipeInformation200Response>) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        result?.let {
                            val image = it.image
                            image?.let { img ->
                                val meal = Meal(it.title ?: "Meal $id", img, id)
                                meals.add(meal)
                                adapter.notifyItemInserted(meals.size - 1)
                            }
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

        backButton.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }

        regenerateButton.setOnClickListener {
            val intent = Intent(this, GenerateMealActivity::class.java)
            startActivity(intent)
        }
    }
}