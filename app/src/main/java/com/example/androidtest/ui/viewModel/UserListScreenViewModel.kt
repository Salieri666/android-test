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

    private val _state = MutableStateFlow(
        savedStateHandle.get<UserListScreenState>("state") ?: UserListScreenState.Default
    )
    val state: StateFlow<UserListScreenState> = _state.asStateFlow()

    init {

        if (_state.value is UserListScreenState.Default) {
            userListUseCase.getUserList()
                .onStart {
                    setState(UserListScreenState.Loading)
                }
                .onEach {
                    setState(UserListScreenState.Success(it))
                }
                .catch { cause ->
                    setState(UserListScreenState.Error(cause))
                }
                .launchIn(viewModelScope)
        }
    }

    private fun setState(state: UserListScreenState) {
        savedStateHandle["state"] = state
        _state.value = state
    }

}