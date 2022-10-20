package com.example.androidtest.ui.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.androidtest.domain.useCase.UserListUseCase
import com.example.androidtest.ui.model.UserUiModel
import com.example.androidtest.ui.screen.userDetailsScreen.UserDetailsScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class UserDetailsScreenViewModel(
    private val userListUseCase: UserListUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val userId: Long?
): ViewModel() {

    private val _state = MutableStateFlow<UserDetailsScreenState>(
        UserDetailsScreenState.Default
    )
    val state: StateFlow<UserDetailsScreenState> = _state.asStateFlow()

    init {
        val item =  UserUiModel(
            1, "1", "Test_name", "test@email.com", true,
            23, "Company_name", "+11111",
            "test_address", "about", "blue",
            "apple",
            "2022",
            emptyList(),
            "Coordinates_22"
        )
        val list = List(23) {item}

        val user = item.copy(friends = list)
        val state = UserDetailsScreenState.Success(user)

        _state.value = state
    }

}