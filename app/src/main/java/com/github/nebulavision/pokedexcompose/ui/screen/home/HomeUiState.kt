package com.github.nebulavision.pokedexcompose.ui.screen.home

import com.github.nebulavision.pokedexcompose.data.PokemonNew

data class HomeUiState(
    val searchText: String = "",
    val pokemonNews: List<PokemonNew> = listOf()
)
