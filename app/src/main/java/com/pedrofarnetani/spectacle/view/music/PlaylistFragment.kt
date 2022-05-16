package com.pedrofarnetani.spectacle.view.music

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pedrofarnetani.spectacle.R
import com.pedrofarnetani.spectacle.databinding.FragmentPlaylistBinding
import com.pedrofarnetani.spectacle.extensions.navigateSafe
import com.pedrofarnetani.spectacle.extensions.observeActions
import com.pedrofarnetani.spectacle.view.music.adapter.MusicAdapter
import com.pedrofarnetani.spectacle.viewmodel.MusicViewModel
import com.pedrofarnetani.spectacle.viewmodel.state.MusicAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class PlaylistFragment : Fragment(R.layout.fragment_playlist) {

    private lateinit var binding: FragmentPlaylistBinding

    private val musicViewModel: MusicViewModel by viewModels()

    private lateinit var musicAdapter: MusicAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPlaylistBinding.inflate(layoutInflater)

        musicAdapter = MusicAdapter(
            MusicAdapter.OnClickListener { music ->
                musicViewModel.removeFromPlaylist(music)
            },
            true
        )

        musicViewModel.fetchPlaylist()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        insertListeners()
        observers()
        observeActions()
    }

    private fun insertListeners() = with(binding) {
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        playlistSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                musicViewModel.searchForMusicInPlaylist(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                musicViewModel.searchForMusicInPlaylist(newText)
                return true
            }

        })
        addMusicButton.setOnClickListener { musicViewModel.onAddMusicButtonClicked() }
    }

    private fun observers() {
        musicViewModel.state.observe(viewLifecycleOwner) { state ->
            showLoading(state.isLoadingPlaylist)
        }
        musicViewModel.playlist.observe(viewLifecycleOwner) { musicList ->
            musicAdapter.submitList(musicList)
            binding.playlistRecyclerView.adapter = musicAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) = with(binding) {
        playlistRecyclerView.isVisible = !isLoading
        loadingPlaylistProgressBar.isVisible = isLoading
    }

    private fun observeActions() {
        musicViewModel.action.observeActions<MusicAction>(this) { action ->
            when (action) {
                MusicAction.NavigateToMusicsScreen -> findNavController().navigateSafe(
                    PlaylistFragmentDirections.actionPlaylistFragmentToMusicsFragment()
                )
            }
        }
    }
}