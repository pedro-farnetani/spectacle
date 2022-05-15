package com.pedrofarnetani.spectacle.view.music

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pedrofarnetani.spectacle.R
import com.pedrofarnetani.spectacle.databinding.FragmentPlaylistBinding

internal class PlaylistFragment : Fragment(R.layout.fragment_playlist) {

    private lateinit var binding: FragmentPlaylistBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPlaylistBinding.inflate(layoutInflater)
        return binding.root
    }
}