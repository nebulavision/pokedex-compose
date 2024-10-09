package com.nebulavision.pokedexcompose.model

import com.nebulavision.data.database.entity.PokemonEntity
import com.nebulavision.pokedexcompose.R
import com.nebulavision.pokedexcompose.capitalizeWithLocale
import com.nebulavision.pokedexcompose.padWithZeros

private fun mapPokemonTypes(pokemonNameList: List<String>): List<Int>{
    return pokemonNameList.map {
        when(it) {
            "normal" -> R.string.type_normal
            "fighting" -> R.string.type_fighting
            "flying" -> R.string.type_flying
            "poison" -> R.string.type_poison
            "ground" -> R.string.type_ground
            "rock" -> R.string.type_rock
            "bug" -> R.string.type_bug
            "ghost" -> R.string.type_ghost
            "steel" -> R.string.type_steel
            "fire" -> R.string.type_fire
            "water" -> R.string.type_water
            "grass" -> R.string.type_grass
            "electric" -> R.string.type_electric
            "psychic" -> R.string.type_psychic
            "ice" -> R.string.type_ice
            "dragon" -> R.string.type_dragon
            "dark" -> R.string.type_dark
            "fairy" -> R.string.type_fairy
            else -> R.string.type_dark
        }
    }.toList()
}

fun PokemonEntity.toPokemon(): Pokemon {
    val pokemonId = id.toString().padWithZeros()
    val image = imageUrl
    val pokemonTypes =  mapPokemonTypes(types)

    return Pokemon(
        id = pokemonId,
        name = normalizePokemonName(name),
        imageUrl = image,
        genus = genus,
        types = pokemonTypes
    )
}

private fun normalizePokemonName(pokemonName: String): String{

    if(pokemonName.contains("tapu", true)){
        val splittedName = pokemonName.split(("-"))

        return "${splittedName[0].capitalizeWithLocale()} ${splittedName[1].capitalizeWithLocale()}"
    }

    return when(pokemonName){
        "wo-chien" -> "Wo Chien"
        "chien-pao" -> "Chien Pao"
        "ting-lu" -> "Ting Lu"
        "chi-yu"  -> "Chi Yu"
        "roaring-moon" -> "Bramaluna"
        else -> pokemonName.capitalizeWithLocale()
    }
}
