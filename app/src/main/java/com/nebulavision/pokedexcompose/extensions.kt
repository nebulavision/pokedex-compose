package com.nebulavision.pokedexcompose

import androidx.compose.ui.graphics.Color
import com.nebulavision.pokedexcompose.model.Pokemon
import com.nebulavision.pokedexcompose.ui.theme.colorMap

fun String.padWithZeros() = padStart(4, '0')

fun Pokemon.getBackgroundColor(pokemonType: Int): Color {
    return when (pokemonType) {
        R.string.type_normal -> colorMap["type_normal"]!!
        R.string.type_fire -> colorMap["type_fire"]!!
        R.string.type_water -> colorMap["type_water"]!!
        R.string.type_electric -> colorMap["type_electric"]!!
        R.string.type_grass -> colorMap["type_grass"]!!
        R.string.type_ice -> colorMap["type_ice"]!!
        R.string.type_fighting -> colorMap["type_fighting"]!!
        R.string.type_poison -> colorMap["type_poison"]!!
        R.string.type_ground -> colorMap["type_ground"]!!
        R.string.type_flying -> colorMap["type_flying"]!!
        R.string.type_psychic -> colorMap["type_psychic"]!!
        R.string.type_bug -> colorMap["type_bug"]!!
        R.string.type_rock -> colorMap["type_rock"]!!
        R.string.type_ghost -> colorMap["type_ghost"]!!
        R.string.type_dragon -> colorMap["type_dragon"]!!
        R.string.type_dark -> colorMap["type_dark"]!!
        R.string.type_steel -> colorMap["type_steel"]!!
        else -> colorMap["type_fairy"]!!
    }
}