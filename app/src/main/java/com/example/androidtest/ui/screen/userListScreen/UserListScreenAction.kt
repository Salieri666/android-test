package com.example.androidtest.ui.screen.userListScreen

sealed class UserListScreenAction {
    object LoadList : UserListScreenAction()
    object RefreshList : UserListScreenAction()
}