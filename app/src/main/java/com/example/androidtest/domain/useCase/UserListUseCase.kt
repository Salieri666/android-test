package com.example.androidtest.domain.useCase

import com.example.androidtest.data.model.toUiModel
import com.example.androidtest.data.repo.UserRepo
import com.example.androidtest.ui.model.UserUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserListUseCase @Inject constructor(
    private val userRepo: UserRepo
) {

    fun getUserList(): Flow<List<UserUiModel>> = flow {
        emit(userRepo.getUserList())
    }
        .map { list ->
            list.map { it.toUiModel() }
        }
}