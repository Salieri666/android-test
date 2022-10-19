package com.example.androidtest.ui.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtest.domain.useCase.UserListUseCase
import com.example.androidtest.ui.screen.userListScreen.UserListScreenState
import kotlinx.coroutines.flow.*


class UserListScreenViewModel(
    private val userListUseCase: UserListUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow<UserListScreenState>(
        UserListScreenState.Default
    )
    val state: StateFlow<UserListScreenState> = _state.asStateFlow()

    init {
        userListUseCase.getUserList()
            .onStart {
                _state.value = UserListScreenState.Loading
            }
            .onEach {
                _state.value = UserListScreenState.Success(it)
            }
            .catch { cause ->
                _state.value = UserListScreenState.Error(cause)
            }
            .launchIn(viewModelScope)
    }

}