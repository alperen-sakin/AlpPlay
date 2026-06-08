package com.example.alpplay.presentation.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpplay.data.local.dao.ChannelDao
import com.example.alpplay.data.local.dao.PlaylistDao
import com.example.alpplay.data.mapper.toDomain
import com.example.alpplay.domain.model.Channel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val playlistDao: PlaylistDao,
    private val channelDao: ChannelDao
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private var allChannelsCache = emptyList<Channel>()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val playlists = playlistDao.getAllPlaylists().firstOrNull()
            val activePlayListId = playlists?.firstOrNull()?.id

            if (activePlayListId != null) {
                channelDao.getChannelsByPlaylist(activePlayListId).collect { entityList ->

                    allChannelsCache = entityList.map { it.toDomain() }

                    val allUniqueCategories = entityList.map { it.category }.distinct()

                    val movieCategories = allUniqueCategories.filter { it.contains("▶") }.sorted()
                    val seriesCategories = allUniqueCategories.filter { it.contains("▷") }.sorted()
                    val tvCategories =
                        allUniqueCategories.filter { !it.contains("▷") && !it.contains("►") }
                            .sorted()

                    _state.update { state ->
                        state.copy(
                            tvCategories = tvCategories,
                            movieCategories = movieCategories,
                            seriesCategories = seriesCategories,
                            isLoading = false
                        )
                    }

                    if (allUniqueCategories.isNotEmpty()) {
                        onCategorySelected(allUniqueCategories.first())
                    }
                }
            } else {
                _state.update { state ->
                    state.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onCategorySelected(category: String) {
        val filteredChannels = allChannelsCache.filter { it.category == category }

        _state.update { state ->
            state.copy(
                channels = filteredChannels
            )
        }
    }
}
