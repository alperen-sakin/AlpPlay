package com.example.alpplay.presentation.addLinkScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alpplay.domain.model.Playlist
import com.example.alpplay.domain.repository.PlaylistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPlayListViewModel @Inject constructor(
    private val repository: PlaylistRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AddPlayListState())
    val state = _state.asStateFlow()

    fun onPlayListNameChange(playListName: String) {
        _state.update {
            it.copy(
                playListName = playListName
            )
        }
    }

    fun m3uUrlChange(m3uUrl: String) {
        _state.update {
            it.copy(
                m3uUrl = m3uUrl
            )
        }
    }

    fun onCancelClicked() {
        _state.update {
            it.copy(
                playListName = "",
                m3uUrl = ""
            )
        }
    }

    fun onAddClicked(onSuccess: () -> Unit) {
        val currentName = state.value.playListName
        val currentUrl = state.value.m3uUrl

        if (currentName.isNotBlank() && currentUrl.isNotBlank()) {
            viewModelScope.launch {
                val newPlaylist = Playlist(name = currentName, url = currentUrl)
                repository.addPlaylist(newPlaylist)
                onSuccess()
            }
        }
    }
}
