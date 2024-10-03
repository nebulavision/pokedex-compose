package com.nebulavision.pokedexcompose.model

data class Pokemon(
    val id: String,
    val name: String,
    val imageUrl: String,
    val types: List<Int>
)
