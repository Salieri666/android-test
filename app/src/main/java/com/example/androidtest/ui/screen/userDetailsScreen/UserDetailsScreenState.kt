package com.example.androidtest.ui.screen.userDetailsScreen

import com.example.androidtest.ui.model.UserUiModel

sealed class UserDetailsScreenState {

    data class Success(
        val user: UserUiModel
    ) : UserDetailsScreenState()

    data class Error(
        val throwable: Throwable
    ) : UserDetailsScreenState()

    object Loading : UserDetailsScreenState()
    object Default : UserDetailsScreenState()
}
