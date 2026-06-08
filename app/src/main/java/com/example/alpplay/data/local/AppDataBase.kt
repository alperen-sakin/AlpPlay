package com.example.alpplay.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.alpplay.data.local.dao.PlaylistDao
import com.example.alpplay.data.local.entity.PlayListEntity

@Database(
    entities = [PlayListEntity::class],
    version = 1,
    exportSchema = false
)
 abstract class AppDataBase: RoomDatabase() {
     abstract val playlistDao: PlaylistDao
}