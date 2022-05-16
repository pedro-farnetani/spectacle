package com.pedrofarnetani.spectacle.repository

import com.pedrofarnetani.spectacle.api.MusicApiService
import javax.inject.Inject

internal class MusicRepository @Inject constructor(
    private val entriesApi: MusicApiService
) {
    suspend fun getEntries() = entriesApi.getEntries()
}