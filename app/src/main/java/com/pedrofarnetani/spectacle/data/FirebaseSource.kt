package com.pedrofarnetani.spectacle.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.pedrofarnetani.spectacle.models.Music
import javax.inject.Inject

internal class FirebaseSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    fun signUpUser(email: String, password: String) = firebaseAuth.createUserWithEmailAndPassword(email, password)

    fun signInUser(email: String, password: String) = firebaseAuth.signInWithEmailAndPassword(email, password)

    fun saveMusicInPlaylist(music: Music) = firestore
        .collection("musics")
        .add(music)

    fun deleteMusicFromPlaylist(id: String) = firestore
        .collection("musics")
        .document(id)
        .delete()

    fun fetchPlaylist() = firestore
        .collection("musics")
        .get()
}