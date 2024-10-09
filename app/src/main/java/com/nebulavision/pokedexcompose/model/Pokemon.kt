package com.nebulavision.pokedexcompose.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Pokemon(
    val id: String,
    val name: String,
    val imageUrl: String,
    val genus: String,
    val types: List<Int>
): Parcelable
