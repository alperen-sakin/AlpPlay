package com.example.alpplay.presentation.addLinkScreen.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddPlayListViewModel @Inject constructor() : ViewModel() {

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
}
