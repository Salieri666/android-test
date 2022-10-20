package com.example.androidtest.data.room.dao

import androidx.room.*
import com.example.androidtest.data.room.entity.UserFriendsCacheEntity

@Dao
interface UserFriendsCacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<UserFriendsCacheEntity>)

    @Query("delete from user_friends_cache")
    suspend fun deleteAll()

    @Transaction
    suspend fun updateCache(entities: List<UserFriendsCacheEntity>) {
        deleteAll()
        insertAll(entities)
    }

}