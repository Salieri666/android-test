package com.example.androidtest.ui.viewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import com.example.androidtest.di.component.DaggerUserDetailsScreenViewModelComponent
import com.example.androidtest.di.component.UserDetailsScreenViewModelComponent
import com.example.androidtest.di.module.AppModule
import com.example.androidtest.di.utils.GenericSavedStateViewModelFactory
import com.example.androidtest.domain.useCase.UserUseCase
import com.example.androidtest.domain.utils.IntentActions
import com.example.androidtest.ui.screen.userDetailsScreen.UserDetailsScreenAction
import com.example.androidtest.ui.screen.userDetailsScreen.UserDetailsScreenState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class UserDetailsScreenViewModel(
    private val userListUseCase: UserUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val userId: Long?,
    private val intentActions: IntentActions
): ViewModel() {

    private val _state = MutableStateFlow(
        savedStateHandle.get<UserDetailsScreenState>("state") ?: UserDetailsScreenState.Default
    )
    val state: StateFlow<UserDetailsScreenState> = _state.asStateFlow()

    init {
        if (userId != null && _state.value is UserDetailsScreenState.Default) {
           setAction(UserDetailsScreenAction.LoadUserDetail(userId))
        }
    }

    private fun setState(state: UserDetailsScreenState) {
        savedStateHandle["state"] = state
        _state.value = state
    }

    fun setAction(action: UserDetailsScreenAction) {
        when(action) {
            is UserDetailsScreenAction.LoadUserDetail -> {
                loadUser(action.id)
            }

            is UserDetailsScreenAction.OpenEmail -> {
                intentActions.composeEmail(action.email)
            }

            is UserDetailsScreenAction.OpenPhone -> {
                intentActions.dialPhoneNumber(action.phone)
            }

            is UserDetailsScreenAction.OpenMap -> {
                intentActions.showMap(action.latitude, action.longitude)
            }
        }
    }

    private fun loadUser(userId: Long) {
        viewModelScope.launch {
            try {
                setState(UserDetailsScreenState.Loading)

                val userDetails = userListUseCase.getUserDetails(userId)
                setState(UserDetailsScreenState.Success(userDetails))
            } catch (e: CancellationException) {
                Log.e("ERROR", "Error in UserDetailsScreen", e)
                throw e
            } catch (e: Exception) {
                Log.e("ERROR", "Error in UserDetailsScreen", e)
                setState(UserDetailsScreenState.Error(e))
            }
        }
    }

}

fun getUserDetailsScreenViewModel(
    appModule: AppModule,
    navBackStackEntry: NavBackStackEntry,
    userId: Long
): UserDetailsScreenViewModel {
    val userDetailsScreenViewModelComponent: UserDetailsScreenViewModelComponent =
        DaggerUserDetailsScreenViewModelComponent.builder()
            .appModule(appModule)
            .build()

    return GenericSavedStateViewModelFactory(
        userDetailsScreenViewModelComponent.getUserDetailsScreenViewModelFactory(),
        mapOf("userId" to userId),
        navBackStackEntry
    ).create(UserDetailsScreenViewModel::class.java)
}