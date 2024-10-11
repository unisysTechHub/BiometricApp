package com.example.biometricsample.notification.song

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class MediaWorker(context: Context, workerParams: WorkerParameters, val mediaPlayerManager: MediaPlayerManager) : Worker(context, workerParams) {
    override fun doWork(): Result {
        // Start the MediaPlayer in background
        MediaPlayerManager(applicationContext).playSong()

        // Show notification to control playback
        showNotification(applicationContext, true, mediaPlayerManager = mediaPlayerManager)

        return Result.success()
    }
}