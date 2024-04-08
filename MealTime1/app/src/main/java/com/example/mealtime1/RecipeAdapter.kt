package com.example.mealtime1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mealtime1.R
import com.spoonacular.client.model.GetRecipePriceBreakdownByID200Response

class RecipeAdapter(private val ingredientCostList: List<IngredientCost>) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewText: TextView = itemView.findViewById(R.id.text_view_text)
        val textViewCost: TextView = itemView.findViewById(R.id.text_view_cost)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recipe_cost, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentItem = ingredientCostList[position]
        holder.textViewText.text = currentItem.name
        holder.textViewCost.text = "Cost: $${currentItem.price}"
    }


    override fun getItemCount(): Int {
        return ingredientCostList.size
    }
}
