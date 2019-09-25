package com.strv.chat.library.core.ui.chat.messages.adapter

import android.view.ViewGroup

interface ChatItemBinder {

    fun headerBinder(parent: ViewGroup): HeaderViewHolder =
        DefaultHeaderViewHolder(parent)

    fun myMessageBinder(parent: ViewGroup): MyMessageViewHolder =
        DefaultMyMessageViewHolder(parent)

    fun otherMessageBinder(parent: ViewGroup): OtherMessageViewHolder =
        DefaultOtherMessageViewHolder(parent)

    fun myImageBinder(parent: ViewGroup): MyImageViewHolder =
        DefaultMyImageViewHolder(parent)

    fun otherImageBinder(parent: ViewGroup): OtherImageViewHolder =
        DefaultOtherImageViewHolder(parent)

}

class DefaultChatItemBinder: ChatItemBinder