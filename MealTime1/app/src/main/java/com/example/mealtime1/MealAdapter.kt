package com.example.mealtime1

import android.app.AlertDialog
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigDecimal

class MealAdapter(private val meals: List<Meal>, ) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    private var alertDialog: AlertDialog? = null

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
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

            val context = view.context
            val builder = AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)
            val popupView = inflater.inflate(R.layout.nutrition_item, null)

            val mealNutritionLabel: ImageView = popupView.findViewById(R.id.mealNutritionLabel)

            val call = service.getNutritionImage(BigDecimal(meal.id), apiKey, false, false, false)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        val imageStream = response.body()?.byteStream()
                        if (imageStream != null) {
                            // Load the image using BitmapFactory or any other library of your choice
                            val bitmap = BitmapFactory.decodeStream(imageStream)
                            mealNutritionLabel.setImageBitmap(bitmap)
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

            builder.setView(popupView)
            alertDialog = builder.create()
            alertDialog?.show()
        }

    }
}
