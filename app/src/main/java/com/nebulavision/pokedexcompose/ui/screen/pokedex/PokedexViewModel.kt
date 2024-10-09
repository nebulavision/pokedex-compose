package com.nebulavision.pokedexcompose.ui.screen.pokedex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nebulavision.data.model.Resource
import com.nebulavision.data.repository.PokemonRepository
import com.nebulavision.pokedexcompose.model.toPokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel(){
    private val _uiState = MutableStateFlow(PokedexUiState())
    val uiState: StateFlow<PokedexUiState> = _uiState.asStateFlow()

    private var currentPage = 1

    fun fetchMore(isNetworkAvailable: Boolean, lang: String){
        _uiState.value = _uiState.value.copy(
            isLoading = true
        )
        viewModelScope.launch(Dispatchers.Default) {
            val pokemonResource = pokemonRepository.getPokemons(currentPage++, networkAvailable = isNetworkAvailable, lang = lang)

            if(pokemonResource is Resource.Success){
                _uiState.value = _uiState.value.copy(
                    pokemons = _uiState.value.pokemons + pokemonResource.data!!.map { it.toPokemon() },
                    error = null,
                    isLoading = false
                )
            }else if(pokemonResource is Resource.Error){
                //val errorCode = pokemonResource.error
                _uiState.value = _uiState.value.copy(
                    error = pokemonResource.message!!,
                    isLoading = false
                )
            }
        }
    }
}