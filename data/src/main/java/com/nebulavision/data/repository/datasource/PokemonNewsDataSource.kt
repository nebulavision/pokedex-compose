package com.nebulavision.data.repository.datasource


import com.nebulavision.data.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

//Api key by constructor if necessary
class PokemonNewsDataSource{
    suspend fun getPokemonNews(): List<Pokemon> {
        return withContext(Dispatchers.IO) {
            delay(3000)
            listOf(
                Pokemon(
                    "Las ilustraciones de la expansión Escarlata y Púrpura-Corona Astral de JCC Pokémon",
                    "27  de septiembre de 2024",
                    "https://www.pokemon.com/static-assets/content-assets/cms2-es-es/img/trading-card-game/_tiles/sv/sv07/art-highlights/sv07-art-highlights-169-es.png",
                    ""
                ),
                Pokemon(
                    "Los Pokémon de tipo Dragón que dominaron el Campeonato Mundial Pokémon 2024",
                    "27 de septiembre de 2024",
                    "https://www.pokemon.com/static-assets/content-assets/cms2-es-es/img/misc/_tiles/2024/year-of-the-dragon/worlds/year-of-the-dragon-169-es.png",
                    ""
                ),
                Pokemon(
                    "Aprende a crear un equipo para el reglamento H de Pokémon Escarlata y Pokémon Púrpura",
                    "24 de septiembre de 2024",
                    "https://www.pokemon.com/static-assets/content-assets/cms2-es-es/img/video-games/_tiles/strategy/scarlet-violet/regulation-set-h/scarlet-violet-169-es.png",
                    ""
                ),
                Pokemon(
                    "Usa los datos de sueño de tu reloj inteligente en Pokémon Sleep",
                    "24 de septiembre de 2024",
                    "https://www.pokemon.com/static-assets/content-assets/cms2-es-es/img/video-games/_tiles/pokemon-sleep/2024/0924/pokemon-sleep-169-es.png",
                    ""
                ),
                Pokemon(
                    "Compite ya en el Gran Desafío I de 2025a",
                    "20 de septiembre de 2024",
                    "https://www.pokemon.com/static-assets/content-assets/cms2-es-es/img/video-games/_tiles/pokemon-scarlet-violet/events/2025/grand-challenge/01/scarlet-violet-169-es.png",
                    ""
                ),
                Pokemon(
                    "Las mejores cartas competitivas de Escarlata y Púrpura-Corona Astral de JCC Pokémon",
                    "20 de septiembre de 2024",
                    "https://www.pokemon.com/static-assets/content-assets/cms2/img/trading-card-game/_tiles/sv/sv07/top-cards/sv07-top-cards-169-en.png",
                    ""
                )
            )
        }
    }
}
