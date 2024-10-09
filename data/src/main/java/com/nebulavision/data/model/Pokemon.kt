package com.nebulavision.data.model

import com.nebulavision.data.model.pokemon.PokemonTypeSlot
import com.nebulavision.data.model.species.PokemonSpeciesGenera

data class Pokemon(
    val id: Int,
    val name: String,
    val enName: String,
    val types: List<PokemonTypeSlot>,
    val genera: List<PokemonSpeciesGenera>
)
