package com.example.alpplay.domain.model

data class Playlist(
    val id: Int = 0,
    val name: String,
    val url: String,
    val addedAt: Long = System.currentTimeMillis()
)
