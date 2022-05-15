package com.pedrofarnetani.spectacle.data

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

internal class FirebaseSource @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    fun signUpUser(email: String, password: String) = firebaseAuth.createUserWithEmailAndPassword(email, password)

    fun signInUser(email: String, password: String) = firebaseAuth.signInWithEmailAndPassword(email, password)
}