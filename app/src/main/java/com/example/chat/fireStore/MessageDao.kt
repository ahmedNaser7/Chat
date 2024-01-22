package com.example.chat.fireStore

import com.example.chat.model.Message
import com.example.chat.model.Message.Companion.CollectionName
import com.google.android.gms.tasks.OnCompleteListener


object MessageDao {
    fun getMessageCollection(roomId: String) =
        RoomsDao.getRoomsCollection()
            .document(roomId)
            .collection(CollectionName)

    fun sendMessage(message: Message, completeListener: OnCompleteListener<Void>) {

        val messageDoc = getMessageCollection(message.roomId ?: "")
            .document()
        message.id = messageDoc.id
        messageDoc.set(message)
            .addOnCompleteListener(completeListener)

    }
}