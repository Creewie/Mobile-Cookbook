package com.example.mobilecookbook

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecipeListFragment : Fragment() {

    private var listener: OnRecipeInteractionListener? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeListAdapter

    //Ustalenie że funkcja nasłuchująca to kontekst
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
        val view = inflater.inflate(R.layout.fragment_recipe_list, container, false)

        recyclerView = view.findViewById(R.id.recipeRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val mainActivity = activity as? MainActivity
        val recipeList = mainActivity?.getRecipes() ?: mutableListOf()

        adapter = RecipeListAdapter(recipeList) { recipe, position ->
            listener?.onRecipeSelected(recipe, position)
        }
        recyclerView.adapter = adapter

        val addButton: Button = view.findViewById(R.id.addRecipeButton)
        addButton.setOnClickListener {
            // Przejście do AddRecipeFragment
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right
                )
                .replace(R.id.fragmentContainer, AddRecipeFragment.newInstance(), "AddRecipe")
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = RecipeListFragment()
    }
}
