package com.example.alpplay.di

import android.content.Context
import androidx.room.Room
import com.example.alpplay.data.local.AppDataBase
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
        ).build()

    }

    @Provides
    @Singleton
    fun providePlaylistDao(databse: AppDataBase): PlaylistDao {
        return databse.playlistDao
    }



}