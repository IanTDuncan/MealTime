package com.example.mealtime1

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mealtime1.DatabaseFiles.DatabaseHelper
import com.spoonacular.client.model.GetRecipePriceBreakdownByID200Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ShoppingListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeAdapter

    private lateinit var addButton: Button
    private lateinit var removeButton: Button
    private lateinit var generateList: Button

    private lateinit var mealIds: IntArray // Global variable

    val apiKey = "faadc412663942a8909197924745241d"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shopping_list_activity)

        recyclerView = findViewById(R.id.recycler_view)
        addButton = findViewById(R.id.add_button)
        removeButton = findViewById(R.id.remove_button)
        generateList = findViewById(R.id.generate_list_button)

        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(MealPlanningService::class.java)

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get mealIds from result table
        val dbHelper = DatabaseHelper(this)
        mealIds = dbHelper.getMealIds()

        // Set up buttons
        addButton.setOnClickListener {
            // Handle adding item
        }

        removeButton.setOnClickListener {
            // Handle removing item
        }

        generateList.setOnClickListener {
            val ingredientCostList = mutableListOf<IngredientCost>() // Create a list to store IngredientCost objects

            val dbHelper = DatabaseHelper(this)

            // Check if there are already ingredients in the database for the meals
            val existingIngredients = dbHelper.getIngredientsForMeals(mealIds)

            if (existingIngredients.isNotEmpty()) {
                ingredientCostList.addAll(existingIngredients)
                adapter.notifyDataSetChanged()
            } else {
                mealIds.forEach { mealId ->
                    val call = service.getRecipeCost(mealId, apiKey)
                    call.enqueue(object : Callback<GetRecipePriceBreakdownByID200Response> {
                        override fun onResponse(
                            call: Call<GetRecipePriceBreakdownByID200Response>,
                            response: Response<GetRecipePriceBreakdownByID200Response>
                        ) {
                            if (response.isSuccessful) {
                                val recipe = response.body()
                                recipe?.ingredients?.forEach { ingredient ->
                                    val ingredientCost =
                                        IngredientCost(ingredient.name,
                                            android.icu.math.BigDecimal(ingredient.price)
                                        )
                                    ingredientCostList.add(ingredientCost) // Add IngredientCost to the list

                                    // Insert ingredient into database
                                    dbHelper.insertIngredient(mealId, ingredient.name,
                                        android.icu.math.BigDecimal(ingredient.price)
                                    )
                                }
                                adapter.notifyDataSetChanged()
                            } else {
                                Log.e("Shopping List", "Failed to get cost for ID: $mealId")
                            }
                        }

                        override fun onFailure(
                            call: Call<GetRecipePriceBreakdownByID200Response>,
                            t: Throwable
                        ) {
                            Log.e("Shopping List", "Failed to send call: $mealId")
                        }
                    })
                }
            }

            adapter = RecipeAdapter(ingredientCostList)
            recyclerView.adapter = adapter // Set adapter to RecyclerView
        }
    }
}
