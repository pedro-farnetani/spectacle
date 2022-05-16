package com.pedrofarnetani.spectacle.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.pedrofarnetani.spectacle.R
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
internal class SplashActivity : AppCompatActivity(R.layout.activity_splash) {

    // Como estou usando uma API genérica no momento, não faz sentido ativar o Spotify por enquanto
    /*override fun onStart() {
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
    }*/

    override fun onResume() {
        super.onResume()

        goToSpectacleActivity()
    }

    private fun goToSpectacleActivity() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, SpectacleActivity::class.java)
            startActivity(intent)
        }, 3000)
    }
}