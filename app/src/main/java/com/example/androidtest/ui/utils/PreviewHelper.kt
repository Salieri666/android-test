package com.example.androidtest.ui.utils

import com.example.androidtest.ui.model.UserUiModel

fun getUserUiModelList(size: Int) : List<UserUiModel> {
    return List(size) { index -> getUserUiModel(index.toLong()) }
}

fun getUserUiModel(id: Long) : UserUiModel {
    return UserUiModel(
        id,
        0,
        "1",
        "Test_name",
        "test@email.com",
        true,
        23,
        "Company_name",
        "+11111",
        "test_address",
        "about",
        "blue",
        "apple",
        "2022",
        emptyList(),
        "Coordinates_22",
        0.0, 0.0
    )
}