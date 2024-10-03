package com.nebulavision.data.model

data class Pokemon(
    val title: String,
    val date: String,
    val imageUrl: String,
    val url: String
)

data class PokemonForm(
    val name: String,
    val url: String
)

data class PokemonType(
    val name: String,
    val url: String
)

data class PokemonTypeSlot(
    val slot: Int,
    val type: PokemonType
)

data class PokemonDetails(
    val id: Int,
    val forms: List<PokemonForm>,
    val types: List<PokemonTypeSlot>
)
