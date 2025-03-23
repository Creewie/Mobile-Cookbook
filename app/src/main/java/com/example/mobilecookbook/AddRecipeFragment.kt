package com.example.mobilecookbook

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import androidx.fragment.app.Fragment

class AddRecipeFragment : Fragment() {

    private var listener: OnRecipeInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnRecipeInteractionListener) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_recipe, container, false)

        val nameEditText = view.findViewById<EditText>(R.id.recipeNameEditText)
        val ingredientsEditText = view.findViewById<EditText>(R.id.recipeIngredientsEditText)
        val instructionsEditText = view.findViewById<EditText>(R.id.recipeInstructionsEditText)
        val ratingBar = view.findViewById<RatingBar>(R.id.recipeRatingBar)
        val saveButton = view.findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {
            val recipe = Recipe(
                name = nameEditText.text.toString(),
                ingredients = ingredientsEditText.text.toString(),
                instructions = instructionsEditText.text.toString(),
                rating = ratingBar.rating
            )
            listener?.onRecipeAdded(recipe)
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddRecipeFragment()
    }
}
