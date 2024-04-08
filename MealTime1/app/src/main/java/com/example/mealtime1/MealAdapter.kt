package com.example.mealtime1

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MealAdapter(private val meals: List<Meal>) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    private var alertDialog: AlertDialog? = null

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
            Picasso.get().load(meal.imageUrl).into(mealImageView)
        }

        override fun onClick(view: View) {
            // Dismiss any existing popup
            alertDialog?.dismiss()

            val context = view.context
            val builder = AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)
            val popupView = inflater.inflate(R.layout.meal_nutrition_activity, null)

            val popupTitleTextView: TextView = popupView.findViewById(R.id.popupTitleTextView)
            val popupDescriptionTextView: TextView = popupView.findViewById(R.id.popupDescriptionTextView)

            popupTitleTextView.text = meal.name
            popupDescriptionTextView.text = "This is a detailed description of ${meal.name}."

            builder.setView(popupView)
            alertDialog = builder.create()
            alertDialog?.show()
        }
    }
}
