package com.example.androidtest.ui.viewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtest.domain.useCase.UserUseCase
import com.example.androidtest.ui.screen.userDetailsScreen.UserDetailsScreenState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class UserDetailsScreenViewModel(
    private val userListUseCase: UserUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val userId: Long?
): ViewModel() {

    private val _state = MutableStateFlow(
        savedStateHandle.get<UserDetailsScreenState>("state") ?: UserDetailsScreenState.Default
    )
    val state: StateFlow<UserDetailsScreenState> = _state.asStateFlow()

    init {
        userId?.let {
            viewModelScope.launch {
                try {
                    val userDetails = userListUseCase.getUserDetails(userId)
                    setState(UserDetailsScreenState.Success(userDetails))
                } catch (e : CancellationException) {
                    Log.e("ERROR", "Error in UserDetailsScreen", e)
                    throw e
                } catch (e: Exception) {
                    Log.e("ERROR", "Error in UserDetailsScreen", e)
                    setState(UserDetailsScreenState.Error(e))
                }
            }
        }
    }

    private fun setState(state: UserDetailsScreenState) {
        savedStateHandle["state"] = state
        _state.value = state
    }

}