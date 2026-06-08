package com.example.alpplay.data.mapper

import com.example.alpplay.data.local.entity.ChannelEntity
import com.example.alpplay.domain.model.Channel

fun Channel.toEntity(playlistId: Int): ChannelEntity {
    return ChannelEntity(
        playlistId = playlistId,
        name = name,
        logoUrl = logoUrl,
        category = category,
        streamUrl = streamUrl
    )
}

fun ChannelEntity.toDomain(): Channel {
    return Channel(
        name = name,
        logoUrl = logoUrl,
        category = category,
        streamUrl = streamUrl
    )
}