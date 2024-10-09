package com.nebulavision.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nebulavision.data.database.entity.PokemonEntity


@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pokemon: PokemonEntity)

    @Query("SELECT * FROM pokemon ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun getPokemons(offset: Int, limit: Int): List<PokemonEntity>

    @Query("SELECT * FROM pokemon WHERE name LIKE :pokemonName")
    fun getPokemonByName(pokemonName: String): List<PokemonEntity>

    @Query("SELECT * FROM pokemon WHERE id == :pokemonId")
    fun getPokemonById(pokemonId: Int): PokemonEntity?
}