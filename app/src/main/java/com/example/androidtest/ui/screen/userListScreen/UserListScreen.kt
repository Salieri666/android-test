package com.example.androidtest.ui.screen.userListScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidtest.ui.component.MessageComponent
import com.example.androidtest.ui.component.UserListComponent
import com.example.androidtest.ui.model.UserUiModel


@Preview(showBackground = true)
@Composable
fun PreviewUserListScreen() {
    val item = UserUiModel(1, "1", "Test_name", "test@email.com", true)
    val list = List(5) {item}
    val state = UserListScreenState.Success(list)

    UserListScreen(state = state, modifier = Modifier.fillMaxSize())
}

@Preview(showBackground = true)
@Composable
fun PreviewUserListScreenLoading() {
    val state = UserListScreenState.Loading

    UserListScreen(state = state, modifier = Modifier.fillMaxSize())
}


@Composable
fun UserListScreen(
    modifier: Modifier = Modifier,
    state: UserListScreenState,
    onClick: (UserUiModel) -> Unit = {}
) {

    when (state) {
        is UserListScreenState.Success -> {
            UserListComponent(modifier = modifier, userList = state.list, onClick = onClick)
        }

        is UserListScreenState.Loading -> {
            MessageComponent(message = "Loading...", modifier = modifier)
        }

        is UserListScreenState.Error -> {
            MessageComponent(message = "Something goes wrong", modifier = modifier)
        }

        is UserListScreenState.Default -> {
            MessageComponent(message = "User List", modifier = modifier)
        }
    }
}
