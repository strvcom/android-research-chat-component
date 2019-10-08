package com.strv.chat.core.core.ui.conversation

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.strv.chat.core.core.session.ChatComponent
import com.strv.chat.core.core.session.ChatComponent.conversationAdapter
import com.strv.chat.core.core.session.ChatComponent.conversationViewHolderProvider
import com.strv.chat.core.core.session.ChatComponent.memberClient
import com.strv.chat.core.core.session.ChatComponent.memberProvider
import com.strv.chat.core.core.ui.conversation.adapter.ConversationAdapter
import com.strv.chat.core.core.ui.conversation.adapter.ConversationViewHolderProvider
import com.strv.chat.core.core.ui.conversation.data.ConversationItemView
import com.strv.chat.core.core.ui.conversation.data.creator.ConversationItemViewConfiguration
import com.strv.chat.core.core.ui.conversation.data.creator.ConversationItemViewCreator
import com.strv.chat.core.core.ui.conversation.style.ConversationRecyclerViewStyle
import com.strv.chat.core.core.ui.extensions.OnClickAction
import com.strv.chat.core.domain.Disposable
import com.strv.chat.core.domain.collect
import com.strv.chat.core.domain.mapIterable
import strv.ktools.logE
import java.util.LinkedList

class ConversationRecyclerView @JvmOverloads constructor(
    context: Context,
    val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val disposable = LinkedList<Disposable>()

    private var conversationAdapter: ConversationAdapter
        get() = super.getAdapter() as ConversationAdapter
        private set(value) = super.setAdapter(value)

    private var style: ConversationRecyclerViewStyle? = null

    init {
        if (attrs != null) {
            style = ConversationRecyclerViewStyle.parse(context, attrs)
        }
    }

    @JvmOverloads
    fun init(
        viewHolderProvider: ConversationViewHolderProvider = conversationViewHolderProvider(),
        layoutManager: LinearLayoutManager? = null,
        onConversationClick: OnClickAction<ConversationItemView>
    ) {
        adapter = conversationAdapter(viewHolderProvider, onConversationClick, style)
        setLayoutManager(layoutManager ?: LinearLayoutManager(context))
    }

    fun onStart() =
        ChatComponent.conversationClient().subscribeConversations(
            memberProvider().currentUserId()
        ).onError { error ->
            logE(error.localizedMessage ?: "Unknown error")
        }.mapIterable { model ->
            ConversationItemViewCreator.create(
                ConversationItemViewConfiguration(
                    model,
                    memberClient()
                )
            )
        }.onNext { list ->
            onConversationsChanged(list)
        }.also { task ->
            disposable.add(task)
        }


    fun onStop() {
        disposable.collect(Disposable::dispose)
    }

    private fun onConversationsChanged(items: List<ConversationItemView>) {
        conversationAdapter.submitList(items)
    }
}