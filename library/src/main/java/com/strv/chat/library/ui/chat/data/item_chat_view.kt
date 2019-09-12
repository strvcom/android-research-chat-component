package com.strv.chat.library.ui.chat.data

import com.strv.chat.library.ui.data.MemberView
import java.util.Date

sealed class ChatItemView(open val sentDate: Date) {

    data class Header(
        override val sentDate: Date
    ): ChatItemView(sentDate)

    data class MyTextMessage(
        override val sentDate: Date,
        val text: String,
        val showSentDate: Boolean = false
    ): ChatItemView(sentDate)

    data class OtherTextMessage(
        override val sentDate: Date,
        val sender: MemberView,
        val text: String,
        val showSentDate: Boolean = false
    ) : ChatItemView(sentDate)

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}