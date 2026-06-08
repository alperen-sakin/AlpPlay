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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Button
import androidx.tv.material3.Text
import com.example.alpplay.domain.model.Channel
import kotlinx.coroutines.delay

@Composable
fun PlayListSideDrawer(
    isVisible: Boolean,
    channels: List<Channel>,
    onChannelSelected: (Channel) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val firstItemFocusRequester = remember { FocusRequester() }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(100)
            try {
                firstItemFocusRequester.requestFocus()
            } catch (e: Exception) {
            }
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

                    Button(
                        onClick = { onChannelSelected(channel) },
                        modifier = Modifier.padding(vertical = 4.dp)
                            .then(if (index == 0) Modifier.focusRequester(firstItemFocusRequester) else Modifier)
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
