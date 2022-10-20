package com.example.androidtest.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidtest.data.room.dao.UserCacheDao
import com.example.androidtest.data.room.dao.UserFriendsCacheDao
import com.example.androidtest.data.room.entity.UserCacheEntity
import com.example.androidtest.data.room.entity.UserFriendsCacheEntity

@Database(
    entities = [UserCacheEntity::class, UserFriendsCacheEntity::class],
    version = 1,
    exportSchema = true
)
abstract class DatabaseUsers: RoomDatabase() {

    abstract fun userCacheDao(): UserCacheDao

    abstract fun userFriendsCacheDao(): UserFriendsCacheDao
}