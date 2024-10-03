package com.nebulavision.pokedexcompose.ui.screen.detail

import com.nebulavision.pokedexcompose.model.Pokemon

data class PokedexUiState(
    val error: String? = null,
    val pokemons: List<Pokemon> = listOf(),
    val isLoading: Boolean = true
)