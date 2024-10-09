package com.nebulavision.data.repository.datasource

import com.nebulavision.data.model.Pokemon
import com.nebulavision.data.model.species.PokemonSpeciesGenera
import com.nebulavision.data.model.species.PokemonSpeciesNameLanguage
import com.nebulavision.data.network.PokemonApiService

class PokemonRemoteDataSource (private val pokemonApiService: PokemonApiService) {

    suspend fun getPokemonPage(offset: Int, limit: Int) = pokemonApiService.getPokemonPage(offset, limit)

    suspend fun getPokemon(pokemonId: Int, lang: String): Pokemon {
        val pokemon = pokemonApiService.getPokemon(pokemonId)
        val pokemonSpecie = pokemonApiService.getPokemonSpecies(pokemonId)

        pokemonSpecie.names
        return Pokemon(
            id = pokemon.id,
            name = pokemonSpecie.names.first { it.language.name == lang }.name,
            enName = pokemonSpecie.names.first { it.language.name == "en" }.name.lowercase(),
            types = pokemon.types,
            genera = pokemonSpecie.genera
        )
    }
}