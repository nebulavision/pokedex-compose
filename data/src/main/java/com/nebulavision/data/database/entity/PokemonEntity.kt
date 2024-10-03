package com.nebulavision.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("image_url")
    val imageUrl: String,
    @ColumnInfo("types")
    val types: List<String>
)
