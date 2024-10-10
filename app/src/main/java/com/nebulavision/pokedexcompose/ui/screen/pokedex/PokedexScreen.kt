package com.nebulavision.pokedexcompose.ui.screen.pokedex

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.nebulavision.pokedexcompose.R
import com.nebulavision.pokedexcompose.getBackgroundColor
import com.nebulavision.pokedexcompose.isNetworkAvailable
import com.nebulavision.pokedexcompose.model.Pokemon
import com.nebulavision.pokedexcompose.ui.screen.common.CircularProgressBox
import com.nebulavision.pokedexcompose.ui.screen.common.EndlessLazyVerticalGrid
import com.nebulavision.pokedexcompose.ui.theme.PokedexComposeTheme
import kotlinx.coroutines.launch
import java.util.Locale


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PokedexScreen(
    onBackClicked: () -> Unit,
    onPokemonClicked: (pokemon: Pokemon) -> Unit
) {
    val viewmodel: PokedexViewModel = hiltViewModel()
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    viewmodel.fetchMore(context.isNetworkAvailable, Locale.getDefault().language)

    PokedexComposeTheme {
        Scaffold(
            modifier = Modifier,
            topBar = { AppTopBar { onBackClicked() } },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { padding ->
            EndlessLazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.small_padding)),
                contentPadding = padding,
                minColumnWidth = 150.dp,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.medium_padding)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.medium_padding)),
                items = uiState.pokemons,
                itemKey = { pokemon: Pokemon -> pokemon.id },
                itemContent = { pokemon: Pokemon ->
                    PokemonEntry(
                        pokemon = pokemon,
                        onClick = { onPokemonClicked(pokemon) }
                    )
                },
                loadMore = { viewmodel.fetchMore(context.isNetworkAvailable, Locale.getDefault().language) },
                isLoading = uiState.isLoading,
                loadingItem = {
                    CircularProgressBox(modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                    )
                }
            )

            if(uiState.error != null){
                LaunchedEffect(scope) {
                    scope.launch {
                        snackbarHostState.showSnackbar(uiState.error!!)
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonEntry(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    onClick: () -> Unit
) {
    val backgroundColor = pokemon.getBackgroundColor()
    Card (
        elevation = CardDefaults.cardElevation(20.dp),
        colors = CardDefaults.cardColors(backgroundColor),
        modifier = modifier.clickable { onClick() }
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.small_padding))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Text(
                    text = pokemon.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.offset(y = 10.dp)
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.labelSmall,
                    text = "#${pokemon.id}")
            }
            Row {
                Column(
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.extra_small_padding)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.small_padding))
                ) {
                    pokemon.types.forEach {
                        PokemonTypeEntry(typeRes = it)
                    }
                }
                Box(
                    contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(R.drawable.pokeball_s),
                        contentDescription = null,
                        alpha = 0.2F,
                        modifier = Modifier
                            .size(80.dp)
                            .offset(x = 24.dp, y = 28.dp)
                            .scale(1.6F)
                    )
                    AsyncImage(
                        model = pokemon.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .offset(x = 10.dp, y = 10.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonTypeEntry(
    modifier: Modifier = Modifier,
    @StringRes typeRes: Int
) {
    Text(
        modifier = modifier
            .background(Color.White.copy(alpha = 0.2f), MaterialTheme.shapes.extraLarge)
            .width(65.dp)
            .padding(4.dp),
        textAlign = TextAlign.Center,
        text = stringResource(typeRes),
        style = MaterialTheme.typography.bodySmall
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = stringResource(R.string.pokedex),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.go_back)
                )
            }
        }
    )
}


@Preview(showSystemUi = true, widthDp = 400)
@Composable
private fun PokedexScreenPreview() {
    PokedexComposeTheme {
        PokedexScreen(
            onBackClicked = {},
            onPokemonClicked = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun AppTopBarPreview(modifier: Modifier = Modifier) {
    PokedexComposeTheme {
        AppTopBar {  }
    }
}