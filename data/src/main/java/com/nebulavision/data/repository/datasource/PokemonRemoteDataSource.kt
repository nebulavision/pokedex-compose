package com.nebulavision.data.repository.datasource

import com.nebulavision.data.network.PokemonApiService

class PokemonRemoteDataSource (private val pokemonApiService: PokemonApiService) {

    suspend fun getPokemonPage(offset: Int, limit: Int) = pokemonApiService.getPokemonPage(offset, limit)

    suspend fun getPokemonDetailApiResponse(pokemonId: Int) = pokemonApiService.getPokemonDetailsApiResponse(pokemonId)
}