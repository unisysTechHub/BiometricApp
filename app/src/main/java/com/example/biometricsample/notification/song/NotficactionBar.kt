package com.example.biometricsample.notification.song

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.biometricsample.MainActivity
import com.example.biometricsample.R

@SuppressLint("WrongConstant")
fun showNotification(context: Context, isPlaying: Boolean, mediaPlayerManager: MediaPlayerManager) {
    val NOTIFICATION_ID = 1
    val playPauseAction = if (isPlaying) {
        NotificationCompat.Action.Builder(
            R.drawable.ic_launcher_background, "Pause", getActionIntent(context, "ACTION_PAUSE")
        ).build()
    } else {
        NotificationCompat.Action.Builder(
            R.drawable.ic_launcher_background, "Play", getActionIntent(context, "ACTION_PLAY")
        ).build()
    }
    val intent = Intent(context, MainActivity::class.java);
    val intArray = intArrayOf(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    val flags =  PendingIntent.FLAG_UPDATE_CURRENT

    val pendingIntent = PendingIntent.getActivity(context, 0, intent,
     flags )

    // use pending inent.
   //   val remoteViews =  RemoteViews(context.applicationContext.packageName,R.layout.remote_view)
    val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setContentTitle("Playing Song")
        .setContentText("Now playing your song")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .addAction(playPauseAction)
        .setOngoing(isPlaying)
        .setContentIntent(pendingIntent)
   //     .setContent(ComposeView((context as NotificationBarActivity).apply {
//            setContent {
//                MusicPlayerUI(mediaPlayerManager)
//            }
//        }))
        .build()

    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)

        return
    }
}

private fun getActionIntent(context: Context, action: String): PendingIntent {
    val intent = Intent(context, MediaReceiver::class.java).apply {
        this.action = action
    }
    return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
}