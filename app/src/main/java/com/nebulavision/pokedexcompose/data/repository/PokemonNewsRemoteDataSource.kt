package com.nebulavision.pokedexcompose.data.repository

import com.nebulavision.pokedexcompose.R
import com.nebulavision.pokedexcompose.data.PokemonNew
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

//Api key by constructor if necessary
class PokemonNewsRemoteDataSource{
    suspend fun getPokemonNews(): List<PokemonNew> {
        return withContext(Dispatchers.IO) {
            delay(3000)
            listOf(
                PokemonNew(
                    "Las ilustraciones de la expansión Escarlata y Púrpura-Corona Astral de JCC Pokémon",
                    "27  de septiembre de 2024",
                    R.drawable.new1_preview,
                    R.string.pokemon_new1
                ),
                PokemonNew(
                    "Los Pokémon de tipo Dragón que dominaron el Campeonato Mundial Pokémon 2024",
                    "27 de septiembre de 2024",
                    R.drawable.new2_preview,
                    R.string.pokemon_new2
                ),
                PokemonNew(
                    "Aprende a crear un equipo para el reglamento H de Pokémon Escarlata y Pokémon Púrpura",
                    "24 de septiembre de 2024",
                    R.drawable.new3_preview,
                    R.string.pokemon_new3
                ),
                PokemonNew(
                    "Usa los datos de sueño de tu reloj inteligente en Pokémon Sleep",
                    "24 de septiembre de 2024",
                    R.drawable.new4_preview,
                    R.string.pokemon_new4
                ),
                PokemonNew(
                    "Compite ya en el Gran Desafío I de 2025a",
                    "20 de septiembre de 2024",
                    R.drawable.new5_preview,
                    R.string.pokemon_new5
                ),
                PokemonNew(
                    "Las mejores cartas competitivas de Escarlata y Púrpura-Corona Astral de JCC Pokémon",
                    "20 de septiembre de 2024",
                    R.drawable.new6_preview,
                    R.string.pokemon_new6
                )
            )
        }
    }
}
