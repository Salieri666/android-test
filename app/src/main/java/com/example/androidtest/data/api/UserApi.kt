package com.example.androidtest.data.api

import com.example.androidtest.data.model.UserBody
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("users.json?")
    suspend fun getUsers(
        @Query("alt") alt: String,
        @Query("token") token: String
    ) : List<UserBody>
}