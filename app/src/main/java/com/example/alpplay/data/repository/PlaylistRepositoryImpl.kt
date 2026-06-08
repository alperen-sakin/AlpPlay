package com.example.alpplay.data.repository

import com.example.alpplay.data.local.dao.ChannelDao
import com.example.alpplay.data.local.dao.PlaylistDao
import com.example.alpplay.data.mapper.toDomain
import com.example.alpplay.data.mapper.toEntity
import com.example.alpplay.data.remote.PlaylistApi
import com.example.alpplay.domain.model.Playlist
import com.example.alpplay.domain.repository.PlaylistRepository
import com.example.alpplay.domain.utils.M3uParser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    private val playlistDao: PlaylistDao,
    private val channelDao: ChannelDao,
    private val api: PlaylistApi
    ): PlaylistRepository {
    override suspend fun addPlaylist(playlist: Playlist) {
        playlistDao.insertPlaylist(playlist.toEntity())
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
       return playlistDao.getAllPlaylists().map{ entities ->
            entities.map{it.toDomain()}
        }
    }

    override suspend fun hesAnyPlaylist(): Boolean {
        return playlistDao.getPlaylistCount() > 0
    }

    override suspend fun addPlaylistAndFetchChannels(playlist: Playlist): Result<Unit> {
        return try {
            val playlistId = playlistDao.insertPlaylist(playlist.toEntity()).toInt()

            val response = api.getM3uFile(playlist.url)

            val parsedChannels = response.byteStream().use { inputStream ->
                M3uParser.parse(inputStream)
            }

            val channelEntities = parsedChannels.map { it.toEntity(playlistId) }
            channelDao.insertChannels(channelEntities)

            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

}