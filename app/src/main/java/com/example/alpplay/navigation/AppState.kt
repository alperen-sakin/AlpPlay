package com.example.alpplay.navigation

sealed class AppState {
    object Loading : AppState()
    object GoToAddPlaylist : AppState()
    object GoToMainScreen : AppState()
}
