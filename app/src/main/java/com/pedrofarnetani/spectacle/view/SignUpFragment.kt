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
import com.pedrofarnetani.spectacle.databinding.FragmentSignUpBinding
import com.pedrofarnetani.spectacle.extensions.navigateSafe
import com.pedrofarnetani.spectacle.extensions.observeActions
import com.pedrofarnetani.spectacle.extensions.text
import com.pedrofarnetani.spectacle.viewmodel.LoginViewModel
import com.pedrofarnetani.spectacle.viewmodel.state.SignUpFragmentAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        insertListeners()
        observers()
        observeActions()
    }

    private fun insertListeners() = with(binding) {
        emailEditText.addTextChangedListener { loginViewModel.onEmailChanged(it.text) }
        passwordEditText.addTextChangedListener { loginViewModel.onPasswordChanged(it.text) }
        signupButton.setOnClickListener { loginViewModel.registerUser() }
    }

    private fun observers() {
        loginViewModel.state.observe(viewLifecycleOwner) { state ->
            if (state.isLoading) {
                showLoading(true)
            }
            if (state.isSignUpError) {
                showLoading(false)
                Toast.makeText(requireContext(), getString(R.string.signup_error), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) = with(binding) {
        signUpTitleTextView.isVisible = !isLoading
        emailTitleTextView.isVisible = !isLoading
        emailEditText.isVisible = !isLoading
        passwordTitleTextView.isVisible = !isLoading
        passwordEditText.isVisible = !isLoading
        emailTitleTextView.isVisible = !isLoading
        signupButton.isVisible = !isLoading
        loadingProgressBar.isVisible = isLoading
    }

    private fun observeActions() {
        loginViewModel.action.observeActions<SignUpFragmentAction>(this) { action ->
            when(action) {
                SignUpFragmentAction.NavigateToHomeScreen -> findNavController().navigateSafe(
                    SignUpFragmentDirections.actionSignUpFragmentToSpectacleHomeFragment()
                )
            }
        }
    }
}