package com.nebulavision.pokedexcompose.ui.screen

import android.net.Uri
import com.nebulavision.pokedexcompose.model.Pokemon
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Pokedex : Screen("pokedex")
    data object PokemonDetails : Screen("detail/{${NavArgs.Pokemon.key}}"){
        fun createRoute(pokemon: Pokemon) = "detail/${Uri.encode(Json.encodeToJsonElement(pokemon).toString())}"
    }
    data object Moves : Screen("moves")
    data object Abilities : Screen("abilities")
    data object Items : Screen("items")
    data object Locations : Screen("locations")
    data object TypeCharts : Screen("types_charts")
}

enum class NavArgs(val key: String){
    Pokemon("pokemon")
}