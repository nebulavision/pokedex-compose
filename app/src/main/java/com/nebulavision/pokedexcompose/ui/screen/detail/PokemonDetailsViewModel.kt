package com.nebulavision.pokedexcompose.ui.screen.detail

import android.app.Application
import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nebulavision.data.model.Resource
import com.nebulavision.data.repository.PokemonRepository
import com.nebulavision.pokedexcompose.isNetworkAvailable
import com.nebulavision.pokedexcompose.model.Pokemon
import com.nebulavision.pokedexcompose.ui.screen.NavArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    context: Application,
    private val pokemonRepository: PokemonRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val pokemon: Pokemon = savedStateHandle[NavArgs.Pokemon.key]!!

    private val _uiState = MutableStateFlow(PokemonDetailsUiState(pokemon = pokemon))
    val uiState: StateFlow<PokemonDetailsUiState> = _uiState.asStateFlow()

    init{
        viewModelScope.launch(Dispatchers.Default){
            val previousPokemonEntityResource =
                pokemonRepository.getPokemon(pokemon.id.toInt() - 1, context.isNetworkAvailable)
            val previousPokemonImageUrl = if (previousPokemonEntityResource is Resource.Success) {
                "https://img.pokemondb.net/sprites/home/normal/${previousPokemonEntityResource.data!!.name.lowercase()}.png"
            } else null

            val nextPokemonEntityResource =
                pokemonRepository.getPokemon(pokemon.id.toInt() + 1, context.isNetworkAvailable)
            val nextPokemonImageUrl = if (nextPokemonEntityResource is Resource.Success) {
                "https://img.pokemondb.net/sprites/home/normal/${nextPokemonEntityResource.data!!.name.lowercase()}.png"
            } else null

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                previousPokemonImageUrl = previousPokemonImageUrl,
                pokemon = pokemon,
                nextPokemonImageUrl = nextPokemonImageUrl
            )
        }
    }
}