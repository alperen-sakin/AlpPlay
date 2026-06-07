package com.example.alpplay.presentation.addLinkScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import com.example.alpplay.ui.theme.Selena

@Composable
fun CustomTextField(
    state: CustomTextFieldState,
    onValueChange: (String) -> Unit,
    onFocusedChange: (Boolean) -> Unit
) {
    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (state.isFocused) {
                    Color.White.copy(alpha = 0.2f)
                } else {
                    Color.Black.copy(
                        alpha = 0.2f
                    )
                }
            )
            .onFocusChanged { onFocusedChange(it.isFocused) }
            .padding(12.dp),
        value = state.value,
        onValueChange = onValueChange,
        decorationBox = { innerTextField ->
            Box(contentAlignment = Alignment.CenterStart) {
                if (state.value.isEmpty()) {
                    Text(
                        text = state.placeholder,
                        color = Selena,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                }

                innerTextField()
            }
        },
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )

    )
}
