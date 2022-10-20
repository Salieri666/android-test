package com.example.androidtest.data.repo

import androidx.room.Transaction
import com.example.androidtest.data.api.UserApi
import com.example.androidtest.data.model.UserBody
import com.example.androidtest.data.room.dao.UserCacheDao
import com.example.androidtest.data.room.dao.UserFriendsCacheDao
import com.example.androidtest.data.room.entity.UserCacheEntity
import com.example.androidtest.data.room.entity.UserFriendsCacheEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepo @Inject constructor(
    private val userApi: UserApi,
    private val userCacheDao: UserCacheDao,
    private val userFriendsCacheDao: UserFriendsCacheDao
) {

    suspend fun getUserListFromApi() : List<UserBody> = withContext(Dispatchers.IO) {
        return@withContext userApi.getUsers(alt = "media", token = "e3672c23-b1a5-4ca7-bb77-b6580d75810c")
    }

    suspend fun getUserListFromCache() : List<UserCacheEntity> = withContext(Dispatchers.IO) {
        return@withContext userCacheDao.getAll()
    }

    @Transaction
    suspend fun updateCache(users: List<UserBody>) = withContext(Dispatchers.IO) {
        val userEntityList = ArrayList<UserCacheEntity>()
        val userFriendsEntityList = ArrayList<UserFriendsCacheEntity>()

        users.forEach { user ->
            userEntityList.add(toEntity(user))

            user.friends.forEach { friends ->
                userFriendsEntityList.add(
                    UserFriendsCacheEntity(
                        0,
                        user.id,
                        friends.id
                    )
                )
            }
        }

        userCacheDao.updateCache(userEntityList)
        userFriendsCacheDao.updateCache(userFriendsEntityList)
    }

    suspend fun getUserById(userId: Long): UserCacheEntity = withContext(Dispatchers.IO) {
        return@withContext userCacheDao.getUserById(userId)
    }

    suspend fun getUserFriendsByOuterId(outerId: Long): List<UserCacheEntity> = withContext(Dispatchers.IO) {
        return@withContext userCacheDao.getUserFriendsById(outerId)
    }

    private fun toEntity(user: UserBody): UserCacheEntity {
        return UserCacheEntity(
            0,
            user.id,
            user.guid,
            user.isActive,
            user.age,
            user.eyeColor,
            user.name,
            user.company,
            user.email,
            user.phone,
            user.address,
            user.about,
            user.registered,
            user.latitude,
            user.longitude,
            user.favoriteFruit
        )
    }
}