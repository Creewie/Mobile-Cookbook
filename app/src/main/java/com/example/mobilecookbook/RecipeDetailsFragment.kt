package com.example.mobilecookbook

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import androidx.fragment.app.Fragment

// Stałe klucze do przekazywania danych między fragmentami
private const val ARG_RECIPE = "arg_recipe"
private const val ARG_POSITION = "arg_position"
class RecipeDetailsFragment : Fragment() {

    private var listener: OnRecipeInteractionListener? = null
    private var recipe: Recipe? = null
    private var position: Int = -1

    // Wywoływane, gdy fragment zostaje dołączony do aktywności
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Sprawdza, czy aktywność implementuje interfejs
        if (context is OnRecipeInteractionListener) {
            listener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipe = it.getSerializable(ARG_RECIPE, Recipe::class.java) as Recipe
            position = it.getInt(ARG_POSITION, -1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val deleteButton = view.findViewById<Button>(R.id.deleteButton)
        deleteButton?.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Potwierdzenie usunięcia")
            .setMessage("Czy na pewno chcesz usunąć ten przepis?")
            .setPositiveButton("Tak") { _, _ ->
                recipe?.let {
                    listener?.onRecipeDeleted(position)
                    parentFragmentManager.popBackStack()
                }
            }
            .setNegativeButton("Nie", null) // Zamknij dialog bez akcji
            .show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Utworzenie widok na podstawie XML i zmiana na obiekt view
        val view = inflater.inflate(R.layout.fragment_recipe_details, container, false)

        val nameEditText = view.findViewById<EditText>(R.id.detailsNameEditText)
        val ingredientsEditText = view.findViewById<EditText>(R.id.detailsIngredientsEditText)
        val instructionsEditText = view.findViewById<EditText>(R.id.detailsInstructionsEditText)
        val ratingBar = view.findViewById<RatingBar>(R.id.detailsRatingBar)

        recipe?.let {
            nameEditText.setText(it.name)
            ingredientsEditText.setText(it.ingredients)
            instructionsEditText.setText(it.instructions)
            ratingBar.rating = it.rating
        }

        // Obsługa zmiany oceny
        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            // Aktualizuje ocenę
            recipe?.rating = rating

            // Informuje aktywność, że przepis został zmieniony
            recipe?.let { r ->
                listener?.onRecipeUpdated(r, position)
            }
        }

        val backButton = view.findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            // Usuwa ten fragment i wraca do poprzedniego
            parentFragmentManager.popBackStack()
        }

        return view
    }

    companion object {
        // Funkcja tworząca nową instancję fragmentu z przekazanym przepisem i jego pozycją
        @JvmStatic
        fun newInstance(recipe: Recipe, position: Int) =
            RecipeDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_RECIPE, recipe)
                    putInt(ARG_POSITION, position)
                }
            }
    }
}
