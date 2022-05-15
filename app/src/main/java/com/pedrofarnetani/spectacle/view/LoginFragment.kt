package com.pedrofarnetani.spectacle.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pedrofarnetani.spectacle.R
import com.pedrofarnetani.spectacle.databinding.FragmentLoginBinding
import com.pedrofarnetani.spectacle.extensions.navigateSafe
import com.pedrofarnetani.spectacle.extensions.observeActions
import com.pedrofarnetani.spectacle.extensions.text
import com.pedrofarnetani.spectacle.viewmodel.LoginViewModel
import com.pedrofarnetani.spectacle.viewmodel.state.LoginFragmentAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        observers()
        observeActions()
    }

    private fun setupListeners() = with(binding) {
        emailEditText.addTextChangedListener { loginViewModel.onEmailChanged(it.text) }
        passwordEditText.addTextChangedListener { loginViewModel.onPasswordChanged(it.text) }
        loginButton.setOnClickListener { loginViewModel.onLoginButtonClicked() }
        signupButton.setOnClickListener { loginViewModel.goToSignUpScreen() }
    }

    private fun observers() {
        loginViewModel.state.observe(viewLifecycleOwner) { state ->
            if (state.isLoading) {
                showLoading(true)
            }
            if (state.isLoginError) {
                showLoading(false)
                Toast.makeText(requireContext(), getString(R.string.invalid_user), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) = with(binding) {
        emailEditText.isVisible = !isLoading
        passwordEditText.isVisible = !isLoading
        loginButton.isVisible = !isLoading
        signupButton.isVisible = !isLoading
        loadingProgressBar.isVisible = isLoading
    }

    private fun observeActions() {
        loginViewModel.action.observeActions<LoginFragmentAction>(this) { action ->
            when (action) {
                LoginFragmentAction.NavigateToHomeScreen -> findNavController().navigateSafe(
                    LoginFragmentDirections.actionLoginFragmentToSpectacleHomeFragment()
                )
                LoginFragmentAction.NavigateToSignupScreen -> findNavController().navigateSafe(
                    LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
                )
            }
        }
    }
}