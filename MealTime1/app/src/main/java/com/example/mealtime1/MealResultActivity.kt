package com.example.mealtime1

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mealtime1.DatabaseFiles.DatabaseHelper
import com.spoonacular.client.model.GetRecipeInformation200Response
import com.squareup.picasso.Picasso
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

    private val meals = mutableListOf<Meal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.meal_result)

        backButton = findViewById(R.id.backButton)
        regenerateButton = findViewById(R.id.btn_go_back)
        saveMealButton = findViewById(R.id.btn_save_meal)
        mealRecyclerView = findViewById(R.id.mealRecyclerView)
        mealRecyclerView.layoutManager = LinearLayoutManager(this)

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)

        if (isFirstRun) {
            // Show alert dialog reminding the user to save the meal if they like it
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Reminder")
            builder.setMessage("If you like this meal plan, don't forget to save it!")
            builder.setPositiveButton("OK", null)
            val dialog: AlertDialog = builder.create()
            dialog.show()

            // Set isFirstRun to false to indicate that it's no longer the first run
            sharedPreferences.edit().putBoolean("isFirstRun", false).apply()
        }

        val dbHelper = DatabaseHelper(this)

        // Query the database for saved meals
        val savedMeals = dbHelper.getSavedMeals()

        // If there are no saved meals, fetch from API
        if (savedMeals.isEmpty()) {
            fetchMealsFromAPI()
        } else {
            displaySavedMeals(savedMeals)
        }

        saveMealButton.setOnClickListener {
            val context: Context = saveMealButton.context

            val builder = android.app.AlertDialog.Builder(context)
            builder.setTitle("Warning!")
            builder.setMessage("This action will override any previously saved meals for this current plan. Would you like to continue?")
            builder.setPositiveButton("OK"){ dialog, _ ->
                for (meal in meals) {
                    val dialog = (saveMealButton.context as? AlertDialog)
                    dialog?.dismiss()
                    dbHelper.insertMeal(meal)
                    println("Meals Saved!")
                }
                dialog.dismiss()
            }
            builder.setNegativeButton("Cancel"){ dialog, _ ->
                dialog.dismiss()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
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

    private fun fetchMealsFromAPI() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(MealPlanningService::class.java)
        val apiKey = "2b78d859d01c4e9c84d93a87691bf450"

        val mealIds = intent.getIntArrayExtra("mealIds") ?: intArrayOf()

        //val meals = mutableListOf<Meal>()
        val adapter = MealAdapter(meals, this)
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


    }


    private fun displaySavedMeals(savedMeals: List<Meal>) {
        val adapter = MealAdapter(savedMeals.toMutableList(),this)
        mealRecyclerView.adapter = adapter
    }
}
