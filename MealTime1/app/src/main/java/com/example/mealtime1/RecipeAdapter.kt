package com.example.mealtime1

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mealtime1.IngredientCost
import com.example.mealtime1.R
import java.text.DecimalFormat

class RecipeAdapter(private val ingredientCostList: List<IngredientCost>, private val listener: OnItemClickListener) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val textViewText: TextView = itemView.findViewById(R.id.text_view_text)
        val textViewCost: TextView = itemView.findViewById(R.id.text_view_cost)
        val mealViewText: TextView = itemView.findViewById(R.id.meal_view_text)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
                setSelectedPosition(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_cost, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentItem = ingredientCostList[position]
        holder.textViewText.text = currentItem.name

        // Format the price
        val df = DecimalFormat("#0.00")
        val formattedPrice = df.format(currentItem.price.toDouble())

        holder.textViewCost.text = "Cost: $$formattedPrice"

        holder.mealViewText.text = currentItem.mealName ?: "Meal Name"

        val lettuceGreenColor = ContextCompat.getColor(holder.itemView.context, R.color.lettuce_green)


        if (position == selectedPosition) {
            holder.itemView.setBackgroundColor(lettuceGreenColor)
            holder.textViewText.setTextColor(Color.WHITE)
            holder.textViewCost.setTextColor(Color.WHITE)
            holder.mealViewText.setTextColor(Color.WHITE)
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT)
            holder.textViewText.setTextColor(Color.GRAY)
            holder.textViewCost.setTextColor(Color.GRAY)
            holder.mealViewText.setTextColor(lettuceGreenColor)
        }
    }

    override fun getItemCount(): Int {
        return ingredientCostList.size
    }

    fun setSelectedPosition(position: Int) {
        val previousPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(previousPosition)
        notifyItemChanged(selectedPosition)
    }

    fun getSelectedItems(): List<IngredientCost> {
        if (selectedPosition != RecyclerView.NO_POSITION) {
            return listOf(ingredientCostList[selectedPosition])
        }
        return emptyList()
    }

    fun clearSelection() {
        val previousPosition = selectedPosition
        selectedPosition = RecyclerView.NO_POSITION
        if (previousPosition != RecyclerView.NO_POSITION) {
            notifyItemChanged(previousPosition)
        }
    }
}