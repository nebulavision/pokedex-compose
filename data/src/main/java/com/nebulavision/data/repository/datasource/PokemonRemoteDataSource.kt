package com.nebulavision.data.repository.datasource

import com.nebulavision.data.model.Pokemon
import com.nebulavision.data.network.PokemonApiService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class PokemonRemoteDataSource (private val pokemonApiService: PokemonApiService) {

    suspend fun getPokemonPage(offset: Int, limit: Int) = pokemonApiService.getPokemonPage(offset, limit)

    suspend fun getPokemon(pokemonId: Int, lang: String): Pokemon {
        return coroutineScope {
            val pokemonDeferred = async { pokemonApiService.getPokemon(pokemonId) }
            val pokemonSpecieDeferred = async { pokemonApiService.getPokemonSpecies(pokemonId) }

            val pokemon = pokemonDeferred.await()
            val pokemonSpecie = pokemonSpecieDeferred.await()

            pokemonSpecie.names
            Pokemon(
                id = pokemon.id,
                name = pokemonSpecie.names.first { it.language.name == lang }.name,
                enName = pokemonSpecie.names.first { it.language.name == "en" }.name.lowercase(),
                types = pokemon.types,
                genera = pokemonSpecie.genera
            )
        }
    }
}