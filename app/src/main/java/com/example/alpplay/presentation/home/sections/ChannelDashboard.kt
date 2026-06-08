package com.example.alpplay.presentation.home.sections

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Card
import androidx.tv.material3.Text
import com.example.alpplay.ui.theme.Emerald

@Composable
fun ChannelDashboard(modifier: Modifier = Modifier) {

    val categories = listOf("Sweden", "Turkey")
    val dummyChannels = List(24) { "kanal ${it + 1}" }

    var focusCategoryIndex by remember { mutableStateOf(0) }

    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 24.dp, bottom = 24.dp, end = 24.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = spacedBy(12.dp)
        ) {
            items(categories.size) { index ->
                val isFocused = focusCategoryIndex == index

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isFocused) Color.White.copy(alpha = 0.2f) else Color.Transparent)

                        .onFocusChanged { if (it.isFocused) focusCategoryIndex = index }
                        .focusable()
                        .padding(16.dp)
                ) {
                    Text(
                        text = categories[index],
                        color = if (isFocused) Emerald else Color.White.copy(alpha = 0.7f)
                    )
                }
            }

        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .weight(3f)
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(dummyChannels.size) { index ->
                Card(
                    onClick = { },
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
                        Text(text = dummyChannels[index], color = Color.White)
                    }
                }
            }
        }
    }

}