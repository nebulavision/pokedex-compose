package com.nebulavision.pokedexcompose.ui.screen.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun <T> EndlessLazyVerticalGrid(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    minColumnWidth: Dp,
    horizontalArrangement: Arrangement.Horizontal,
    verticalArrangement: Arrangement.Vertical,
    items: List<T>,
    itemKey: (T) -> Any,
    itemContent: @Composable (T) -> Unit,
    loadMore: () -> Unit,
    isLoading: Boolean = false,
    loadingItem: @Composable () -> Unit
){
    val listState = rememberLazyGridState()
    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }
    LaunchedEffect(reachedBottom) {
        if (reachedBottom){
            loadMore()
        }
    }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val columnCount = (screenWidth / minColumnWidth).toInt()

    LazyVerticalGrid(
        state = listState,
        modifier = modifier,
        contentPadding = contentPadding,
        columns = GridCells.Adaptive(minColumnWidth),
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement
    ) {
        items(
            items = items,
            key = { item: T -> itemKey(item) }
        ){
            itemContent(it)
        }

        if(isLoading){
            item(span = { GridItemSpan(columnCount) }){
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ){
                    loadingItem()
                }
            }
        }
    }
}