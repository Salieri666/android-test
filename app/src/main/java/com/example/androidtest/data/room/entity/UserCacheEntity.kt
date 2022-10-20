package com.example.androidtest.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_cache")
data class UserCacheEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk")
    val id: Long,

    @ColumnInfo(name = "outer_id") val outerId: Long,
    val guid: String,
    @ColumnInfo(name = "is_active") val isActive: Boolean,
    val age: Int,
    val eyeColor: String,
    val name: String,
    val company: String,
    val email: String,
    val phone: String,
    val address: String,
    val about: String,
    val registered: String,
    val latitude: Double,
    val longitude: Double,
    val favoriteFruit: String,
)
