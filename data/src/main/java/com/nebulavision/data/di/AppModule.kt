package com.nebulavision.data.di

import android.content.Context
import com.nebulavision.data.database.PokedexDatabase
import com.nebulavision.data.network.BASE_URL
import com.nebulavision.data.network.PokemonApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApiService(): PokemonApiService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

        return retrofit.create(PokemonApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePokemonDao(@ApplicationContext context: Context) = PokedexDatabase.getDatabase(context).pokemonDao()
}