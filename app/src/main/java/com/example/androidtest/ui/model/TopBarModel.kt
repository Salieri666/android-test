package com.example.androidtest.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopBarModel(
    val title: String,
    val showBackButton: Boolean
) : Parcelable
