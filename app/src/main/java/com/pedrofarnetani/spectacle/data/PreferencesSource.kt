package com.pedrofarnetani.spectacle.data

import android.content.SharedPreferences
import javax.inject.Inject

internal class PreferencesSource @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun saveUser(email: String, password: String) = sharedPreferences
        .edit()
        .putString(EMAIL, email)
        .putString(PASSWORD, password)
        .apply()

    fun getUser(): Pair<String, String> {
        val email: String = sharedPreferences.getString(EMAIL, "") ?: ""
        val password: String = sharedPreferences.getString(PASSWORD, "") ?: ""

        return Pair(email, password)
    }

    companion object {
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
    }
}