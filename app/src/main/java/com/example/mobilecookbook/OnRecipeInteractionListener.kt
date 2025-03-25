package com.example.mobilecookbook

interface OnRecipeInteractionListener {
    /**
     * Przepis -> Szczegóły przepisu
     */
    fun onRecipeSelected(recipe: Recipe, position: Int)

    /**
     * Gdy tworzy się przepis
     */
    fun onRecipeAdded(recipe: Recipe)

    /**
     * Gdy przepis jest updatowany
     */
    fun onRecipeUpdated(recipe: Recipe, position: Int)

    /**
     * Gdy usuwamy przepis
     */
    fun onRecipeDeleted(position: Int)
}
