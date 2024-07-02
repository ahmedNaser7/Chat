package com.example.chat.ui.addRoom

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.ui.Message
import com.example.chat.SessionProvider
import com.example.chat.common.SingleLiveEvent
import com.example.chat.fireStore.RoomsDao
import com.example.chat.model.Category

class AddRoomViewModel : ViewModel() {

    val events = SingleLiveEvent<AddRoomViewEvents>()
    val roomTitle = MutableLiveData<String>()
    val roomDesc = MutableLiveData<String>()
    val roomTitleError = MutableLiveData<String?>()
    val roomDescError = MutableLiveData<String?>()
    val categories = Category.getCategories()
    val messageLiveData = SingleLiveEvent<Message>()
    var selectCategory: Category = categories[0]
    fun createRoom() {
        if (!validForm()) return

        RoomsDao.createRoom(
            title = roomTitle.value ?: "",
            desc = roomDesc.value ?: "",
            ownerId = SessionProvider.user?.id ?: "",
            categoryId = selectCategory.id,
        ) { task ->
            if (task.isSuccessful) {
                messageLiveData.postValue(
                    Message(
                        message = "room created successfully",
                        posActionName = "ok",
                        onPosActionClick = {
                            events.postValue(AddRoomViewEvents.NavigteToHomeAndFinish)
                        }
                    )
                )
                return@createRoom
            }

            messageLiveData.postValue(
                Message(
                    message = "something went wrong ${
                        task.exception?.localizedMessage
                    }",
                    posActionName = "ok"

                )
            )
        }
    }

    private fun validForm(): Boolean {
        var isVaild = true

        if (roomTitle.value.isNullOrBlank()) {
            roomTitleError.postValue("please Enter room title")
            isVaild = false
        } else {
            roomTitleError.postValue(null)
        }

        if (roomDesc.value.isNullOrBlank()) {
            roomDescError.postValue("please Enter room description")
            isVaild = false
        } else {
            roomDescError.postValue(null)
        }

        return isVaild
    }

    fun onCategorySelected(position: Int) {
        selectCategory = categories[position]
    }
}
