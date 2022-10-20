package com.example.androidtest.ui.screen.userDetailsScreen

sealed class UserDetailsScreenAction {
    data class OpenEmail(
        val email: String
    ): UserDetailsScreenAction()

    data class LoadUserDetail(
        val id: Long
    ): UserDetailsScreenAction()

    data class OpenPhone(
        val phone: String
    ): UserDetailsScreenAction()

    data class OpenMap(
        val latitude: Double,
        val longitude: Double
    ): UserDetailsScreenAction()
}
