package com.example.androidtest.ui.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import com.example.androidtest.di.component.DaggerUserListScreenViewModelComponent
import com.example.androidtest.di.component.UserListScreenViewModelComponent
import com.example.androidtest.di.module.AppModule
import com.example.androidtest.di.utils.GenericSavedStateViewModelFactory
import com.example.androidtest.domain.exception.NetworkConnectionException
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
                if (cause is NetworkConnectionException) {
                    setState(UserListScreenState.ConnectionProblem)
                } else {
                    Log.e("ERROR", "Error in UserListScreen", cause)
                    setState(UserListScreenState.Error(cause))
                }
            }
            .launchIn(viewModelScope)
    }


}

fun getUserListScreenViewModel(
    context: Context,
    navBackStackEntry: NavBackStackEntry
): UserListScreenViewModel {
    val userListScreenComponent: UserListScreenViewModelComponent =
        DaggerUserListScreenViewModelComponent.builder()
            .appModule(AppModule(context))
            .build()

    return GenericSavedStateViewModelFactory(
        userListScreenComponent.getUserListScreenViewModelFactory(),
        null,
        navBackStackEntry
    ).create(UserListScreenViewModel::class.java)
}