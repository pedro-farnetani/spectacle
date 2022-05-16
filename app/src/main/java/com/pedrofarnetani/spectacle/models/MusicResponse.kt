package com.pedrofarnetani.spectacle.models

data class MusicResponse(
    val count: Int,
    val entries: MutableList<Music>
)
