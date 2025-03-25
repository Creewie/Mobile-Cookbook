package com.example.mobilecookbook

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity(), OnRecipeInteractionListener {

    private lateinit var sharedPrefs: SharedPreferences
    private val gson = Gson()

    // Lista przepisów
    private var recipeList = mutableListOf<Recipe>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Odpalenie SharedPreferences inicjalizacja czy jakos tak
        sharedPrefs = getSharedPreferences("my_cookbook_prefs", Context.MODE_PRIVATE)

        // Wczytanie przepisów z SharedPreferences funckja sama w sobie na dole
        loadRecipesFromPrefs()

        // Jeśli to pierwsze uruchomienie, pokaż listę przepisów
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setCustomAnimations(
                    R.anim.slide_in_right,  // anim wejścia
                    R.anim.slide_out_left,  // anim wyjścia
                    R.anim.slide_in_left,   // anim powrotu (pop enter)
                    R.anim.slide_out_right  // anim powrotu (pop exit)
                )
                replace(R.id.fragmentContainer, RecipeListFragment.newInstance(), "RecipeList")
            }
        }
    }

    override fun onRecipeSelected(recipe: Recipe, position: Int) {
        // Przejscie do fragmentu z szczegolami przepisów type shi
        val detailsFragment = RecipeDetailsFragment.newInstance(recipe, position)
        supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            replace(R.id.fragmentContainer, detailsFragment, "RecipeDetails")
            addToBackStack(null)
        }
    }

    override fun onRecipeAdded(recipe: Recipe) {
        recipeList.add(recipe)
        saveRecipesToPrefs()
        // Cofnij się do listy (lub można też wstawić fragment listy od nowa)
        supportFragmentManager.popBackStack()
    }

    //No update cnie
    override fun onRecipeUpdated(recipe: Recipe, position: Int) {
        recipeList[position] = recipe
        saveRecipesToPrefs()
    }

    //Funkcja od wczytywania przepisów z Jojsona (Julka)
    private fun loadRecipesFromPrefs() {
        val json = sharedPrefs.getString("recipes_list", null) ?: return
        val type = object : TypeToken<MutableList<Recipe>>() {}.type
        val listFromJson: MutableList<Recipe> = gson.fromJson(json, type)
        recipeList = listFromJson
    }

    //Zapisanie do SharedPreferences
    private fun saveRecipesToPrefs() {
        val editor = sharedPrefs.edit()
        val json = gson.toJson(recipeList)
        editor.putString("recipes_list", json)
        editor.apply()
    }

    override fun onRecipeDeleted(position: Int) {
        if (position in recipeList.indices) {
            recipeList.removeAt(position)
            saveRecipesToPrefs()
        }
        supportFragmentManager.popBackStack() // Powrót do listy przepisów
    }

    // Funkcja pomocnicza, żeby fragmenty mogły pobierać aktualną listę
    fun getRecipes(): MutableList<Recipe> {
        return recipeList
    }
}
