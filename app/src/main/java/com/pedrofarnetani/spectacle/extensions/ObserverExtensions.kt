package com.pedrofarnetani.spectacle.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.pedrofarnetani.spectacle.arch.Event
import com.pedrofarnetani.spectacle.viewmodel.state.LoginAction

internal fun <T> LiveData<Event<T>>.observeSingleEvent(owner: Fragment, observer: (T) -> Unit) {
    this.observe(owner.viewLifecycleOwner) { event ->
        event.getContentIfNotHandled()?.let { observer(it) }
    }
}

internal inline fun <reified T> LiveData<Event<LoginAction>>.observeActions(
    fragment: Fragment,
    crossinline observer: (T) -> Unit
) {
    this.observeSingleEvent(fragment) { action ->
        if (action !is T) return@observeSingleEvent
        observer(action)
    }
}