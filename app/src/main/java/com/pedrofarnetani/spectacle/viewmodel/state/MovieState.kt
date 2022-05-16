package com.pedrofarnetani.spectacle.viewmodel.state

import com.pedrofarnetani.spectacle.models.Music

internal data class MovieState(
    val isLoadingMyMovies: Boolean = false,
    val musicList: MutableList<Music> = mutableListOf()
)