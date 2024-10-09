package com.nebulavision.pokedexcompose.ui.screen.home

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.nebulavision.pokedexcompose.R
import com.nebulavision.pokedexcompose.isNetworkAvailable
import com.nebulavision.pokedexcompose.model.Pokemon
import com.nebulavision.pokedexcompose.toast
import com.nebulavision.pokedexcompose.ui.screen.common.CircularProgressBox
import com.nebulavision.pokedexcompose.ui.screen.common.ExpandableSearchBar

@Composable
fun HomeScreen(
    onPokedexScreen: () -> Unit,
    onMovesScreen: () -> Unit,
    onAbilitiesScreen: () -> Unit,
    onItemsScreen: () -> Unit,
    onLocationsScreen: () -> Unit,
    onTypeChartsScreen: () -> Unit
){
    val viewModel: HomeViewModel = hiltViewModel()
    val homeUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val newsPokemonUrl = stringResource(R.string.news_pokemon_url)

    Scaffold{ paddingValues ->
        val focusManager = LocalFocusManager.current
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .pointerInput(Unit) {
                    // Detecta cualquier clic en la pantalla
                    detectTapGestures {
                        focusManager.clearFocus() // Quita el foco cuando se hace clic en cualquier parte
                    }
                }
        ){
            Column {
                HomeHeader(
                    title = { HomeTitle(text = R.string.home_title) },
                    searchBar = {
                        val pokemons = homeUiState.pokemonSearch
                        ExpandableSearchBar(
                            query = homeUiState.searchText,
                            onQueryChange = { viewModel.onSearchTextChanged(context.isNetworkAvailable, it) },
                            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.large_padding)),
                            focusManager = focusManager,
                            onDismiss = { viewModel.onSearchTextChanged(context.isNetworkAvailable, "") }
                        ){
                            pokemons.forEach {
                                PokemonSuggestionItem(
                                    pokemon = it,
                                    onClick = { pokemonId ->
                                        context.toast("ID: $pokemonId")
                                    }
                                )
                            }
                        }
                    },
                    sections = { HomeSections(
                        modifier = Modifier.fillMaxWidth(),
                        onPokedexScreen = onPokedexScreen,
                        onMovesScreen = onMovesScreen,
                        onAbilitiesScreen = onAbilitiesScreen,
                        onItemsScreen = onItemsScreen,
                        onLocationsScreen = onLocationsScreen,
                        onTypeChartsScreen = onTypeChartsScreen,
                        onNewsIntent = { viewModel.openPokemonUrl(context, newsPokemonUrl) }
                    ) }
                )
            }
            Image(
                painter = painterResource(R.drawable.pokeball),
                contentDescription = null,
                modifier = Modifier
                    .offset(x = 115.dp, y = (-115).dp)
                    .alpha(0.2f)
            )
        }
    }
}

@Composable
fun HomeTitle(modifier: Modifier = Modifier, @StringRes text: Int){
    Column(modifier = modifier) {
        Text(
            text = stringResource(text),
            style = MaterialTheme.typography.titleLarge)
    }
}



@Composable
fun PokemonSuggestionItem(pokemon: Pokemon, onClick: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.extra_small_padding)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.small_padding))
            .clickable { onClick(pokemon.id) }
    ) {
        AsyncImage(
            model = pokemon.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(32.dp)
        )
        Text(text = pokemon.name, modifier = Modifier.fillMaxWidth())
    }
}


@Composable
fun HomeHeader(modifier: Modifier = Modifier, title: @Composable () -> Unit, searchBar: @Composable () -> Unit, sections: @Composable () -> Unit){
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp),
        elevation = CardDefaults.elevatedCardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.medium_padding))
        ) {
            title()
            searchBar()
            sections()

        }
    }
}

@Composable
fun HomeSections(
    modifier: Modifier = Modifier,
    onPokedexScreen: () -> Unit,
    onMovesScreen: () -> Unit,
    onAbilitiesScreen: () -> Unit,
    onItemsScreen: () -> Unit,
    onLocationsScreen: () -> Unit,
    onTypeChartsScreen: () -> Unit,
    onNewsIntent: () -> Unit
){
    val smallPadding = dimensionResource(R.dimen.small_padding)
    Column(
        modifier = modifier.heightIn(max = 600.dp),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding))
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(smallPadding)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(smallPadding)) {
                HomeSection(
                    modifier = Modifier
                        .size(100.dp, 60.dp)
                        .weight(1f),
                    text = R.string.pokedex,
                    color = Color(0xFF4FC1A6),
                    onClick = onPokedexScreen
                )
                HomeSection(
                    modifier = Modifier
                        .size(100.dp, 60.dp)
                        .weight(1f),
                    text = R.string.moves,
                    color = Color(0xFFF7786B),
                    onClick = onMovesScreen
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(smallPadding)) {
                HomeSection(
                    modifier = Modifier
                        .size(100.dp, 60.dp)
                        .weight(1f),
                    text = R.string.abilities,
                    color = Color(0xFF58AAF6),
                    onClick = onAbilitiesScreen
                )
                HomeSection(
                    modifier = Modifier
                        .size(100.dp, 60.dp)
                        .weight(1f),
                    text = R.string.items,
                    color = Color(0xFFFFCE4B),
                    onClick = onItemsScreen
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(smallPadding)) {
                HomeSection(
                    modifier = Modifier
                        .size(100.dp, 60.dp)
                        .weight(1f),
                    text = R.string.locations,
                    color = Color(0xFF7C538C),
                    onClick = onLocationsScreen
                )
                HomeSection(
                    modifier = Modifier
                        .size(100.dp, 60.dp)
                        .weight(1f),
                    text = R.string.type_charts,
                    color = Color(0xFFB1736C),
                    onClick = onTypeChartsScreen
                )
            }
        }
        Column {
            Row(horizontalArrangement = Arrangement.spacedBy(smallPadding)) {
                HomeSection(
                    modifier = Modifier
                        .size(100.dp, 60.dp)
                        .weight(1f),
                    text = R.string.news_title,
                    color = Color(0xFF82BAF0),
                    onClick = onNewsIntent
                )
                Spacer(Modifier.weight(1F))
            }
        }
    }
}

