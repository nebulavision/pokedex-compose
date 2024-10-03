package com.nebulavision.data.model

data class PokemonPageEntry(
    val name: String,
    val url: String
)

data class PokemonPage(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonPageEntry>
)
