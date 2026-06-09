package com.example.alpplay.presentation.home.sections.channelDashboard

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Card
import androidx.tv.material3.Text
import com.example.alpplay.domain.model.Channel
import com.example.alpplay.presentation.home.sections.channelDashboard.component.CategoryBox
import com.example.alpplay.presentation.home.viewModel.HomeState
import com.example.alpplay.ui.theme.ChosenBlue
import kotlinx.coroutines.delay

private const val GRID_SIZE = 4

@Composable
fun ChannelDashboard(
    modifier: Modifier = Modifier,
    state: HomeState,
    onCategoryFocused: (String) -> Unit,
    onChannelClick: (Channel) -> Unit,
) {
    var activeCategoryIndex by remember { mutableIntStateOf(0) }
    var isGridFocused by remember { mutableStateOf(false) }
    val categoriesFocusRequester = remember { FocusRequester() }
    val gridFocusRequester = remember { FocusRequester() }
    var isInitialLaunch by remember { mutableStateOf(true) }

    BackHandler(enabled = isGridFocused) {
        isGridFocused = false
    }

    Row(
        modifier = modifier
            .fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = !isGridFocused,
            enter = expandHorizontally(),
            exit = shrinkHorizontally()
        ) {
            LazyColumn(
                modifier = Modifier
                    .width(208.dp)
                    .fillMaxHeight()
                    .padding(end = 16.dp)
                    .background(ChosenBlue.copy(alpha = 0.5f)),
                verticalArrangement = spacedBy(12.dp, Alignment.CenterVertically),

                ) {
                items(state.tvCategories.size) { index ->
                    val category = state.tvCategories[index]
                    val isSelected = activeCategoryIndex == index

                    CategoryBox(
                        modifier = if (isSelected) Modifier.focusRequester(categoriesFocusRequester) else Modifier,
                        isSelected = isSelected,
                        category = category,
                        onClick = {
                            activeCategoryIndex = index
                            onCategoryFocused(category)
                            isGridFocused = true
                        },
                        onFocusChanged = { isFocused ->
                            if (isFocused) {
                                activeCategoryIndex = index
                                onCategoryFocused(category)
                            }
                        }
                    )
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(GRID_SIZE),
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .onFocusChanged { focusState ->
                    if (focusState.hasFocus && !isGridFocused && !isInitialLaunch) {
                        isGridFocused = true
                    }
                },
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.channels.size) { index ->
                val channel = state.channels[index]
                ChannelCard(
                    modifier = Modifier
                        .then(if (index == 0) Modifier.focusRequester(gridFocusRequester) else Modifier)
                        .onKeyEvent { keyEvent ->
                            if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionLeft) {
                                if (index % GRID_SIZE == 0) {
                                    isGridFocused = false
                                    return@onKeyEvent true
                                }
                            }
                            false
                        },
                    onChannelClick = onChannelClick,
                    channel = channel
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        delay(300)

        try {
            categoriesFocusRequester.requestFocus()
        } catch (e: Exception) {
        }
        isInitialLaunch = false
    }

    LaunchedEffect(isGridFocused) {
        if (!isInitialLaunch) {
            try {
                if (isGridFocused) {
                    gridFocusRequester.requestFocus()
                } else {
                    categoriesFocusRequester.requestFocus()
                }
            } catch (e: Exception) {}
        }
    }


}

private const val RATIO = 16f / 9f

@Composable
private fun ChannelCard(
    modifier: Modifier = Modifier,
    onChannelClick: (Channel) -> Unit,
    channel: Channel
) {
    Card(
        onClick = { onChannelClick(channel) },
        modifier = modifier
            .aspectRatio(RATIO)
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
