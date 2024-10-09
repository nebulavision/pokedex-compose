package com.nebulavision.pokedexcompose.ui.screen.detail

import com.nebulavision.pokedexcompose.model.Pokemon

data class PokemonDetailsUiState(
    val isLoading: Boolean = true,
    val previousPokemonImageUrl: String? = null,
    val pokemon: Pokemon? = null,
    val nextPokemonImageUrl: String? = null,
    val error: String = ""
)
