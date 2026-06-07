package com.example.alpplay.presentation.addLinkScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.tv.material3.Text

@Composable
fun AddLinkScreen(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .fillMaxSize()
    ){
        Text(text = "Add Your m3u Playlist")
    }

}


@Preview
@Composable
private fun ada() {
    AddLinkScreen()
}
