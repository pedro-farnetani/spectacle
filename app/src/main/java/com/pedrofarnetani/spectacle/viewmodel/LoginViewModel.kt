package com.pedrofarnetani.spectacle.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedrofarnetani.spectacle.arch.Event
import com.pedrofarnetani.spectacle.repository.FirebaseRepository
import com.pedrofarnetani.spectacle.viewmodel.state.LoginAction
import com.pedrofarnetani.spectacle.viewmodel.state.LoginFragmentAction
import com.pedrofarnetani.spectacle.viewmodel.state.LoginState
import com.pedrofarnetani.spectacle.viewmodel.state.SignUpFragmentAction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _state = MutableLiveData(LoginState())
    val state: LiveData<LoginState> = _state

    private val _action = MutableLiveData<Event<LoginAction>>()
    val action: LiveData<Event<LoginAction>> = _action

    fun onEmailChanged(email: String) = setState {
        copy(email = email, isLoginError = false, isSignUpError = false)
    }

    fun onPasswordChanged(password: String) = setState {
        copy(password = password, isLoginError = false, isSignUpError = false)
    }

    fun onLoginButtonClicked() {
        state.value?.let { stateValue ->
            setLoading(true)
            firebaseRepository.signInUser(stateValue.email, stateValue.password).addOnCompleteListener { task ->
                setLoading(false)
                if (task.isSuccessful) {
                    sendAction(LoginFragmentAction.NavigateToHomeScreen)
                } else {
                    setState { copy(isLoginError = true) }
                }
            }
        }
    }

    fun goToSignUpScreen() {
        sendAction(LoginFragmentAction.NavigateToSignupScreen)
    }

    fun registerUser() {
        state.value?.let { stateValue ->
            setLoading(true)
            firebaseRepository.signUpUser(stateValue.email, stateValue.password).addOnCompleteListener { task ->
                setLoading(false)
                if (task.isSuccessful) {
                    sendAction(SignUpFragmentAction.NavigateToHomeScreen)
                } else {
                    setState { copy(isSignUpError = true) }
                }
            }
        }
    }

    private fun setLoading(isLoading: Boolean) = setState { copy(isLoading = isLoading) }

    private fun setState(block: LoginState.() -> LoginState) {
        _state.value = _state.value!!.block()
    }

    private fun sendAction(action: LoginAction) {
        _action.value = Event(action)
    }
}