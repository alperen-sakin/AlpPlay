package com.example.alpplay.data.mapper

import com.example.alpplay.data.local.entity.PlayListEntity
import com.example.alpplay.domain.model.Playlist

fun Playlist.toEntity(): PlayListEntity {
    return PlayListEntity(id = id, name = name, url = url, addedAt = addedAt)
}

fun PlayListEntity.toDomain(): Playlist {
    return Playlist(id = id, name = name, url = url, addedAt = addedAt)
}
