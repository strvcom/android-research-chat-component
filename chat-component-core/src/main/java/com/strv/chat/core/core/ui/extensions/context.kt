package com.strv.chat.core.core.ui.extensions

import android.content.Context
import androidx.core.app.NotificationCompat

internal fun Context.notification(channelId: String, setup: NotificationCompat.Builder.() -> Unit) =
    NotificationCompat.Builder(this, channelId).apply(setup).build()