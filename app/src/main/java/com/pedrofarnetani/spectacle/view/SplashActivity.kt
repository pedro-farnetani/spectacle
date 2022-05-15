package com.pedrofarnetani.spectacle.view

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pedrofarnetani.spectacle.R
import com.pedrofarnetani.spectacle.services.SpotifyService
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
internal class SplashActivity : AppCompatActivity(R.layout.activity_splash) {

    override fun onStart() {
        super.onStart()

        connectSpotify()
    }

    private fun connectSpotify() {
        SpotifyService.connect(this) { connected ->
            if (connected) {
                val intent = Intent(this, SpectacleActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, getString(R.string.spotify_connection_error), Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}