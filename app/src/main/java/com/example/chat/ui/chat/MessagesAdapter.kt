package com.example.chat.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chat.SessionProvider
import com.example.chat.databinding.ItemReceivedMessageBinding
import com.example.chat.databinding.ItemSentMessageBinding
import com.example.chat.model.Message

class MessagesAdapter(val messages: MutableList<Message>) : Adapter<ViewHolder>() {


    class SentMessageViewHolder(val itemBinding: ItemSentMessageBinding) :
        ViewHolder(itemBinding.root) {

        fun bind(message: Message) {
            itemBinding.setMessage(message)
            itemBinding.executePendingBindings()
        }
    }

    class ReceivedMessageViewHolder(val itemBinding: ItemReceivedMessageBinding) :
        ViewHolder(itemBinding.root) {
        fun bind(message: Message) {
            itemBinding.setMessage(message)
            itemBinding.executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages.get(position)
        if (message.senderId == SessionProvider.user?.id) {
            return MessageType.Sent.value
        } else {
            return MessageType.Received.value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == MessageType.Sent.value) {
            val itemBinding =
                ItemSentMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SentMessageViewHolder(itemBinding)
        } else {
            val itemBinding = ItemReceivedMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ReceivedMessageViewHolder(itemBinding)
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        when (holder) {
            is SentMessageViewHolder -> {
                // smart cast
                holder.bind(messages[position])
            }

            is ReceivedMessageViewHolder -> {
                // smart cast
                holder.bind(messages[position])
            }
        }
    }

    fun addNewMessage(newMessage: List<Message>?) {
        if (newMessage == null) return
        val oldSize = messages.size
        messages.addAll(newMessage)
        notifyItemRangeChanged(oldSize, newMessage.size)
    }

    enum class MessageType(val value: Int) {
        Received(100),
        Sent(200)
    }
}