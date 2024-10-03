package com.nebulavision.pokedexcompose.ui.screen.home

import com.nebulavision.pokedexcompose.model.PokemonNew

data class HomeUiState(
    val searchText: String = "",
    val pokemonNews: List<PokemonNew> = listOf(),
    val isLoading: Boolean = true
)
