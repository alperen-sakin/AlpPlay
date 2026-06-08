package com.example.alpplay.domain.repository

import com.example.alpplay.domain.model.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {

    suspend fun addPlaylist(playlist: Playlist)

    fun getAllPlaylists(): Flow<List<Playlist>>

    suspend fun hesAnyPlaylist(): Boolean

    suspend fun addPlaylistAndFetchChannels(playlist: Playlist): Result<Unit>
}
