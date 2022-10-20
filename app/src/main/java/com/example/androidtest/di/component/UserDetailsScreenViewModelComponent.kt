package com.example.androidtest.di.component

import androidx.lifecycle.SavedStateHandle
import com.example.androidtest.di.module.NetworkModule
import com.example.androidtest.di.utils.ViewModelAssistedFactory
import com.example.androidtest.domain.useCase.UserListUseCase
import com.example.androidtest.ui.viewModel.UserDetailsScreenViewModel
import dagger.Component
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface UserDetailsScreenViewModelComponent {

    fun getUserDetilsScreenViewModelFactory() : UserDetilsScreenViewModelFactory
}

class UserDetilsScreenViewModelFactory @Inject constructor(
    private val userListUseCase: UserListUseCase
) : ViewModelAssistedFactory<UserDetailsScreenViewModel> {
    override fun create(handle: SavedStateHandle, params: Map<String, Any>?): UserDetailsScreenViewModel {
        return UserDetailsScreenViewModel(userListUseCase, handle, params?.get("userId") as Long?)
    }
}