package com.example.androidtest.data.api

import com.example.androidtest.data.model.UserBody
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("users.json?")
    suspend fun getUsers(
        @Query("alt") alt: String = "media",
        @Query("token") token: String = "e3672c23-b1a5-4ca7-bb77-b6580d75810c"
    ) : List<UserBody>
}