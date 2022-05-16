package com.pedrofarnetani.spectacle.view.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.pedrofarnetani.spectacle.R
import com.pedrofarnetani.spectacle.databinding.FragmentMusicsBinding
import com.pedrofarnetani.spectacle.databinding.FragmentMyMoviesBinding

internal class MyMoviesFragment : Fragment(R.layout.fragment_my_movies) {

    private lateinit var binding: FragmentMyMoviesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyMoviesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        insertListeners()
    }

    private fun insertListeners() = with(binding) {
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }
}