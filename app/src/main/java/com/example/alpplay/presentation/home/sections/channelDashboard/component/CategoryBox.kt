package com.example.alpplay.presentation.home.sections.channelDashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.Glow
import androidx.tv.material3.Icon
import androidx.tv.material3.Text
import com.example.alpplay.ui.theme.Darlington
import com.example.alpplay.ui.theme.Turquoise
import com.example.alpplay.ui.theme.UniformBlue


@Composable
fun CategoryBox(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    category: String,
    onClick: () -> Unit,
    onFocusChanged: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = if (isSelected) Turquoise else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            ),
    ) {
        Card(
            modifier = modifier
                .onFocusChanged { onFocusChanged(it.isFocused) }
                .fillMaxWidth()
                .offset(x = 3.dp),
            shape = CardDefaults.shape(RoundedCornerShape(16.dp)),
            border = CardDefaults.border(
                focusedBorder = Border.None,
                border = Border.None,
            ),
            scale = CardDefaults.scale(focusedScale = 1f, pressedScale = 1f),
            glow = CardDefaults.glow(focusedGlow = Glow.None, pressedGlow = Glow.None),

            colors = CardDefaults.colors(
                containerColor = if (isSelected) UniformBlue else Color.Transparent,

                focusedContainerColor = UniformBlue,

                pressedContainerColor = UniformBlue,

                ),

            onClick = onClick
        ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 12.dp, top = 12.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = category,
                    color = if (isSelected) Turquoise else Darlington,
                    maxLines = 1,
                    fontSize = 14.sp,
                    modifier = Modifier
                )

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = null,
                    tint = if (isSelected) Turquoise else Darlington,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }

}


@Preview
@Composable
private fun co() {
//    CountyTabBox(
//        isFocused = true,
//        category = "TV"
//    )
}
