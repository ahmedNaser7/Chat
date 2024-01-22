package com.example.chat.ui.splash

import androidx.lifecycle.ViewModel
import com.example.chat.SessionProvider
import com.example.chat.common.SingleLiveEvent
import com.example.chat.fireStore.UserDao
import com.example.chat.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashViewModel : ViewModel() {

    val events = SingleLiveEvent<SplashViewEvents>()

    fun redirect() {
        if (Firebase.auth.currentUser == null) {
            events.postValue(SplashViewEvents.navigateToLogin)
            return
        }
        UserDao.getUser(
            Firebase.auth.currentUser?.uid ?: ""
        ) { task ->
            if (!task.isSuccessful) {
                events.postValue(SplashViewEvents.navigateToLogin)
                return@getUser
            }
            val user = task.result.toObject(User::class.java)
            SessionProvider.user = user
            events.postValue(SplashViewEvents.navigateToHome)
        }
    }

}