package com.example.alpplay.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpplay.domain.repository.PlaylistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val repository: PlaylistRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AppState>(AppState.Loading)
    val state = _state.asStateFlow()

    init {
        checkIfPlaylistExists()
    }

    private fun checkIfPlaylistExists() {
        viewModelScope.launch {
            val hesData = repository.hesAnyPlaylist()

            if (hesData) {
                _state.value = AppState.GoToMainScreen
            } else {
                _state.value = AppState.GoToAddPlaylist
            }
        }
    }
}
