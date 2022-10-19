package com.example.androidtest.data.model

data class UserBody(
    val id: Long,
    val guid: String,
    val isActive: Boolean,
    val balance: String,
    val age: Int,
    val eyeColor: String,
    val name: String,
    val gender: String,
    val company: String,
    val email: String,
    val phone: String,
    val address: String,
    val about: String,
    val registered: String,
    val latitude: Double,
    val longitude: Double,
    val tags: List<String>,
    val favoriteFruit: String,
    val friends: List<Friends>
)

data class Friends(
    val id: Long
)
