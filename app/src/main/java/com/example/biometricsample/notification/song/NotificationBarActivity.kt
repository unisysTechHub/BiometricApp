package com.example.biometricsample.notification.song

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.biometricsample.R
const val CHANNEL_ID = "PLAY_SONG_CHANNEL"
class NotificationBarActivity : AppCompatActivity() {
    private lateinit var mediaPlayerManager: MediaPlayerManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mediaPlayerManager = MediaPlayerManager(this.applicationContext)
         setContentView(R.layout.activity_notification_bar)
        showNotification(this,true,mediaPlayerManager)

        // Start background music with WorkManager
        //startMusicInBackground(this)
    }
    fun startMusicInBackground(context: Context) {
        val workRequest = OneTimeWorkRequestBuilder<MediaWorker>().build()
        WorkManager.getInstance(context).enqueue(workRequest)
    }
    @Composable
    fun MusicPlayerUI(mediaPlayerManager: MediaPlayerManager) {
        var isPlaying by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                if (isPlaying) {
                    mediaPlayerManager.pauseSong()
                } else {
                    mediaPlayerManager.playSong()
                }
                isPlaying = !isPlaying
                showNotification(context = this@NotificationBarActivity, isPlaying = isPlaying,mediaPlayerManager)
            }) {
                Text(if (isPlaying) "Pause" else "Play")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Song progress:")
            LinearProgressIndicator(progress = 0.5f) // Placeholder for progress (You can bind to MediaPlayer's progress)
        }
    }

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, "Music Player", NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Channel for music playback"
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}


