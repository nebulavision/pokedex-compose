package com.github.nebulavision.pokedexcompose.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.nebulavision.pokedexcompose.ui.screen.home.HomeScreen

@Composable
fun AppNavigation(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screens.HOME.name
    ){
        composable(Screens.HOME.name){
            HomeScreen(
                onPokedexScreen = {},
                onMovesScreen = {},
                onAbilitiesScreen = {},
                onItemsScreen = {},
                onLocationsScreen = {},
                onTypeChartsScreen = {}
            )
        }
    }
}