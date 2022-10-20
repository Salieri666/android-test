package com.example.androidtest.ui.navigation


sealed class MainNavigation(val path: String) {
    object UserList: MainNavigation("userList")
    object UserDetails: MainNavigation("userDetailsScreen/{userId}") {
        val arg = "userId"

        fun pathWithArgs(userId: Long) : String {
            return "userDetailsScreen/${userId}"
        }
    }
}

