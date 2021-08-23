package br.com.capivaras.android_pagbank.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import br.com.capivaras.android_pagbank.R
import br.com.capivaras.android_pagbank.features.intro.IntroActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class FirebaseServiceMessage : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        notificatioReceived( remoteMessage.notification?.title, remoteMessage.notification?.body)
        if(remoteMessage.notification?.body?.contains("PIX", true) == true) {
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(Intent("ON_PIX_RECEIVED"))
        }
    }

    fun notificatioReceived( title: String?, message: String?) {

        val notificationChannel: NotificationChannel
        val builder: NotificationCompat.Builder
        val channelId = "br.com.capivaras"
        val notificationManager: NotificationManager = applicationContext
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val id = Random.nextInt(1, 100)
        val intent = Intent(this, IntroActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel =
                NotificationChannel(channelId, message, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = NotificationCompat.Builder(this, channelId)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(message)
                        .setSummaryText("Notificação")
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
        } else {
            builder = NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(id, builder.build())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}
