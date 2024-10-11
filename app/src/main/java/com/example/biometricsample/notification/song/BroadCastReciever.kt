package com.example.biometricsample.notification.song
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MediaReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        when (intent?.action) {
            "ACTION_PLAY" -> {
                // Handle play action
                MediaPlayerManager(context).playSong()
                //showNotification(context, true)
            }
            "ACTION_PAUSE" -> {
                // Handle pause action
                MediaPlayerManager(context).pauseSong()
              //9  showNotification(context, false)
            }
        }
    }
}

