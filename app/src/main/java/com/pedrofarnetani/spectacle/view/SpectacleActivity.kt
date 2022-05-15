package com.pedrofarnetani.spectacle.view

import androidx.appcompat.app.AppCompatActivity
import com.pedrofarnetani.spectacle.R
import com.pedrofarnetani.spectacle.services.SpotifyService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class SpectacleActivity : AppCompatActivity(R.layout.activity_spectacle) {

    override fun onDestroy() {
        super.onDestroy()
        SpotifyService.disconnect()
    }
}