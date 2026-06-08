package com.example.alpplay.presentation.home.sections

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Card
import androidx.tv.material3.Text
import com.example.alpplay.presentation.home.viewModel.HomeState
import com.example.alpplay.ui.theme.Emerald

@Composable
fun ChannelDashboard(
    modifier: Modifier = Modifier,
    state: HomeState,
    onCategoryFocused: (String) -> Unit,
    onChannelClick: (String) -> Unit,
) {


    var activeCategoryIndex by remember { mutableIntStateOf(0) }
    var isGridFocused by remember { mutableStateOf(false) }
    val categoriesFocusRequester = remember { FocusRequester() }

    BackHandler(enabled = isGridFocused) {
        isGridFocused = false
        categoriesFocusRequester.requestFocus()
    }

    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 24.dp, bottom = 24.dp, end = 24.dp)
    ) {
        AnimatedVisibility(
            visible = !isGridFocused,
            enter = expandHorizontally(),
            exit = shrinkHorizontally()
        ) {
            LazyColumn(
                modifier = Modifier
                    .width(240.dp)
                    .fillMaxHeight()
                    .focusRequester(categoriesFocusRequester),
                verticalArrangement = spacedBy(12.dp)
            ) {
                items(state.tvCategories.size) { index ->
                    val category = state.tvCategories[index]
                    val isFocused = activeCategoryIndex == index

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isFocused) Color.White.copy(alpha = 0.2f) else Color.Transparent)

                            .onFocusChanged {
                                if (it.isFocused) {
                                    activeCategoryIndex = index
                                    onCategoryFocused(category)
                                }
                            }
                            .focusable()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = category,
                            color = if (isFocused) Emerald else Color.White.copy(alpha = 0.7f)
                        )
                    }
                }

            }
        }


        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .onFocusChanged{
                    isGridFocused = it.hasFocus
                },
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.channels.size) { index ->
                val channel = state.channels[index]
                Card(
                    onClick = { onChannelClick(channel.streamUrl) },
                    modifier = Modifier
                        .aspectRatio(16f / 9f)
                        .border(
                            width = 1.dp,
                            color = Color.White.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White.copy(alpha = 0.05f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = channel.name, color = Color.White)
                    }
                }
            }
        }
    }

}