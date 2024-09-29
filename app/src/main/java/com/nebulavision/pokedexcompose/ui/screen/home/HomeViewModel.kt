package com.nebulavision.pokedexcompose.ui.screen.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nebulavision.pokedexcompose.data.repository.PokemonNewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsRepository: PokemonNewsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init{
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                pokemonNews = newsRepository.getAll(),
                isLoading = false
            )

        }
    }

    fun onSearchTextChanged(searchText: String){
        _uiState.value = _uiState.value.copy(searchText = searchText)
    }

    fun onSearchAction(){

    }

    fun openPokemonUrl(context: Context, url: String){
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
        )
    }
}