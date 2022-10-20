package com.example.androidtest.di.component

import androidx.lifecycle.SavedStateHandle
import com.example.androidtest.di.module.DatabaseModule
import com.example.androidtest.di.module.NetworkModule
import com.example.androidtest.di.utils.ViewModelAssistedFactory
import com.example.androidtest.domain.useCase.UserUseCase
import com.example.androidtest.ui.viewModel.UserListScreenViewModel
import dagger.Component
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface UserListScreenViewModelComponent {

    fun getUserListScreenViewModelFactory() : UserListScreenViewModelFactory
}

class UserListScreenViewModelFactory @Inject constructor(
    private val userListUseCase: UserUseCase
) : ViewModelAssistedFactory<UserListScreenViewModel> {
    override fun create(handle: SavedStateHandle, params: Map<String, Any>?): UserListScreenViewModel {
        return UserListScreenViewModel(userListUseCase, handle)
    }
}