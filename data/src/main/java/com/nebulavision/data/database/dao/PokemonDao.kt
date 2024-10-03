package com.nebulavision.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nebulavision.data.database.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pokemon: PokemonEntity)

    @Query("SELECT * FROM pokemon ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun getPokemons(offset: Int, limit: Int): Flow<List<PokemonEntity>>
}