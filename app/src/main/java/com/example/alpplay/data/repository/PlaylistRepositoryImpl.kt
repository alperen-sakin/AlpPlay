package com.example.alpplay.data.repository

import com.example.alpplay.data.local.dao.PlaylistDao
import com.example.alpplay.data.mapper.toDomain
import com.example.alpplay.data.mapper.toEntity
import com.example.alpplay.domain.model.Playlist
import com.example.alpplay.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    private val dao: PlaylistDao
): PlaylistRepository {
    override suspend fun addPlaylist(playlist: Playlist) {
        dao.insertPlaylist(playlist.toEntity())
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
       return dao.getAllPlaylists().map{entities ->
            entities.map{it.toDomain()}
        }
    }

    override suspend fun hesAnyPlaylist(): Boolean {
        return dao.getPlaylistCount() > 0
    }
}