package com.pedrofarnetani.spectacle.models

data class Movie(
    var id: String? = null,
    val API: String,
    val Description: String,
    val Auth: String,
    val HTTPS: Boolean,
    val Cors: String,
    val Link: String,
    val Category: String
)