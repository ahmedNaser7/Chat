package com.example.chat.model

import com.example.chat.R

data class Category(

    val id: Int,
    val title: String,
    val imageId: Int,
) {
    companion object {

        fun getCategories() =
            listOf<Category>(
                Category(
                    id = 1,
                    title = "Sport",
                    imageId = R.drawable.sports,
                ),
                Category(
                    id = 2,
                    title = "Music",
                    imageId = R.drawable.music,
                ),
                Category(
                    id = 3,
                    title = "Movies",
                    imageId = R.drawable.movies,
                ),

                )

        fun getCategoriesImageByCategoryId(catId: Int?): Int {
            return when (catId) {
                1 -> R.drawable.sports
                2 -> R.drawable.music
                3 -> R.drawable.movies
                else -> {
                    R.drawable.sports
                }
            }
        }
    }
}