package com.example.mealtime1

import android.content.Context
import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mealtime1.DatabaseFiles.DatabaseHelper

import com.squareup.picasso.Picasso
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.math.BigDecimal

class MealAdapter(private val meals: List<Meal>, private val context: Context) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    private var alertDialog: AlertDialog? = null

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.spoonacular.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(MealPlanningService::class.java)
    val apiKey = "faadc412663942a8909197924745241d"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meal_item, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]
        holder.bind(meal)
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    inner class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val mealImageView: ImageView = itemView.findViewById(R.id.mealImageView)
        private val mealNameTextView: TextView = itemView.findViewById(R.id.mealNameTextView)
        private lateinit var meal: Meal

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(meal: Meal) {
            this.meal = meal
            mealNameTextView.text = meal.name
            if (meal.imageUrl != null) {
                Picasso.get().load(meal.imageUrl).into(mealImageView)
            } else {
                // Load placeholder image if meal.imageUrl is null
                Picasso.get().load(R.drawable.ic_image_na).into(mealImageView)
            }
        }

        override fun onClick(view: View) {
            alertDialog?.dismiss()

            val builder = AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)
            val popupView = inflater.inflate(R.layout.nutrition_item, null)

            val mealNutritionLabel: ImageView = popupView.findViewById(R.id.mealNutritionLabel)

            if (meal.nutritionLabel != null) {
                // Nutrition label exists in the database, load it directly
                val bitmap = BitmapFactory.decodeByteArray(meal.nutritionLabel, 0, meal.nutritionLabel!!.size)
                mealNutritionLabel.setImageBitmap(bitmap)
            } else {
                // Nutrition label doesn't exist in the database, fetch it from API
                val call = service.getNutritionImage(BigDecimal(meal.id), apiKey, false, false, false)
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            val imageStream = response.body()?.byteStream()
                            if (imageStream != null) {
                                val bitmap = BitmapFactory.decodeStream(imageStream)
                                mealNutritionLabel.setImageBitmap(bitmap)

                                // Save the nutrition label to the database
                                saveNutritionLabelToDatabase(meal.id, bitmap)
                            }
                        } else {
                            Log.e("NutrientLabel", "Error fetching the nutrient label for meal: $meal")
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        t.printStackTrace()
                        Log.e("NutrientLabel", "Error fetching the nutrient label for meal: $meal", t)
                    }
                })
            }

            builder.setView(popupView)
            alertDialog = builder.create()
            alertDialog?.show()
        }

        private fun saveNutritionLabelToDatabase(mealId: Int, bitmap: Bitmap) {
            // Convert bitmap to byte array
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()

            if(stream == null && mealId == null) {
                // Insert into the database
                val dbHelper = DatabaseHelper(context)
                dbHelper.insertNutritionLabel(mealId, byteArray)
            }
        }
    }

}
