package com.nebulavision.pokedexcompose.ui.screen.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nebulavision.pokedexcompose.model.Pokemon
import com.nebulavision.pokedexcompose.model.parcelableTypeOf
import com.nebulavision.pokedexcompose.ui.screen.NavArgs
import com.nebulavision.pokedexcompose.ui.screen.Screen
import com.nebulavision.pokedexcompose.ui.screen.detail.PokemonDetailsScreen
import com.nebulavision.pokedexcompose.ui.screen.pokedex.PokedexScreen
import com.nebulavision.pokedexcompose.ui.screen.home.HomeScreen

@Composable
fun AppNavigation(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(Screen.Home.route){
            HomeScreen(
                onPokedexScreen = { navController.navigate(Screen.Pokedex.route) },
                onMovesScreen = {},
                onAbilitiesScreen = {},
                onItemsScreen = {},
                onLocationsScreen = {},
                onTypeChartsScreen = {}
            )
        }

        composable(Screen.Pokedex.route) {
            PokedexScreen(
                onBackClicked = { navController.popBackStack() },
                onPokemonClicked = {
                    navController.navigate(Screen.PokemonDetails.createRoute(it))
                }
            )
        }

        composable(
            route = Screen.PokemonDetails.route,
            arguments = listOf(navArgument(NavArgs.Pokemon.key){ type = parcelableTypeOf<Pokemon>() })
        ){
            PokemonDetailsScreen(onBackClicked = { navController.popBackStack() })
        }
    }
}

