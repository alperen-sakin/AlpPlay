package com.example.alpplay.presentation.addLinkScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.Text
import com.example.alpplay.presentation.addLinkScreen.components.CustomTextField
import com.example.alpplay.presentation.addLinkScreen.components.CustomTextFieldState
import com.example.alpplay.ui.theme.BusinessNavy
import com.example.alpplay.ui.theme.Crimson
import com.example.alpplay.ui.theme.Emerald
import com.example.alpplay.ui.theme.Mirage
import com.example.alpplay.ui.theme.TextColor
import com.example.alpplay.ui.theme.TitleText

@Composable
fun AddLinkScreen(
    modifier: Modifier = Modifier,
    events: AddPlaylistScreenUIEvents,
    state: AddPlaylistScreenUIState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Mirage),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Add Playlist",
            textAlign = TextAlign.Center,
            color = TitleText,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Enter your M3U playlist details below",
            color = TextColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )

        ListDetailsInput(
            events = events,
            state = state
        )
    }
}

@Composable
fun ListDetailsInput(
    modifier: Modifier = Modifier,
    state: AddPlaylistScreenUIState,
    events: AddPlaylistScreenUIEvents
) {
    Column(
        modifier = modifier
            .width(350.dp)
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(size = 16.dp))
            .background(Color.White.copy(alpha = 0.04f))
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.1f),
                shape = RoundedCornerShape(size = 16.dp)
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = spacedBy(16.dp)
    ) {
        var isNameFocused by remember { mutableStateOf(false) }
        var isUrlFocused by remember { mutableStateOf(false) }
        Text(
            text = "Playlist Name",
            color = TitleText,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        CustomTextField(
            state = CustomTextFieldState(
                value = state.playListName,
                isFocused = isNameFocused,
                placeholder = "e.g. My Playlist"
            ),
            onValueChange = events.onPlayListNameChange,
            onFocusedChange = { isNameFocused = it }
        )

        Text(
            text = "M3U URL",
            color = TitleText,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        CustomTextField(
            state = CustomTextFieldState(
                value = state.m3uUrl,
                isFocused = isUrlFocused,
                placeholder = "https://example.com/my-playlist.m3u"
            ),
            onValueChange = events.onM3UUrlChange,
            onFocusedChange = { isUrlFocused = it }
        )

        Buttons(events)
    }
}

@Composable
private fun Buttons(events: AddPlaylistScreenUIEvents) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 8.dp)
                .wrapContentSize(),
            onClick = events.onCancelClicked,
            colors = ButtonDefaults.colors(
                containerColor = BusinessNavy,
                contentColor = TitleText,
                focusedContainerColor = Crimson,
            )
        ) {
            Text(
                text = "Cancel",
                color = TitleText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 8.dp)
                .wrapContentSize(),
            onClick = events.onAddClicked,
            colors = ButtonDefaults.colors(
                containerColor = BusinessNavy,
                contentColor = TitleText,
                focusedContainerColor = Emerald,
            )
        ) {
            Text(
                text = "Add Playlist",
                color = TitleText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
