package com.example.chat.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.ui.Message
import com.example.chat.SessionProvider
import com.example.chat.common.SingleLiveEvent
import com.example.chat.fireStore.UserDao
import com.example.chat.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {

    val messageLiveData = SingleLiveEvent<Message>()

    val EmailAddress = MutableLiveData<String>()
    val Password = MutableLiveData<String>()
    val EmailAddressError = MutableLiveData<String?>()
    val PasswordError = MutableLiveData<String?>()

    val events = SingleLiveEvent<LoginActivityEvent>()

    val auth = Firebase.auth
    fun login() {
        if (!validForm()) return

        auth.signInWithEmailAndPassword(EmailAddress.value!!, Password.value!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // is loding

                    getUserFromFireStore(task.result.user?.uid)

                } else {
                    messageLiveData.postValue(
                        Message(
                            message = task.exception?.localizedMessage,
                        )
                    )
                }
            }

    }

    private fun getUserFromFireStore(uid: String?) {
        UserDao
            .getUser(uid) { task ->
                if (task.isSuccessful) {
                    val user = task.result.toObject(User::class.java)
                    SessionProvider.user = user

                    messageLiveData.postValue(
                        Message(
                            message = "Logged in successfully ",
                            posActionName = "ok",
                            onPosActionClick = {
                                events.postValue(LoginActivityEvent.navigateToHome)
                            },
                            isCancelable = false
                        )
                    )
                } else {
                    messageLiveData.postValue(
                        Message(
                            message = task.exception?.localizedMessage,
                        )
                    )
                }

            }
    }


    private fun validForm(): Boolean {
        var isVaild = true

        if (EmailAddress.value.isNullOrBlank()) {
            EmailAddressError.postValue("please Enter user name")
            isVaild = false
        } else {
            EmailAddressError.postValue(null)
        }

        if (Password.value.isNullOrBlank()) {
            PasswordError.postValue("please Enter user name")
            isVaild = false
        } else {
            PasswordError.postValue(null)
        }

        return isVaild
    }

    fun navigateToRegister() {
        events.postValue(LoginActivityEvent.naviagteToRegister)
    }

}