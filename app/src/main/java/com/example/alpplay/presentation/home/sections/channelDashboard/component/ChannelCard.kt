package com.example.alpplay.presentation.home.sections.channelDashboard.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Tv
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.Icon
import androidx.tv.material3.Text
import com.example.alpplay.domain.model.Channel
import com.example.alpplay.ui.theme.DarkSpace
import com.example.alpplay.ui.theme.Darlington
import com.example.alpplay.ui.theme.Turquoise

@Composable
fun ChannelCard(
    modifier: Modifier = Modifier,
    onChannelClick: (Channel) -> Unit,
    channel: Channel,
) {
    val tvSafeColors = listOf(
        Color(0xFF2C3E50),
        Color(0xFF8E44AD),
        Color(0xFF27AE60),
        Color(0xFFD35400),
        Color(0xFF16A085),
        Color(0xFFC0392B)
    )

    val randomColor = remember {tvSafeColors.random() }

    Card(
        modifier = Modifier
            .aspectRatio(16f / 9f),
        shape = CardDefaults.shape(shape = RoundedCornerShape(16.dp)),
        onClick = { onChannelClick(channel) },
        colors = CardDefaults.colors(
            containerColor = DarkSpace,
        ),
        border = CardDefaults.border(
            focusedBorder = Border(
                border = BorderStroke(
                    width = 2.dp,
                    color = Turquoise
                )
            ),

        )

        ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier

                    .background(color = randomColor)
                    .padding(8.dp)
                    .fillMaxWidth()
                    .weight(0.5f),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Tv,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .aspectRatio(1f / 1f)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
                    .padding(start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = channel.name,
                    color = Color.White,
                    fontSize = 18.sp,

                    )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = channel.category,
                    color = Darlington,
                    fontSize = 14.sp,
                )
            }
        }
    }

}
