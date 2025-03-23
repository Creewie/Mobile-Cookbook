package com.example.mobilecookbook

interface OnRecipeInteractionListener {
    /**
     * Przepis -> Szczegóły przepisu
     */
    fun onRecipeSelected(recipe: Recipe, position: Int)

    /**
     * Gdy utwarza się nowy przepis (to w ogóle poprawnie po polsku? XD)
     */
    fun onRecipeAdded(recipe: Recipe)

    /**
     * Gdy przepis jest updatowany
     */
    fun onRecipeUpdated(recipe: Recipe, position: Int)
}
