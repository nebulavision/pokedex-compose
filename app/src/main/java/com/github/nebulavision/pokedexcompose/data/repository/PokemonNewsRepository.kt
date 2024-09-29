package com.github.nebulavision.pokedexcompose.data.repository

import com.github.nebulavision.pokedexcompose.data.PokemonNew

class PokemonNewsRepository(
    private val pokemonNewsRemoteDataSource: PokemonNewsRemoteDataSource
) {

    suspend fun getAll(): List<PokemonNew> = pokemonNewsRemoteDataSource.getPokemonNews()

}