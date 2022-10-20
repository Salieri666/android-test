package com.example.androidtest.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_friends_cache")
data class UserFriendsCacheEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk")
    val id: Long,

    @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "friend_user_id") val friendUserId: Long
)