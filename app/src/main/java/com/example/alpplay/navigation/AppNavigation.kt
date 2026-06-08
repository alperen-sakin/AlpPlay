package com.example.alpplay.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.tv.material3.Text
import com.example.alpplay.domain.model.ChannelStorage
import com.example.alpplay.presentation.playerScreen.PlayerScreen
import com.example.alpplay.presentation.addLinkScreen.AddLinkScreen
import com.example.alpplay.presentation.home.HomeScreen
import com.example.alpplay.ui.theme.Mirage
import com.example.alpplay.ui.theme.TitleText

@Composable
fun AppNavigation(
    viewModel: AppViewModel = hiltViewModel(),
) {
    val appState by viewModel.state.collectAsState()

    val navController = rememberNavController()

    when (appState) {
        AppState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Mirage)
            ) {
                Text(text = "Loading", fontSize = 40.sp, color = TitleText)
            }
        }

        AppState.GoToAddPlaylist -> {
            NavHost(navController = navController, startDestination = "add_link") {
                composable("add_link") { AddLinkScreen(navController = navController) }
                composable("main") { HomeScreen(navController = navController) }
            }
        }

        AppState.GoToMainScreen -> {
            NavHost(navController = navController, startDestination = "main") {
                composable("add_link") { AddLinkScreen(navController = navController) }
                composable("main") { HomeScreen(navController = navController) }
                composable("player") {
                    val channel = ChannelStorage.selectedChannel


                    if (
                        channel != null
                    ) {
                        PlayerScreen(
                            url = channel.streamUrl,
                            channelName = channel.name,
                            channelCategory = channel.category,
                            onNextChannel = {},
                            onPreviousChannel = {}
                        )
                    }


                }
            }
        }
    }
}
