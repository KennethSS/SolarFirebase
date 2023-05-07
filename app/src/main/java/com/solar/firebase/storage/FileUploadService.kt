package com.solar.firebase.storage

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.sample.basefirebase.storage.FirebaseStorageManager
import com.solar.firebase.presentation.MainActivity

/**
 *  Created by Kenneth on 12/22/20
 */
class FileUploadService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            val ref = intent.getStringExtra("reference")
            val uri = intent.getParcelableExtra<Uri>("uri")
            if (ref != null && uri != null) {
                startForeground(ref, uri)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startForeground(ref: String, uri: Uri) {
        val builder = if (Build.VERSION.SDK_INT >= 26) {
            val CHANNEL_ID = "snwodeer_service_channel"
            val channel = NotificationChannel(
                CHANNEL_ID,
                "SnowDeer Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
            NotificationCompat.Builder(this, CHANNEL_ID)
        } else {
            NotificationCompat.Builder(this)
        }

        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }

        val notification = builder
            .setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false)
            .setContentTitle("Store to $ref")
            .setContentText("Uploading..")
            .setSmallIcon(com.solar.firebase.R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)

        NotificationManagerCompat.from(this).apply {
            builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false)
            builder.setAutoCancel(true)
            notify(10, builder.build())

            FirebaseStorageManager.upload(ref, uri, {
            }, {
                Log.d(TAG, "progress:$it")
                if (it.toInt() == 100) {
                    stopForeground(true)
                } else {
                    notification.setProgress(PROGRESS_MAX, it.toInt(), false)
                    notify(10, builder.build())
                }
            })

            startForeground(10, notification.build())
        }
    }

    companion object {
        private const val TAG = "FileUploadService"
        private const val PROGRESS_MAX = 100
        private const val PROGRESS_CURRENT = 0
    }
}
