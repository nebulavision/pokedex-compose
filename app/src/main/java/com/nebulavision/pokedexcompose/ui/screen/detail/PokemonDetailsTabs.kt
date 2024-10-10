package com.nebulavision.pokedexcompose.ui.screen.detail

import android.content.Context
import androidx.annotation.StringRes
import com.nebulavision.pokedexcompose.R

enum class PokemonDetailsTabs (@StringRes val textId: Int, @StringRes val textLandscapeId: Int? = null){
    About(R.string.about),
    Stats(R.string.stats),
    Evolution(R.string.evolution),
    Moves(R.string.moves_short, R.string.moves)
}

context(Context)
fun PokemonDetailsTabs.getText(): String{
    return getString(textId)
}

context(Context)
fun PokemonDetailsTabs.getLandscapeText(): String{
    return getString(textLandscapeId ?: textId)
}