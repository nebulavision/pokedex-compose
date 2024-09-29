package com.github.nebulavision.pokedexcompose.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class PokemonNew(
    val title: String,
    val date: String,
    @DrawableRes val imageRes: Int,
    @StringRes val url: Int
)