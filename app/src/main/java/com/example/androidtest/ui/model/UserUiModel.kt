package com.example.androidtest.ui.model

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import com.example.androidtest.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserUiModel(
    val id: Long,
    val guid: String,
    val name: String,
    val email: String,
    val isActive: Boolean,
    val age: Int,
    val company: String,
    val phone: String,
    val address: String,
    val about: String,
    val eyeColor: String,
    val favoriteFruit: String,
    val registered: String,
    val friends: List<UserUiModel>,
    val coordinates: String
) : Parcelable

fun UserUiModel.getColorByEye(): Color {
    return when (this.eyeColor) {
        "blue" -> Color.Blue
        "green" -> Color.Green
        "brown" -> Color(0xFF995B00)
        else -> Color.Unspecified
    }
}

fun UserUiModel.getDrawableByFruit(): Int {
    return when (this.favoriteFruit) {
        "apple" -> R.drawable.ic_apple
        "banana" -> R.drawable.ic_banana
        "strawberry" -> R.drawable.ic_strawberry
        else -> -1
    }
}


