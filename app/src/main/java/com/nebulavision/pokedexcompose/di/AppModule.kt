package com.nebulavision.pokedexcompose.di

import com.nebulavision.pokedexcompose.data.repository.PokemonNewsRemoteDataSource
import com.nebulavision.pokedexcompose.data.repository.PokemonNewsRepository
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
    fun providePokemonNewsRemoteDataSource(): PokemonNewsRemoteDataSource{
        return PokemonNewsRemoteDataSource()
    }

    @Provides
    @Singleton
    fun providePokemonNewsRepository(pokemonNewsRemoteDataSource: PokemonNewsRemoteDataSource): PokemonNewsRepository{
        return PokemonNewsRepository(pokemonNewsRemoteDataSource)
    }
}