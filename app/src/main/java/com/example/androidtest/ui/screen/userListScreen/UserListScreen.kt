package com.example.androidtest.ui.screen.userListScreen

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.androidtest.ui.component.MessageComponent
import com.example.androidtest.ui.component.UserListComponent
import com.example.androidtest.ui.model.UserUiModel
import com.example.androidtest.ui.viewModel.UserListScreenViewModel


@Preview(showBackground = true)
@Composable
fun PreviewUserListScreen() {
    val item = UserUiModel(
        1, 0, "1", "Test_name", "test@email.com", true,
        23, "Company_name", "+11111",
        "test_address", "about", "blue",
        "apple",
        "2022",
        emptyList(),
        "Coordinates_22"
    )
    val list = List(1) { item }
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
    vm: UserListScreenViewModel,
    navController: NavController
) {
    val state by vm.state.collectAsState()
    val context = LocalContext.current

    UserListScreen(
        state = state,
        modifier = modifier,
        onClick = {
            if (it.isActive) {
                navController.navigate("userDetailsScreen/${it.id}")
            } else {
                Toast.makeText(context, "User is disabled", Toast.LENGTH_SHORT).show()
            }
        },
        onFloatClick = {
            vm.setAction(UserListScreenAction.RefreshList)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    modifier: Modifier = Modifier,
    state: UserListScreenState,
    onClick: (UserUiModel) -> Unit = {},
    onFloatClick: () -> Unit = {}
) {

    when (state) {
        is UserListScreenState.Success -> {
            Scaffold(floatingActionButton = {

                FloatingActionButton(onClick = onFloatClick) {
                    Icon(Icons.Filled.Refresh, "Refresh")
                }

            }) { paddingValues ->
                UserListComponent(
                    modifier = modifier.padding(paddingValues),
                    userList = state.list,
                    onClick = onClick
                )
            }
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
