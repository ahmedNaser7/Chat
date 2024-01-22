package com.example.chat.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.ui.Message
import com.example.chat.SessionProvider
import com.example.chat.common.SingleLiveEvent
import com.example.chat.fireStore.RoomsDao
import com.example.chat.model.Room
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeViewModel : ViewModel() {

    val events = SingleLiveEvent<HomeViewEvents>()
    val roomsLiveData = MutableLiveData<List<Room>>()
    val messageLiveData = SingleLiveEvent<Message>()

    fun navigateToAddRoom() {
        events.postValue(HomeViewEvents.navigateToAddRoom)
    }

    fun loadRooms() {
        RoomsDao
            .getAllRooms() { task ->
                if (task.isSuccessful) {
                    // show message
                }
                val rooms = task.result.toObjects(Room::class.java)
                roomsLiveData.postValue(rooms)
            }
    }

    fun logout() {
        Firebase.auth.signOut()
        SessionProvider.user = null
        events.postValue(HomeViewEvents.navigateToLogIn)
    }
}