package com.pedrofarnetani.spectacle.api

import com.pedrofarnetani.spectacle.models.MusicResponse
import retrofit2.Response
import retrofit2.http.GET

interface MusicApiService {
    @GET("entries")
    suspend fun getEntries(): Response<MusicResponse>
}