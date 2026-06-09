package com.example.alpplay.presentation.playerScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Button
import androidx.tv.material3.Text
import com.example.alpplay.domain.model.Channel
import kotlinx.coroutines.delay

@Composable
fun PlayListSideDrawer(
    isVisible: Boolean,
    channels: List<Channel>,
    currentChannel: Channel?,
    onChannelSelected: (Channel) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activeItemFocusRequester = remember { FocusRequester() }
    val listState = rememberLazyListState()

    val activeIndex = channels.indexOf(currentChannel).takeIf { it >= 0 } ?: 0
    var hasRequestedFocus by remember { mutableStateOf(false) }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            hasRequestedFocus = false
            listState.scrollToItem(activeIndex)
            delay(150)
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(initialOffsetX = { -it }),
        exit = slideOutHorizontally(targetOffsetX = { -it }),
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.35f)
                .background(Color.Black.copy(alpha = 0.9f))
                .padding(16.dp)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .onKeyEvent { event ->
                        if (event.key == Key.Back || event.key == Key.DirectionRight) {
                            onBack()
                            true
                        } else {
                            false
                        }
                    }

            ) {
                itemsIndexed(channels) { index, channel ->
                    val isFocused = index == activeIndex

                    Button(
                        onClick = { onChannelSelected(channel) },
                        modifier = Modifier.padding(vertical = 4.dp)
                            .then(if (isFocused) Modifier.focusRequester(activeItemFocusRequester) else Modifier)
                            .onGloballyPositioned {
                                if (isFocused && isVisible && !hasRequestedFocus) {
                                    activeItemFocusRequester.requestFocus()
                                    hasRequestedFocus = true
                                }
                        }
                    ) {
                        Text(
                            text = channel.name,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
