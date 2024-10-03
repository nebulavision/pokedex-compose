package com.nebulavision.pokedexcompose.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nebulavision.pokedexcompose.ui.screen.detail.PokedexScreen
import com.nebulavision.pokedexcompose.ui.screen.home.HomeScreen

@Composable
fun AppNavigation(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screens.HOME.name
    ){
        composable(Screens.HOME.name){
            HomeScreen(
                onPokedexScreen = { navController.navigate(Screens.POKEDEX.name) },
                onMovesScreen = {},
                onAbilitiesScreen = {},
                onItemsScreen = {},
                onLocationsScreen = {},
                onTypeChartsScreen = {}
            )
        }

        composable(Screens.POKEDEX.name) {
            PokedexScreen(
                onBackClicked = { navController.popBackStack() }
            )
        }
    }
}