package com.pedrofarnetani.spectacle.view.music

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pedrofarnetani.spectacle.R
import com.pedrofarnetani.spectacle.databinding.FragmentMusicsBinding
import com.pedrofarnetani.spectacle.view.music.adapter.MusicAdapter
import com.pedrofarnetani.spectacle.viewmodel.MusicViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class MusicsFragment : Fragment(R.layout.fragment_musics) {

    private lateinit var binding: FragmentMusicsBinding

    private val musicViewModel: MusicViewModel by viewModels()

    private lateinit var musicAdapter: MusicAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMusicsBinding.inflate(layoutInflater)

        musicAdapter = MusicAdapter(
            MusicAdapter.OnClickListener { music ->
                musicViewModel.addToPlaylist(music)
            },
            false
        )

        musicViewModel.fetchMusics()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        insertListeners()
        observers()
    }

    private fun insertListeners() = with(binding) {
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        musicSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                musicViewModel.searchForMusic(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                musicViewModel.searchForMusic(newText)
                return true
            }

        })
    }

    private fun observers() = with(binding) {
        musicViewModel.state.observe(viewLifecycleOwner) { state ->
            musicSearchView.isVisible = !state.isLoadingMusics
            musicsRecyclerView.isVisible = !state.isLoadingMusics
            loadingMusicsProgressBar.isVisible = state.isLoadingMusics

            if (state.musicAdded != null) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.music_was_added, state.musicAdded.API),
                    Toast.LENGTH_SHORT
                ).show()
                musicViewModel.onMusicAdded()
            }
        }
        musicViewModel.musicList.observe(viewLifecycleOwner) { musicList ->
            musicAdapter.submitList(musicList)
            musicsRecyclerView.adapter = musicAdapter
        }
    }
}