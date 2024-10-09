package com.nebulavision.pokedexcompose

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.nebulavision.pokedexcompose.model.Pokemon
import com.nebulavision.pokedexcompose.ui.theme.colorMap

fun String.padWithZeros() = padStart(4, '0')

fun String.capitalizeWithLocale() = capitalize(Locale.current)


fun Context.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

val Context.isNetworkAvailable: Boolean
    get() {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return getCurrentConnectivityState(cm)
    }

private fun getCurrentConnectivityState(connectivityManager: ConnectivityManager): Boolean {
    val nw = connectivityManager.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false

    return when{
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
        else -> false
    }
}

fun Pokemon.getBackgroundColor(): Color {
    return when (types[0]) {
        R.string.type_normal -> colorMap["type_normal"]!!
        R.string.type_fire -> colorMap["type_fire"]!!
        R.string.type_water -> colorMap["type_water"]!!
        R.string.type_electric -> colorMap["type_electric"]!!
        R.string.type_grass -> colorMap["type_grass"]!!
        R.string.type_ice -> colorMap["type_ice"]!!
        R.string.type_fighting -> colorMap["type_fighting"]!!
        R.string.type_poison -> colorMap["type_poison"]!!
        R.string.type_ground -> colorMap["type_ground"]!!
        R.string.type_flying -> colorMap["type_flying"]!!
        R.string.type_psychic -> colorMap["type_psychic"]!!
        R.string.type_bug -> colorMap["type_bug"]!!
        R.string.type_rock -> colorMap["type_rock"]!!
        R.string.type_ghost -> colorMap["type_ghost"]!!
        R.string.type_dragon -> colorMap["type_dragon"]!!
        R.string.type_dark -> colorMap["type_dark"]!!
        R.string.type_steel -> colorMap["type_steel"]!!
        else -> colorMap["type_fairy"]!!
    }
}