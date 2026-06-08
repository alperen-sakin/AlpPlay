package com.example.alpplay.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.Icon
import androidx.tv.material3.Text
import com.example.alpplay.ui.theme.Turquoise
import com.example.alpplay.ui.theme.Darlington

@Composable
fun NavItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    text: String
) {


    Card(
        shape = CardDefaults.shape(RoundedCornerShape(16.dp)),
        border = CardDefaults.border(
            focusedBorder = Border.None,
            border = Border.None
        ),
        colors = CardDefaults.colors(
            containerColor = if (selected) Turquoise.copy(alpha = 0.15f) else Color.Transparent,

            focusedContainerColor = Turquoise.copy(alpha = 0.15f),

            pressedContainerColor = Turquoise.copy(alpha = 0.15f),

            ),
        onClick = onClick,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .padding(vertical = 12.dp)
            .height(64.dp)
            .widthIn(min = 64.dp)
            .wrapContentWidth()
            .drawBehind {
                if (selected) {

                    val barWidth = 4.dp.toPx()
                    val barHeight = size.height * 0.6f
                    val cornerRadius = 8.dp.toPx()

                    val path = Path().apply {
                        moveTo(0f, (size.height - barHeight) / 2)

                        lineTo(barWidth - cornerRadius, (size.height - barHeight) / 2)
                        quadraticTo(
                            barWidth,
                            (size.height - barHeight) / 2,
                            barWidth,
                            (size.height - barHeight) / 2 + cornerRadius
                        )

                        lineTo(barWidth, (size.height + barHeight) / 2 - cornerRadius)

                        quadraticTo(
                            barWidth,
                            (size.height + barHeight) / 2,
                            barWidth - cornerRadius,
                            (size.height + barHeight) / 2
                        )

                        lineTo(0f, (size.height + barHeight) / 2)

                        close()
                    }

                    drawPath(path, color = Turquoise)

                }
            }
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (selected) Turquoise else Darlington,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = text,
                    color = if (selected) Turquoise else Darlington,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }


}

