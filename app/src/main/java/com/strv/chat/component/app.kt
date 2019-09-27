package com.strv.chat.component

import android.app.Application
import android.app.NotificationManager
import android.app.Service
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.strv.chat.component.di.CompositionRoot
import com.strv.chat.library.cloudStorage.di.cloudStorageMediaClient
import com.strv.chat.library.core.session.ChatComponent
import com.strv.chat.library.core.session.config.Configuration
import com.strv.chat.library.core.ui.extensions.serviceConfig
import com.strv.chat.library.firestore.di.firestoreChatClient
import com.strv.chat.library.firestore.di.firestoreConversationClient

private const val CHANNEL_ID = "upload"
private const val CHANNEL_DESCRIPTION = "ImageModel upload"

class App : Application() {

    private lateinit var compositionRoot: CompositionRoot

    val firestoreDb by lazy {
        FirebaseFirestore.getInstance()
    }

    val firebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }

    private val zoeNotificationChannels
        @RequiresApi(Build.VERSION_CODES.O)
        get() = listOf(
            notificationChannel(
                CHANNEL_ID,
                CHANNEL_DESCRIPTION,
                NotificationManager.IMPORTANCE_DEFAULT
            ) {
                vibration = true
                lights = true
                showBadge = true
                setSound(null, null)
            }
        )

    override fun onCreate() {
        super.onCreate()

        compositionRoot = CompositionRoot()

        //register notification channels
        (getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager).run {
            zoeNotificationChannels.forEach { channel ->
                createNotificationChannel(channel)
            }
        }

        ChatComponent.init(
            Configuration(
                firestoreChatClient(firestoreDb),
                firestoreConversationClient(firestoreDb),
                cloudStorageMediaClient(firebaseStorage),
                serviceConfig("upload"),
                compositionRoot.memberProvider()
            )
        )
    }

    fun compositionRoot() =
        compositionRoot
}