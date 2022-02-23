package com.example.dorecomic

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.dorecomic.utilities.NEW_COMIC_CHANNEL_ID
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import java.text.Normalizer

class ComicApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        createChannelNotification()
        activeFacebook()
    }

    private fun createChannelNotification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(NEW_COMIC_CHANNEL_ID,
                "new comic",
                NotificationManager.IMPORTANCE_DEFAULT)

            val manager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            manager.createNotificationChannel(channel)
        }
    }

    private fun activeFacebook(){
        FacebookSdk.sdkInitialize(applicationContext);
        AppEventsLogger.activateApp(this);
    }
}