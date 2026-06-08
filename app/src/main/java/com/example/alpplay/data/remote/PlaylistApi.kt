package com.example.alpplay.data.remote

import retrofit2.http.GET
import retrofit2.http.Url

interface PlaylistApi {

    @GET
    suspend fun getM3uFile(@Url fileUrl: String): String
}