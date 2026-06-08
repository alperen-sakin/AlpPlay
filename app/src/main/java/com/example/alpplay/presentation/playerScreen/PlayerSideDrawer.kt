package com.example.alpplay.presentation.playerScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ListItem
import androidx.tv.material3.Text
import com.example.alpplay.domain.model.Channel

@Composable
fun PlayListSideDrawer(
    isVisible: Boolean,
    channels: List<Channel>,
    onChannelSelected: (Channel) -> Unit,
    modifier: Modifier = Modifier
) {
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
            LazyColumn {
                items(channels) { channel ->

                    Text(
                        text = channel.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onChannelSelected(channel) }
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}