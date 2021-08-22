package com.example.retrofitwithcoroutine_u_test
import com.google.gson.annotations.SerializedName

// this JSON class file is from
// https://jsonplaceholder.typicode.com/albums
data class AlbumsItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("userId")
    val userId: Int
)