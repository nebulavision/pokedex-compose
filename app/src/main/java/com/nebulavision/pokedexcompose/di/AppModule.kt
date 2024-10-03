package com.nebulavision.pokedexcompose.di

import com.nebulavision.data.database.dao.PokemonDao
import com.nebulavision.data.repository.datasource.PokemonNewsDataSource
import com.nebulavision.data.network.PokemonApiService
import com.nebulavision.data.repository.datasource.PokemonRemoteDataSource
import com.nebulavision.data.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePokemonRemoteDataSource(pokemonApiService: PokemonApiService): PokemonRemoteDataSource {
        return PokemonRemoteDataSource(pokemonApiService)
    }

    @Provides
    @Singleton
    fun providePokemonRepository(pokemonRemoteDataSource: PokemonRemoteDataSource, pokemonDao: PokemonDao): PokemonRepository {
        return PokemonRepository(pokemonRemoteDataSource, pokemonDao)
    }
}