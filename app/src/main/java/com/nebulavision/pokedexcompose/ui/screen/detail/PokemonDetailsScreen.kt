package com.nebulavision.pokedexcompose.ui.screen.detail

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.nebulavision.pokedexcompose.R
import com.nebulavision.pokedexcompose.getBackgroundColor
import com.nebulavision.pokedexcompose.isNetworkAvailable
import com.nebulavision.pokedexcompose.model.Pokemon
import com.nebulavision.pokedexcompose.ui.theme.PokedexComposeTheme
import kotlinx.coroutines.launch

@Composable
fun PokemonDetailsScreen(
    onBackClicked: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: PokemonDetailsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PokedexComposeTheme {
        PokedexComposeTheme {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    AppBar(
                        backgroundColor = uiState.pokemon?.getBackgroundColor() ?: Color.Transparent
                    ) { onBackClicked() }
                }
            ) {
                if (uiState.isLoading) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CircularProgressIndicator(modifier = Modifier.padding(it))
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .background(uiState.pokemon!!.getBackgroundColor())
                            .verticalScroll(rememberScrollState())
                    ) {
                        Header(
                            pokemon = uiState.pokemon
                                ?: throw Exception("Error in PokemonDetailsScreen::PokemonDetailsScreen()")
                        )
                        PokemonImages(
                            previousPokemonUrl = uiState.previousPokemonImageUrl,
                            currentPokemonUrl = uiState.pokemon!!.imageUrl,
                            nextPokemonUrl = uiState.nextPokemonImageUrl
                        )
                        DataCard()

                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(modifier: Modifier = Modifier, backgroundColor: Color, onBackClicked: () -> Unit) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    //Remove in future
    var isFavoriteSelected by rememberSaveable { mutableStateOf(false) }

    TopAppBar(
        modifier = modifier,
        title = {},
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = backgroundColor
        ),
        navigationIcon = {
            IconButton(
                onClick = onBackClicked
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Go back",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        actions = {
            IconButton(
                onClick = { isFavoriteSelected = !isFavoriteSelected }
            ) {
                Icon(
                    imageVector = if (isFavoriteSelected) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Mark as favourite",
                    tint = if (isFavoriteSelected) Color(0xFFE53935) else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    )
    Image(
        painter = painterResource(R.drawable.dotted),
        contentDescription = null,
        alpha = 0.3f,
        modifier = Modifier
            .offset(x = screenWidth - 140.dp)
    )
}

@Composable
private fun Header(modifier: Modifier = Modifier, pokemon: Pokemon) {
    Column(
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.small_padding))
    ) {
        PokemonData(pokemon = pokemon)
    }

}

@Composable
fun PokemonData(modifier: Modifier = Modifier, pokemon: Pokemon) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = pokemon.name,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.weight(1F))
            Text(
                text = "#${pokemon.id}",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.extra_small_padding)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            pokemon.types.forEach {
                PokemonTypeEntry(typeRes = it)
            }
            Spacer(Modifier.weight(1F))
            Text(
                style = MaterialTheme.typography.labelMedium,
                text = pokemon.genus
            )
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
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.bodySmall
    )
}

@Composable
private fun PokemonImages(
    previousPokemonUrl: String?,
    currentPokemonUrl: String,
    nextPokemonUrl: String?
) {
    val pokemonImageWidth = 200.dp
    val pokemonImageHeight = 200.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(1F),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.pokeball),
            contentDescription = null,
            alpha = 0.4F,
            modifier = Modifier
                .size(pokemonImageWidth - 50.dp, pokemonImageHeight - 50.dp)
                .offset(y = 20.dp)
                .scale(1.1F)
        )
        previousPokemonUrl?.let {
            AsyncImage(
                model = previousPokemonUrl,
                contentDescription = null,
                alpha = 0.4F,
                colorFilter = ColorFilter.tint(Color(0xFF000000)),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(100.dp)
                    .offset(x = (-52).dp)
                    .zIndex(5f)
            )
        }

        AsyncImage(
            model = currentPokemonUrl,
            contentDescription = null,
            modifier = Modifier
                .size(pokemonImageWidth, pokemonImageHeight)
                .offset(y = 0.dp)
                .zIndex(5f)
                .scale(1.1F)
        )

        nextPokemonUrl?.let {
            AsyncImage(
                model = nextPokemonUrl,
                contentDescription = null,
                alpha = 0.4F,
                colorFilter = ColorFilter.tint(Color(0xFF000000)),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(100.dp)
                    .offset(x = 60.dp)
                    .zIndex(5f)
            )
        }
    }
}

@Composable
fun DataCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        //colors = CardDefaults.cardColors(Color.White),
        shape = MaterialTheme.shapes.extraLarge.copy(
            bottomStart = CornerSize(0.dp),
            bottomEnd = CornerSize(0.dp)
        )
    ) {
        PokemonDetailsPager()
    }
}

@Composable
private fun PokemonDetailsPager(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val pagerState = rememberPagerState(pageCount = { PokemonDetailsTabs.entries.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Surface(modifier = modifier.fillMaxWidth()) {
        Column {
            TabRow(
                selectedTabIndex = selectedTabIndex.value,
                modifier = Modifier.fillMaxWidth()
            ) {
                PokemonDetailsTabs.entries.forEachIndexed { index, currentTab ->
                    Tab(
                        selected = selectedTabIndex.value == index,
                        selectedContentColor = MaterialTheme.colorScheme.onSurface,
                        unselectedContentColor = Color(0xFFA9A9A9),
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(currentTab.ordinal)
                            }
                        },
                        text = {
                            Text(
                                text = with(context) { currentTab.getText() },
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.ExtraBold
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        with(context) {
                            if (isLandscape) {
                                stringResource(PokemonDetailsTabs.entries[selectedTabIndex.value].textLandscapeId!!)
                            } else {
                                PokemonDetailsTabs.entries[selectedTabIndex.value].getText()
                            }
                        }
                    )
                }
            }
        }
    }
}