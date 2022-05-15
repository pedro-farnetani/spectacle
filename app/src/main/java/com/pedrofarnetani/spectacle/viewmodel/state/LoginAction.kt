package com.pedrofarnetani.spectacle.viewmodel.state

internal sealed class LoginAction

internal sealed class LoginFragmentAction : LoginAction() {
    object NavigateToSignupScreen : LoginFragmentAction()
    object NavigateToHomeScreen : LoginFragmentAction()
}

internal sealed class SignUpFragmentAction : LoginAction() {
    object NavigateToHomeScreen : SignUpFragmentAction()
}