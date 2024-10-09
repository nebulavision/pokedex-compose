package com.nebulavision.data.network


import com.nebulavision.data.model.Pokemon
import com.nebulavision.data.model.PokemonPage
import com.nebulavision.data.model.species.PokemonSpecies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://pokeapi.co/api/v2/"

interface PokemonApiService{
    @GET("pokemon")
    suspend fun getPokemonPage(@Query("offset") offset: Int = 0, @Query("limit") limit: Int = 20) : PokemonPage

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") pokemonId: Int) : Pokemon

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(@Path("id") pokemonId: Int): PokemonSpecies
}