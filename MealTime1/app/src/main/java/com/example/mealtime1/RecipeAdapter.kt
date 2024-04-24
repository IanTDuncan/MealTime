package com.example.mealtime1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mealtime1.R
import com.spoonacular.client.model.GetRecipePriceBreakdownByID200Response
import java.text.DecimalFormat

class RecipeAdapter(private val ingredientCostList: List<IngredientCost>) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewText: TextView = itemView.findViewById(R.id.text_view_text)
        val textViewCost: TextView = itemView.findViewById(R.id.text_view_cost)
        val mealViewText: TextView = itemView.findViewById(R.id.meal_view_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recipe_cost, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentItem = ingredientCostList[position]
        holder.textViewText.text = currentItem.name

        // Format the price
        val df = DecimalFormat("#0.00")
        val formattedPrice = df.format(currentItem.price.toDouble())

        holder.textViewCost.text = "Cost: $$formattedPrice"

        // Set meal name to the meal_view_text TextView
        holder.mealViewText.text = currentItem.mealName ?: "Meal Name"
    }


    override fun getItemCount(): Int {
        return ingredientCostList.size
    }
}
