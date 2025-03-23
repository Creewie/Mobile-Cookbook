package com.example.mobilecookbook

import java.io.Serializable

data class Recipe(
    var name: String,
    var ingredients: String,
    var instructions: String,
    var rating: Float
) : Serializable
