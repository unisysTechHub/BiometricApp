package com.example.biometricsample.notification.song

import android.media.MediaPlayer
import android.content.Context
import com.example.biometricsample.R

class MediaPlayerManager(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null

    fun playSong() {
     //  mediaPlayer = MediaPlayer.create(context, R.drawable.song) // R.raw.sample_song is your resource file
        mediaPlayer?.start()
    }

    fun pauseSong() {
        mediaPlayer?.pause()
    }

    fun stopSong() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying == true
    }
}