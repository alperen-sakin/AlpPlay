package com.example.alpplay.di

import android.content.Context
import androidx.room.Room
import com.example.alpplay.data.local.AppDataBase
import com.example.alpplay.data.local.dao.ChannelDao
import com.example.alpplay.data.local.dao.PlaylistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlaylistModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(
                context,
                AppDataBase::class.java,
                "alpplay_database"
            ).fallbackToDestructiveMigration(false)
            .build()

    }

    @Provides
    @Singleton
    fun providePlaylistDao(database: AppDataBase): PlaylistDao {
        return database.playlistDao
    }

    @Provides
    @Singleton
    fun provideChannelDao(database: AppDataBase): ChannelDao {
        return database.channelDao
    }


}