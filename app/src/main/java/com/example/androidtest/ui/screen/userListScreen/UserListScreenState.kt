package com.example.androidtest.ui.screen.userListScreen

import com.example.androidtest.ui.model.UserUiModel

sealed class UserListScreenState {
    data class Success(
        val list: List<UserUiModel>
    ) : UserListScreenState()

    data class Error(
        val exception: Exception
    ) : UserListScreenState()

    object Loading : UserListScreenState()
    object Default : UserListScreenState()
}
