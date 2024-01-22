package com.example.chat.model

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

data class Message(
    var id: String? = null,
    val content: String? = null,
    val dateTime: Timestamp? = null,
    val senderName: String? = null,
    val senderId: String? = null,
    val roomId: String? = null,
) {
    companion object {
        const val CollectionName = "messages"
    }

    fun formatTime(): String {
        val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return simpleDateFormat.format(dateTime?.toDate())
    }
}