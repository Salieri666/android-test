package com.example.androidtest.ui.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtest.domain.useCase.UserUseCase
import com.example.androidtest.ui.screen.userListScreen.UserListScreenAction
import com.example.androidtest.ui.screen.userListScreen.UserListScreenState
import kotlinx.coroutines.flow.*


class UserListScreenViewModel(
    private val userListUseCase: UserUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(
        savedStateHandle.get<UserListScreenState>("state") ?: UserListScreenState.Default
    )
    val state: StateFlow<UserListScreenState> = _state.asStateFlow()

    init {
        if (_state.value is UserListScreenState.Default) {
            setAction(UserListScreenAction.LoadList)
        }
    }

    fun setAction(action: UserListScreenAction) {
        when (action) {
            is UserListScreenAction.LoadList -> {
                loadList()
            }

            is UserListScreenAction.RefreshList -> {
                loadList(true)
            }
        }
    }

    private fun setState(state: UserListScreenState) {
        savedStateHandle["state"] = state
        _state.value = state
    }


    private fun loadList(update: Boolean = false) {
        userListUseCase.getAllUsers(update)
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