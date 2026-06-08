package com.example.alpplay.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface PlaylistApi {

    @GET
    suspend fun getM3uFile(@Url fileUrl: String): ResponseBody
}