@Composable
fun HomeSection(modifier: Modifier = Modifier, @StringRes text: Int, color: Color, onClick: () -> Unit){
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.CenterStart
    ){
        Image(
            painter = painterResource(R.drawable.pokeball_s),
            contentDescription = null,
            alignment = Alignment.TopStart,
            modifier = Modifier
                .size(100.dp)
                .offset(x = (-34).dp, y = (-33).dp)
                .alpha(0.2f)
        )
        Image(
            painter = painterResource(R.drawable.pokeball_s),
            contentDescription = null,
            alignment = Alignment.CenterEnd,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .fillMaxSize()
                .offset(x = 15.dp)
                .alpha(0.2f)
        )
        Text(
            text = stringResource(text),
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = dimensionResource(R.dimen.extra_small_padding))
        )
    }
}



/*
@Composable
fun HomeNews(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    onViewAllClick: () -> Unit,
    pokemonNews: List<PokemonNew>,
    onPokemonNewClick: (String) -> Unit
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(dimensionResource(R.dimen.extra_small_padding))
        ) {
            Text(
                text = stringResource(R.string.news_title),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = stringResource(R.string.view_all),
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.clickable { onViewAllClick() }
            )
        }

        if(isLoading){
            CircularProgressBox(modifier = Modifier.padding(top = 8.dp))
        }else{
            Column {
                pokemonNews.forEach{
                    val pokemonNewUrl = it.url
                    NewItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = dimensionResource(R.dimen.extra_small_padding)),
                        title = it.title,
                        date = it.date,
                        imageUrl = it.imageUrl,
                        onClick = { onPokemonNewClick(pokemonNewUrl) }
                    )
                }

            }
        }
    }
}

@Composable
fun NewItem(modifier: Modifier = Modifier, onClick: () -> Unit = {}, title: String, date: String, imageUrl: String){
    Card(
        modifier = modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(dimensionResource(R.dimen.small_padding))
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            Spacer(Modifier.width(4.dp))
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(140.dp, 80.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
        }
    }
}
*/


/*
 * =========================================================================================================================================================================
 *                                                                             PREVIEWS
 * =========================================================================================================================================================================
 */

//
//@Preview(showBackground = true)
//@Composable
//fun HomeTitlePreview(){
//    PokedexComposeTheme {
//        HomeTitle(text = R.string.home_title)
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun SearchBarPreview(){
//    PokedexComposeTheme {
//        SearchBar(
//            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.large_padding)),
//            searchText = "",
//            onSearchChanged = {},
//            onKeyboardDone = {}
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun NewItemPreview(){
//    PokedexComposeTheme {
//        NewItem(
//            title = "Aprende a crear un equipo para el reglamento H de Pokémon Escarlata y Pokémon Púrpura",
//            date = "24 de septiembre de 2024",
//            drawable = R.drawable.new2_preview
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun HomeSectionPreview(){
//    PokedexComposeTheme {
//        HomeSection(
//            text = R.string.pokedex,
//            color = Color(0xFF4FC1A6)
//        ){}
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun HomeSectionsPreview(){
//    PokedexComposeTheme {
//        HomeSections(
//            onPokedexScreen = {},
//            onMovesScreen = {},
//            onAbilitiesScreen = {},
//            onItemsScreen = {},
//            onLocationsScreen = {},
//            onTypeChartsScreen = {},
//            modifier = Modifier.padding(8.dp)
//        )
//    }
//}
//
//@Preview(showSystemUi = true)
//@Composable
//fun HomeHeaderPreview(){
//    PokedexComposeTheme {
//        HomeHeader(
//            title = { HomeTitle(text = R.string.home_title) },
//            searchBar = {
//                SearchBar(
//                    modifier = Modifier.padding(vertical = dimensionResource(R.dimen.large_padding)),
//                    searchText = "",
//                    onSearchChanged = {},
//                    onKeyboardDone = {}
//                )
//            },
//            sections = { HomeSections(
//                onPokedexScreen = {},
//                onMovesScreen = {},
//                onAbilitiesScreen = {},
//                onItemsScreen = {},
//                onLocationsScreen = {},
//                onTypeChartsScreen = {}
//            ) }
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview(){
//    PokedexComposeTheme {
//        HomeScreen(
//            onPokedexScreen = {},
//            onMovesScreen = {},
//            onAbilitiesScreen = {},
//            onItemsScreen = {},
//            onLocationsScreen = {},
//            onTypeChartsScreen = {}
//        )
//    }
//}

@Preview(showSystemUi = true)
@Composable
private fun CircularProgressBoxPreview() {
    CircularProgressBox()
}
