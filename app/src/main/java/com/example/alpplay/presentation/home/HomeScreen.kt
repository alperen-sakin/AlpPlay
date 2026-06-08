package com.example.alpplay.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.tv.material3.Icon
import androidx.tv.material3.NavigationDrawer
import androidx.tv.material3.NavigationDrawerItem
import androidx.tv.material3.NavigationDrawerItemDefaults
import androidx.tv.material3.Text
import com.example.alpplay.domain.model.ChannelStorage
import com.example.alpplay.presentation.home.constant.MenuItems
import com.example.alpplay.presentation.home.sections.ChannelDashboard
import com.example.alpplay.presentation.home.viewModel.HomeViewModel
import com.example.alpplay.ui.theme.Emerald
import com.example.alpplay.ui.theme.Mirage
import kotlin.io.encoding.Base64

private const val ALPHA0_05F = 0.05f
private const val ALPHA0_1F = 0.1f

private const val TV_SECTION = 0
private const val MOVIE_SECTION = 1
private const val SERIES_SECTION = 2
private const val SETTINGS_SECTION = 3

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    var selectedIndex by remember { mutableStateOf(0) }
    val state by viewModel.state.collectAsState()

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
                        .background(Color.Black.copy(alpha = ALPHA0_05F))
                        .padding(vertical = 32.dp, horizontal = 12.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    MenuItems.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            selected = selectedIndex == index,
                            onClick = { selectedIndex = index },
                            colors = NavigationDrawerItemDefaults.colors(
                                focusedContainerColor = Color.White.copy(ALPHA0_1F),
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
            ) {
                when (selectedIndex) {
                    TV_SECTION -> ChannelDashboard(
                        state = state,
                        onCategoryFocused = { category -> viewModel.onCategorySelected(category) },
                        onChannelClick = { channel ->
                            ChannelStorage.selectedChannel = channel
                            navController.navigate("player")
                        }

                    )

                    MOVIE_SECTION -> Text(text = "Movies", color = Color.White)
                    SERIES_SECTION -> Text(text = "Series", color = Color.White)
                    SETTINGS_SECTION -> Text(text = "Settings", color = Color.White)
                }
            }
        }
    }
}
