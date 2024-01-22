package com.example.chat.ui.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.SessionProvider
import com.example.chat.common.SingleLiveEvent
import com.example.chat.fireStore.MessageDao
import com.example.chat.model.Message
import com.example.chat.model.Room
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener

class ChatViewModel : ViewModel() {
    private var room: Room? = null
    val messageLiveData = MutableLiveData<String>()
    val toastLiveData = SingleLiveEvent<String>()
    val newMessageLiveData = SingleLiveEvent<List<Message>>()

    fun sendMessage() {

        if (messageLiveData.value.isNullOrBlank()) return
        val message = Message(
            content = messageLiveData.value,
            dateTime = Timestamp.now(),
            senderName = SessionProvider.user?.userName,
            senderId = SessionProvider.user?.id,
            roomId = room?.id
        )
        MessageDao.sendMessage(message) { task ->
            if (task.isSuccessful) {
                messageLiveData.value = ""
                return@sendMessage
            }
            toastLiveData.value = "something went wrong , please try again later "
        }
    }

    fun changeRoom(room: Room?) {
        this.room = room
        listForMessagesInRoom()
    }

    fun listForMessagesInRoom() {
        MessageDao.getMessageCollection(room?.id ?: "")
            .orderBy("dateTime")
            .limitToLast(100)
            .addSnapshotListener(EventListener { snapShot, error ->
                snapShot?.documents?.forEach { doc ->
                    val message = doc.toObject(Message::class.java)
                }
                val newMessages = mutableListOf<Message>()
                snapShot?.documentChanges?.forEach { docChange ->
                    if (docChange.type == DocumentChange.Type.ADDED) {
                        val message = docChange.document.toObject(Message::class.java)
                        newMessages.add(message)
                    } else if (docChange.type == DocumentChange.Type.MODIFIED) {

                    } else if (docChange.type == DocumentChange.Type.REMOVED) {

                    }
                }
                newMessageLiveData.value = newMessages
            })
    }
}