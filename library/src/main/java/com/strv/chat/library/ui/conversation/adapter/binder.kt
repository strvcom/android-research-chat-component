package com.strv.chat.library.ui.conversation.adapter

import android.view.ViewGroup

interface ConversationBinder {

    fun conversationBinder(parent: ViewGroup): ConversationViewHolder =
        DefaultConversationViewHolder(parent)
}

internal class DefaultConversationBinder: ConversationBinder
