package com.pedrofarnetani.spectacle.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedrofarnetani.spectacle.arch.Event
import com.pedrofarnetani.spectacle.models.Music
import com.pedrofarnetani.spectacle.repository.FirebaseRepository
import com.pedrofarnetani.spectacle.repository.MusicRepository
import com.pedrofarnetani.spectacle.viewmodel.state.MusicAction
import com.pedrofarnetani.spectacle.viewmodel.state.MusicState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MusicViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val entryRepository: MusicRepository
) : ViewModel() {

    private val _state = MutableLiveData(MusicState())
    val state: LiveData<MusicState> = _state

    private val _action = MutableLiveData<Event<MusicAction>>()
    val action: LiveData<Event<MusicAction>> = _action

    private val _playlist = MutableLiveData<MutableList<Music>>()
    val playlist: LiveData<MutableList<Music>> = _playlist

    private var storedPlayList = mutableListOf<Music>()

    private val _musicList = MutableLiveData<MutableList<Music>>()
    val musicList: LiveData<MutableList<Music>> = _musicList

    private var storedMusicList = mutableListOf<Music>()

    fun fetchPlaylist() {
        firebaseRepository.fetchPlaylist().addOnCompleteListener { task ->
            storedPlayList = mutableListOf()
            if (task.isSuccessful) {
                for (item in task.result.documents) {
                    val music = Music(
                        id = item.id,
                        API = item.data!!["api"] as String,
                        Description = item.data!!["description"] as String,
                        Auth = item.data!!["auth"] as String,
                        HTTPS = item.data!!["https"] as Boolean,
                        Cors = item.data!!["cors"] as String,
                        Link = item.data!!["link"] as String,
                        Category = item.data!!["category"] as String
                    )
                    storedPlayList.add(music)
                }
            }
            setState { copy(isLoadingPlaylist = false) }
            _playlist.value = storedPlayList
        }
    }

    fun onAddMusicButtonClicked() {
        sendAction(MusicAction.NavigateToMusicsScreen)
    }

    fun fetchMusics() {
        viewModelScope.launch {
            runCatching {
                entryRepository.getEntries()
            }.onSuccess { musicResponse ->
                if (musicResponse.isSuccessful) {
                    musicResponse.body()?.let {
                        setState { copy(isLoadingMusics = false) }
                        storedMusicList = it.entries
                        _musicList.value = it.entries
                    }
                }
            }.onFailure {
                Log.e("Pedro", "falhou")
            }
        }
    }

    fun addToPlaylist(music: Music) {
        firebaseRepository.saveFavoriteMusic(music).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                storedPlayList.add(music)
                setState { copy(musicAdded = music) }
                _playlist.value = storedPlayList
            }
        }
        _playlist.value
    }

    fun removeFromPlaylist(music: Music) {
        music.id?.let { id ->
            firebaseRepository.deleteMusicFromPlaylist(id).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    storedPlayList.remove(music)
                    setState { copy(musicRemoved = music) }
                    _playlist.value = storedPlayList
                }
            }
        }
    }

    fun searchForMusicInPlaylist(query: String) {
        if (!storedPlayList.isNullOrEmpty()) {
            val newMusicList = storedPlayList.filter { music -> music.API.startsWith(query, true) }.toMutableList()
            _playlist.value = newMusicList
        }
    }

    fun searchForMusic(query: String) {
        if (!storedMusicList.isNullOrEmpty()) {
            val newMusicList = storedMusicList.filter { music -> music.API.startsWith(query, true) }.toMutableList()
            _musicList.value = newMusicList
        }
    }

    fun onMusicAdded() = setState { copy(musicAdded = null) }

    fun onMusicRemoved() = setState { copy(musicRemoved = null) }

    private fun setState(block: MusicState.() -> MusicState) {
        _state.value = _state.value!!.block()
    }

    private fun sendAction(action: MusicAction) {
        _action.value = Event(action)
    }
}