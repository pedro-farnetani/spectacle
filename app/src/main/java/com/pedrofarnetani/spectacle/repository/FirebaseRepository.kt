package com.pedrofarnetani.spectacle.repository

import com.pedrofarnetani.spectacle.data.FirebaseSource
import javax.inject.Inject

internal class FirebaseRepository @Inject constructor(private val firebaseSource: FirebaseSource) {

    fun signUpUser(email: String, password: String) = firebaseSource.signUpUser(email, password)

    fun signInUser(email: String, password: String) = firebaseSource.signInUser(email, password)
}