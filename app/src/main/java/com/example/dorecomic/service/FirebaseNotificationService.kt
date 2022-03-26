package com.example.dorecomic.service

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.dorecomic.ComicApplication
import com.example.dorecomic.R
import com.example.dorecomic.activity.LaunchActivity
import com.example.dorecomic.utilities.NEW_COMIC_CHANNEL_ID
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage

class FirebaseNotificationService : FirebaseMessagingService() {

    private lateinit var token: String

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
//
//        FirebaseMessaging.getInstance().token.addOnCompleteListener(
//            OnCompleteListener { task ->
//                if(!task.isSuccessful) {
//                    Log.d("Tag", "Fetching fcm registration token failed", task.exception)
//                    return@OnCompleteListener
//                }
//                token = task.result
//            }
//        )

        val notification: RemoteMessage.Notification = p0.notification ?: return
        val strTitle: String = notification.title ?: "Thông báo"
        val strMess: String = notification.body ?: "<Trống>"

//        pushNotification(strTitle, strMess)
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }
//
//    @SuppressLint("UnspecifiedImmutableFlag")
//    private fun pushNotification(title: String, mess: String) {
//        val intent = Intent(this, LaunchActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
//
//        val builder = NotificationCompat.Builder(this, NEW_COMIC_CHANNEL_ID)
//            .setSmallIcon(R.drawable.default_comic)
//            .setContentTitle(title)
//            .setContentText(mess)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setContentIntent(pendingIntent)
//            .setAutoCancel(true)
//
//        with(NotificationManagerCompat.from(this)) {
//            notify(1, builder.build())
//        }
//    }
}