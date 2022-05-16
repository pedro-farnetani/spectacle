package com.pedrofarnetani.spectacle.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedrofarnetani.spectacle.arch.Event
import com.pedrofarnetani.spectacle.repository.MusicRepository
import com.pedrofarnetani.spectacle.viewmodel.state.MovieAction
import com.pedrofarnetani.spectacle.viewmodel.state.MovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MovieViewModel @Inject constructor(
    private val entryRepository: MusicRepository
) : ViewModel() {

    private val _state = MutableLiveData(MovieState())
    val state: LiveData<MovieState> = _state

    private val _action = MutableLiveData<Event<MovieAction>>()
    val action: LiveData<Event<MovieAction>> = _action



    private fun setState(block: MovieState.() -> MovieState) {
        _state.value = _state.value!!.block()
    }

    private fun sendAction(action: MovieAction) {
        _action.value = Event(action)
    }
}