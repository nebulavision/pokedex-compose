package com.nebulavision.data.repository

import android.util.Log
import com.google.gson.JsonParseException
import com.nebulavision.data.database.dao.PokemonDao
import com.nebulavision.data.database.entity.PokemonEntity
import com.nebulavision.data.model.ErrorType
import com.nebulavision.data.model.ResponseResource
import com.nebulavision.data.model.PokemonPageEntry
import com.nebulavision.data.repository.datasource.PokemonRemoteDataSource
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOError
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject


class PokemonRepository @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val pokemonDao: PokemonDao
) {

    suspend fun getPokemons(page: Int = 1, limit: Int = 20): ResponseResource<List<PokemonEntity>> {
        val offset = (page-1) * limit

        val roomPokemons = pokemonDao.getPokemons(offset, limit).first()

        return if(roomPokemons.isEmpty() || roomPokemons.size < limit) {
            val networkResource = getPokemonPage(offset, limit)

            if(networkResource is ResponseResource.Success){
                val pokemons = networkResource.data!!
                pokemons.forEach { pokemonDao.insert(it) }
            }

            networkResource
        }else{
            ResponseResource.Success(roomPokemons)
        }
    }

    private suspend fun getPokemonPage(offset: Int, limit: Int = 20): ResponseResource<List<PokemonEntity>>{
        return try{
            val response = remoteDataSource.getPokemonPage(offset, limit)


            if(offset < response.count){
                val pokemonsEntities = response.results.map { result -> mapToPokemonEntity(result) }

                ResponseResource.Success(pokemonsEntities)
            }else{
                ResponseResource.Error("No hay más items")
            }
        }catch(e: IOException){
            handleException(e)
        }
    }

    private fun handleException(e: Exception): ResponseResource<List<PokemonEntity>>{
        return when(e){
            is IOException -> {
                if(e is SocketTimeoutException)
                    ResponseResource.Error("Timeout", ErrorType.TIMEOUT)
                else
                    ResponseResource.Error("Connection Error: ${e.message}", ErrorType.NO_INTERNET)
            }
            is HttpException -> {
                val errorResponse = e.response()
                val code = errorResponse?.code()

                if(code == 404){
                    ResponseResource.Error("Error 404: $errorResponse", ErrorType.ERROR_404)
                }else{
                    ResponseResource.Error("Error: ${e.message}")
                }
            }
            is JsonParseException -> ResponseResource.Error("Error: ${e.message}", ErrorType.JSON_PARSE)

            else -> ResponseResource.Error("Error: ${e.message}")
        }
    }

    private suspend fun mapToPokemonEntity(result: PokemonPageEntry): PokemonEntity {
        val pokemonId = extractPokemonId(result.url)

        val pokemonDetailsApiResponse = remoteDataSource.getPokemonDetailApiResponse(pokemonId)

        val pokemonTypes = mutableListOf<String>()
        pokemonDetailsApiResponse.types.forEach { type ->
            pokemonTypes.add(type.type.name)
        }

        val pokemonEntity = PokemonEntity(
            id = pokemonDetailsApiResponse.id,
            name = pokemonDetailsApiResponse.forms[0].name,
            imageUrl = "https://img.pokemondb.net/sprites/home/normal/${pokemonDetailsApiResponse.forms[0].name}.png",
            types = pokemonTypes
        )
        return pokemonEntity
    }

    private fun extractPokemonId(url: String): Int {
        val regex = """.*/(\d+)/?$""".toRegex()

        return regex.find(url)!!.groupValues[1].toInt()
    }
}