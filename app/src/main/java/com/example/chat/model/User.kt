package com.example.chat.model

data class User(
    val id: String? = null,
    val userName: String? = null,
    val email: String? = null,
) {

    companion object {
        const val CollectionName = "users"
    }

}