package com.nebulavision.pokedexcompose.ui.screen.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nebulavision.data.model.Resource
import com.nebulavision.data.repository.PokemonRepository
import com.nebulavision.pokedexcompose.isNetworkAvailable
import com.nebulavision.pokedexcompose.model.toPokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null


    fun onSearchTextChanged(isNetworkAvailable: Boolean, query: String){
        _uiState.value = _uiState.value.copy(searchText = query, pokemonSearch = emptyList())
        if(query.length >= 3) {
            searchPokemon(isNetworkAvailable, query)
        }
    }

    fun openPokemonUrl(context: Context, url: String){
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
        )
    }

    private fun searchPokemon(isNetworkAvailable: Boolean, query: String){
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.Default) {

            val pokemonResource = pokemonRepository.query(query, isNetworkAvailable)

            if(pokemonResource is Resource.Success){
                _uiState.value = _uiState.value.copy(pokemonSearch = pokemonResource.data!!.map { it.toPokemon() })
            }else{
                _uiState.value = _uiState.value.copy(pokemonSearch = emptyList())
            }
        }
    }
}