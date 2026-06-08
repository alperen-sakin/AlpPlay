package com.example.alpplay.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.alpplay.data.local.dao.ChannelDao
import com.example.alpplay.data.local.dao.PlaylistDao
import com.example.alpplay.data.local.entity.ChannelEntity
import com.example.alpplay.data.local.entity.PlayListEntity

@Database(
    entities = [PlayListEntity::class, ChannelEntity::class],
    version = 2,
)
abstract class AppDataBase : RoomDatabase() {
    abstract val playlistDao: PlaylistDao
    abstract val channelDao: ChannelDao
}
