package com.example.androidtest.domain.model

import android.os.Parcelable
import com.example.androidtest.ui.model.UserUiModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetails(
    val user: UserUiModel,
    val userFriends: List<UserUiModel>? = null
) : Parcelable
