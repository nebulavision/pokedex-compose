package com.nebulavision.pokedexcompose.ui.screen.home

import com.nebulavision.pokedexcompose.model.Pokemon

data class HomeUiState(
    val searchText: String = "",
    val pokemonSearch: List<Pokemon> = listOf(),
    val isLoading: Boolean = true
)
