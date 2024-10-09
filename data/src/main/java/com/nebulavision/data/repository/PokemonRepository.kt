package com.nebulavision.data.repository

import android.util.Log
import com.google.gson.JsonParseException
import com.nebulavision.data.database.dao.PokemonDao
import com.nebulavision.data.database.entity.PokemonEntity
import com.nebulavision.data.model.ErrorType
import com.nebulavision.data.model.Resource
import com.nebulavision.data.model.PokemonPageEntry
import com.nebulavision.data.repository.datasource.PokemonRemoteDataSource
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject


class PokemonRepository @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val pokemonDao: PokemonDao
) {

    suspend fun query(query: String, networkAvailable: Boolean, lang: String = "es"): Resource<List<PokemonEntity>> {
        val pokemonsToReturn = mutableListOf<PokemonEntity>()

        pokemonsToReturn.addAll(pokemonDao.getPokemonByName("%$query%"))

        if(!networkAvailable){
            return Resource.Success(pokemonsToReturn)
        }

        try {
            val response = remoteDataSource.getPokemonPage(offset = 0, limit = 5000)
            val mappedResponse = response.results
                .filter { it.name.contains(query, true) }
                .map { mapToPokemonEntity(it, lang) }

            mappedResponse.forEach {
                pokemonsToReturn.add(it)
                pokemonDao.insert(it)
            }
        } catch (e: Exception) {
            return handleException(e)
        }

        return Resource.Success(pokemonsToReturn.distinct())
    }

    suspend fun getPokemons(page: Int = 1, limit: Int = 20, networkAvailable: Boolean, lang: String = "es"): Resource<List<PokemonEntity>> {
        val offset = (page - 1) * limit

        val roomPokemons = pokemonDao.getPokemons(offset, limit)

        if(!networkAvailable){
            return Resource.Success(roomPokemons)
        }

        return if (roomPokemons.isEmpty() || roomPokemons.size < limit) {
            val networkResource = getPokemonPage(offset, limit, lang)

            if (networkResource is Resource.Success) {
                val pokemons = networkResource.data!!
                pokemons.forEach { pokemonDao.insert(it) }
            }

            networkResource
        } else {
            Resource.Success(roomPokemons)
        }
    }

    suspend fun getPokemon(pokemonId: Int, networkAvailable: Boolean, lang: String = "es"): Resource<PokemonEntity>{
        if(!networkAvailable){
            return Resource.Error("No Internet Connection")
        }

        return try{
            val roomPokemon = pokemonDao.getPokemonById(pokemonId)

            roomPokemon?.let {
                Resource.Success(roomPokemon)
            }
            val pokemon = remoteDataSource.getPokemon(pokemonId, lang)
            val pokemonTypes = mutableListOf<String>()

            pokemon.types.forEach { type ->
                pokemonTypes.add(type.type.name)
            }

            val genra = pokemon.genera.first { genra ->
                genra.language.name == "es"
            }

            val pokemonEntity = PokemonEntity(
                id = pokemon.id,
                name = pokemon.name,
                types = pokemonTypes,
                genus = genra.genus,
                imageUrl = "https://img.pokemondb.net/sprites/home/normal/${pokemon.enName}.png"
            )

            Resource.Success(pokemonEntity)
        }catch (e: Exception){
            handleException(e)
        }
    }

    private suspend fun getPokemonPage(offset: Int = 0, limit: Int = 20, lang: String): Resource<List<PokemonEntity>> {
        return try {
            val response = remoteDataSource.getPokemonPage(offset, limit)

            if (offset < response.count) {
                val pokemonsEntities = response.results.map { result -> mapToPokemonEntity(result, lang) }

                Resource.Success(pokemonsEntities)
            } else {
                Resource.Error("No hay más items")
            }
        } catch (e: IOException) {
            handleException(e)
        }
    }

    private fun <T> handleException(e: Exception): Resource<T> {
        return when (e) {
            is IOException -> {
                if (e is SocketTimeoutException)
                    Resource.Error("Timeout", ErrorType.TIMEOUT)
                else
                    Resource.Error("Connection Error: ${e.message}", ErrorType.NO_INTERNET)
            }

            is HttpException -> {
                val errorResponse = e.response()
                val code = errorResponse?.code()

                if (code == 404) {
                    Resource.Error("Error 404: $errorResponse", ErrorType.ERROR_404)
                } else {
                    Resource.Error("Error: ${e.message}")
                }
            }

            is JsonParseException -> Resource.Error("Error: ${e.message}", ErrorType.JSON_PARSE)

            else -> Resource.Error("Error: ${e.message}")
        }
    }

    private suspend fun mapToPokemonEntity(result: PokemonPageEntry, lang: String): PokemonEntity {
        val pokemonId = extractPokemonId(result.url)

        val pokemon = remoteDataSource.getPokemon(pokemonId, lang)
        val pokemonTypes = mutableListOf<String>()

        pokemon.types.forEach { type ->
            pokemonTypes.add(type.type.name)
        }

        val genra = pokemon.genera.first { genra ->
            genra.language.name == "es"
        }
        Log.d("REPO","https://img.pokemondb.net/sprites/home/normal/${normalizeNameForImageUrl(pokemon.enName)}.png")
        val pokemonEntity = PokemonEntity(
            id = pokemon.id,
            name = pokemon.name,
            imageUrl = "https://img.pokemondb.net/sprites/home/normal/${normalizeNameForImageUrl(pokemon.enName)}.png",
            genus = genra.genus,
            types = pokemonTypes
        )
        return pokemonEntity
    }

    private fun extractPokemonId(url: String): Int {
        val regex = """.*/(\d+)/?$""".toRegex()

        return regex.find(url)!!.groupValues[1].toInt()
    }

    private fun normalizeNameForImageUrl(pokemonName: String): String{
        return when{
            pokemonName.contains("♂") -> pokemonName.replace("♂", "-m")
            pokemonName.contains("♀") -> pokemonName.replace("♀", "-f")
            pokemonName.contains(". ") -> pokemonName.replace(". ", "-")
            else -> pokemonName
        }
    }
}