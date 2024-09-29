package com.github.nebulavision.pokedexcompose.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.github.nebulavision.pokedexcompose.R

data class New(
    val title: String,
    val date: String,
    @DrawableRes val imageRes: Int,
    @StringRes val url: Int
)

object NewsDataSource{
    val news = listOf(
        New("Las ilustraciones de la expansión Escarlata y Púrpura-Corona Astral de JCC Pokémon", "27  de septiembre de 2024", R.drawable.new1_preview, R.string.pokemon_new1),
        New("Los Pokémon de tipo Dragón que dominaron el Campeonato Mundial Pokémon 2024", "27 de septiembre de 2024", R.drawable.new2_preview, R.string.pokemon_new2),
        New("Aprende a crear un equipo para el reglamento H de Pokémon Escarlata y Pokémon Púrpura", "24 de septiembre de 2024", R.drawable.new3_preview, R.string.pokemon_new3),
        New("Usa los datos de sueño de tu reloj inteligente en Pokémon Sleep", "24 de septiembre de 2024", R.drawable.new4_preview, R.string.pokemon_new4),
        New("Compite ya en el Gran Desafío I de 2025a", "20 de septiembre de 2024", R.drawable.new5_preview, R.string.pokemon_new5),
        New("Las mejores cartas competitivas de Escarlata y Púrpura-Corona Astral de JCC Pokémon", "20 de septiembre de 2024", R.drawable.new6_preview, R.string.pokemon_new6)
    )
}
