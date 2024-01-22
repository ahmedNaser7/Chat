package com.example.chat.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.ui.Message
import com.example.chat.SessionProvider
import com.example.chat.common.SingleLiveEvent
import com.example.chat.fireStore.UserDao
import com.example.chat.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterViewModel : ViewModel() {

    val messageLiveData = SingleLiveEvent<Message>()
    val userName = MutableLiveData<String>()
    val EmailAddress = MutableLiveData<String>()
    val Password = MutableLiveData<String>()
    val PasswordConfirm = MutableLiveData<String>()

    val userNameError = MutableLiveData<String?>()
    val EmailAddressError = MutableLiveData<String?>()
    val PasswordError = MutableLiveData<String?>()
    val PasswordConfirmError = MutableLiveData<String?>()

    val events = SingleLiveEvent<RegisterViewEvent>()

    val auth = Firebase.auth
    fun register() {
        if (!validForm()) return

        auth.createUserWithEmailAndPassword(EmailAddress.value!!, Password.value!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // is loding
                    insertInFireStore(task.result.user?.uid)


                } else {


                    messageLiveData.postValue(
                        Message(
                            message = task.exception?.localizedMessage,
                        )
                    )
                }
            }

    }

    private fun insertInFireStore(uid: String?) {
        val user = User(
            id = uid,
            userName = userName.value,
            email = EmailAddress.value
        )

        UserDao.createUser(user) { task ->
            if (task.isSuccessful) {

                messageLiveData.postValue(
                    Message(
                        message = "User Registered Successfully !!",
                        posActionName = "ok",
                        onPosActionClick = {
                            // save user
                            SessionProvider.user = user
                            // navigate to home
                            events.postValue(RegisterViewEvent.NavigateToHome)
                        }
                    )
                )

            } else {

            }
        }
    }


    private fun validForm(): Boolean {
        var isVaild = true
        if (userName.value.isNullOrBlank()) {
            userNameError.postValue("please Enter user name")
            isVaild = false
        } else {
            userNameError.postValue(null)
        }

        if (EmailAddress.value.isNullOrBlank()) {
            EmailAddressError.postValue("please Enter gmail again")
            isVaild = false
        } else {
            EmailAddressError.postValue(null)
        }

        if (Password.value.isNullOrBlank()) {
            PasswordError.postValue("please Enter Password")
            isVaild = false
        } else {
            PasswordError.postValue(null)
        }

        if (PasswordConfirm.value.isNullOrBlank() || Password.value != PasswordConfirm.value) {
            PasswordConfirmError.postValue("please Enter Password  Confirm")
            isVaild = false
        } else {
            PasswordConfirmError.postValue(null)
        }
        return isVaild
    }

    fun navigateToLogin() {
        events.postValue(RegisterViewEvent.NavigateToLogin)
    }

}