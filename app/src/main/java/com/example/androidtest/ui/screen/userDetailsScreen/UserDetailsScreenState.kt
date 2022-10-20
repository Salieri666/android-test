package com.example.androidtest.ui.screen.userDetailsScreen

import android.os.Parcelable
import com.example.androidtest.ui.model.UserUiModel
import kotlinx.parcelize.Parcelize

sealed class UserDetailsScreenState : Parcelable {

    @Parcelize
    data class Success(
        val user: UserUiModel
    ) : UserDetailsScreenState()

    @Parcelize
    data class Error(
        val throwable: Throwable
    ) : UserDetailsScreenState()

    @Parcelize
    object Loading : UserDetailsScreenState()

    @Parcelize
    object Default : UserDetailsScreenState()
}
