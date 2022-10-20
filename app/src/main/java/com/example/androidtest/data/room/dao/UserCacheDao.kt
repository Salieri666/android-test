package com.example.androidtest.data.room.dao

import androidx.room.*
import com.example.androidtest.data.room.entity.UserCacheEntity

@Dao
interface UserCacheDao {

    @Query("select * from user_cache")
    suspend fun getAll(): List<UserCacheEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<UserCacheEntity>)

    @Query("delete from user_cache")
    suspend fun deleteAll()

    @Transaction
    suspend fun updateCache(entities: List<UserCacheEntity>) {
        deleteAll()
        insertAll(entities)
    }

    @Query("select * from user_cache where pk = :userId")
    suspend fun getUserById(userId: Long): UserCacheEntity

    @Query("select u.* from user_friends_cache uf " +
            "join user_cache u on u.outer_id = uf.friend_user_id " +
            "where uf.user_id = :outerUserId ")
    suspend fun getUserFriendsById(outerUserId: Long): List<UserCacheEntity>
}