package com.pedrofarnetani.spectacle.viewmodel.state

import com.pedrofarnetani.spectacle.models.Music

internal data class MusicState(
    val isLoadingPlaylist: Boolean = true,
    val playlistList: MutableList<Music> = mutableListOf(),
    val isLoadingMusics: Boolean = true,
    val musicRemoved: Music? = null,
    val musicAdded: Music? = null
)