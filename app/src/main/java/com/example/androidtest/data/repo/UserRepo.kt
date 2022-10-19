package com.example.androidtest.data.repo

import com.example.androidtest.data.api.UserApi
import com.example.androidtest.data.model.UserBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepo @Inject constructor(
    private val userApi: UserApi
) {

    suspend fun getUserList() : List<UserBody> = withContext(Dispatchers.IO) {
        return@withContext userApi.getUsers(alt = "media", token = "e3672c23-b1a5-4ca7-bb77-b6580d75810c")
    }
}