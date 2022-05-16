package com.pedrofarnetani.spectacle.repository

import com.pedrofarnetani.spectacle.data.PreferencesSource
import javax.inject.Inject

internal class PreferencesRepository @Inject constructor(
    private val preferencesSource: PreferencesSource
) {
    fun saveUser(email: String, password: String) = preferencesSource.saveUser(email, password)

    fun getUser() = preferencesSource.getUser()
}