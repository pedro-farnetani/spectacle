package com.pedrofarnetani.spectacle.viewmodel.state

internal data class LoginState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val isLoginError: Boolean = false,
    val isSignUpError: Boolean = false,
    val hasUserStored: Boolean = false
)