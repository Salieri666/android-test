package com.example.androidtest.ui.screen.userListScreen

import android.os.Parcelable
import com.example.androidtest.ui.model.UserUiModel
import kotlinx.parcelize.Parcelize


sealed class UserListScreenState : Parcelable {
    @Parcelize
    data class Success(
        val list: List<UserUiModel>
    ) : UserListScreenState()

    @Parcelize
    data class Error(
        val throwable: Throwable
    ) : UserListScreenState()

    @Parcelize
    object ConnectionProblem : UserListScreenState()

    @Parcelize
    object Loading : UserListScreenState()

    @Parcelize
    object Default : UserListScreenState()
}

