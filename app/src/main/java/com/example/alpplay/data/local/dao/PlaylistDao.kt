package com.example.alpplay.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.alpplay.data.local.entity.PlayListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: PlayListEntity):Long

    @Query("SELECT * FROM playlists ORDER BY addedAt DESC")
    fun getAllPlaylists(): Flow<List<PlayListEntity>>

    @Query("SELECT COUNT(*) FROM playlists")
    suspend fun getPlaylistCount(): Int
}