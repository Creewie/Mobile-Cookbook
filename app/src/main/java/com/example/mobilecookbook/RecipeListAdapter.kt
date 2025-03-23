package com.example.mobilecookbook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecipeListAdapter(
    private val recipes: List<Recipe>,  // Lista przepisów
    private val onItemClick: (Recipe, Int) -> Unit // Click listener
) : RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeName: TextView = itemView.findViewById(R.id.recipeName) // Tekst z nazwą przepisu
        val recipeRating: RatingBar = itemView.findViewById(R.id.recipeRating) // Gwiazdkowa ocena przepisu
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.recipeName.text = recipe.name
        holder.recipeRating.rating = recipe.rating

        holder.itemView.setOnClickListener {
            onItemClick(recipe, position)
        }
    }

    override fun getItemCount(): Int = recipes.size
}
