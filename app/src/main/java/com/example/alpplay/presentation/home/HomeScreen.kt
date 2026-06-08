package com.example.alpplay.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Icon
import androidx.tv.material3.NavigationDrawer
import androidx.tv.material3.NavigationDrawerItem
import androidx.tv.material3.NavigationDrawerItemDefaults
import androidx.tv.material3.Text
import com.example.alpplay.presentation.home.constant.MenuItems
import com.example.alpplay.presentation.home.sections.ChannelDashboard
import com.example.alpplay.ui.theme.BusinessNavy
import com.example.alpplay.ui.theme.Emerald
import com.example.alpplay.ui.theme.Mirage

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var selectedIndex by remember { mutableStateOf(0) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Mirage)
    ) {
        NavigationDrawer(
            drawerContent = {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(Color.Black.copy(0.05f))
                        .padding(vertical = 32.dp, horizontal = 12.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    MenuItems.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            selected = selectedIndex == index,
                            onClick = { selectedIndex = index},
                            colors = NavigationDrawerItemDefaults.colors(
                                focusedContainerColor = Color.White.copy(0.1f),
                                focusedContentColor = Emerald,
                                selectedContainerColor = Color.Transparent,
                                selectedContentColor = Emerald

                            ),
                            leadingContent = {
                                Icon(
                                    imageVector = item.second,
                                    contentDescription = item.first,
                                )
                            }
                        ) {
                            Text(
                                text = item.first,

                            )
                        }
                    }
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ){
                when(selectedIndex){
                    0 -> ChannelDashboard()
                    1 -> Text(text = "Movies", color = Color.White)
                    2 -> Text(text = "Series", color = Color.White)
                    3 -> Text(text = "Settings", color = Color.White)
                }
            }
        }


    }

}


@Preview(device = "id:tv_1080p")
@Composable
private fun adam() {
    HomeScreen()
}
