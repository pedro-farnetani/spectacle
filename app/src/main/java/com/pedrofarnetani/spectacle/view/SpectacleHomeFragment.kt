package com.pedrofarnetani.spectacle.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pedrofarnetani.spectacle.R
import com.pedrofarnetani.spectacle.databinding.FragmentSpectacleHomeBinding
import com.pedrofarnetani.spectacle.extensions.navigateSafe

internal class SpectacleHomeFragment : Fragment(R.layout.fragment_spectacle_home) {

    private lateinit var binding: FragmentSpectacleHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSpectacleHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        insertListeners()
    }

    private fun insertListeners() = with(binding) {
        musicCustomButton.setOnClickListener {
            findNavController().navigateSafe(
                SpectacleHomeFragmentDirections.actionSpectacleHomeFragmentToPlaylistFragment()
            )
        }
        movieCustomButton.setOnClickListener {
            findNavController().navigateSafe(
                SpectacleHomeFragmentDirections.actionSpectacleHomeFragmentToMyMoviesFragment()
            )
        }
    }
}