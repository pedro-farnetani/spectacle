package com.pedrofarnetani.spectacle.view.music

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pedrofarnetani.spectacle.R
import com.pedrofarnetani.spectacle.databinding.FragmentMusicsBinding

internal class MusicsFragment : Fragment(R.layout.fragment_musics) {

    private lateinit var binding: FragmentMusicsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMusicsBinding.inflate(layoutInflater)
        return binding.root
    }
}