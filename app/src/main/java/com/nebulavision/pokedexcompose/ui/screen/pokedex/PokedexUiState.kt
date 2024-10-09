package com.nebulavision.pokedexcompose.ui.screen.pokedex

import com.nebulavision.pokedexcompose.model.Pokemon

data class PokedexUiState(
    val error: String? = null,
    val pokemons: List<Pokemon> = listOf(),
    val isLoading: Boolean = true
)