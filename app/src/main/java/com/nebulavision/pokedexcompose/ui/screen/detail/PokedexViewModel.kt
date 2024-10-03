package com.nebulavision.pokedexcompose.ui.screen.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nebulavision.data.model.ResponseResource
import com.nebulavision.data.repository.PokemonRepository
import com.nebulavision.pokedexcompose.model.toPokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel(){
    private val _uiState = MutableStateFlow(PokedexUiState())
    val uiState: StateFlow<PokedexUiState> = _uiState.asStateFlow()

    private var currentPage = 1

    init{
        fetchMore()
    }

    fun fetchMore(){
        _uiState.value = _uiState.value.copy(
            isLoading = true
        )
        viewModelScope.launch(Dispatchers.IO) {
            val pokemonResource = pokemonRepository.getPokemons(currentPage++)

            if(pokemonResource is ResponseResource.Success){
                _uiState.value = _uiState.value.copy(
                    pokemons = _uiState.value.pokemons + pokemonResource.data!!.map { it.toPokemon() },
                    isLoading = false
                )
            }else if(pokemonResource is ResponseResource.Error){
                val errorCode = pokemonResource.error
                _uiState.value = _uiState.value.copy(
                    error = pokemonResource.message!!,
                    isLoading = false
                )
            }
        }
    }

    /*fun fetchMore(){
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                pokemons = _uiState.value.pokemons
            )

            _uiState.value = _uiState.value.copy(
                pokemons = _uiState.value.pokemons + pokemonRepository.getPokemons(++currentPage).map { it.toPokemon() },
                isLoading = false
            )
        }
    }*/
}