package com.nebulavision.pokedexcompose.di

import android.content.Context
import com.nebulavision.data.database.dao.PokemonDao
import com.nebulavision.data.network.PokemonApiService
import com.nebulavision.data.repository.datasource.PokemonRemoteDataSource
import com.nebulavision.data.repository.PokemonRepository
import com.nebulavision.pokedexcompose.model.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideResourceProvider(@ApplicationContext ctx: Context): ResourceProvider = ResourceProvider(ctx)

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