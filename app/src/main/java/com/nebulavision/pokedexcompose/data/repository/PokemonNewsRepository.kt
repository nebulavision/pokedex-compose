package com.nebulavision.pokedexcompose.data.repository

import com.nebulavision.pokedexcompose.data.PokemonNew

class PokemonNewsRepository(
    private val pokemonNewsRemoteDataSource: PokemonNewsRemoteDataSource
) {

    suspend fun getAll(): List<PokemonNew> = pokemonNewsRemoteDataSource.getPokemonNews()

}