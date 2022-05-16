package com.pedrofarnetani.spectacle.repository

import com.pedrofarnetani.spectacle.data.FirebaseSource
import com.pedrofarnetani.spectacle.models.Music
import javax.inject.Inject

internal class FirebaseRepository @Inject constructor(private val firebaseSource: FirebaseSource) {

    fun signUpUser(email: String, password: String) = firebaseSource.signUpUser(email, password)

    fun signInUser(email: String, password: String) = firebaseSource.signInUser(email, password)

    fun saveFavoriteMusic(music: Music) = firebaseSource.saveMusicInPlaylist(music)

    fun deleteMusicFromPlaylist(id: String) = firebaseSource.deleteMusicFromPlaylist(id)

    fun fetchPlaylist() = firebaseSource.fetchPlaylist()
}