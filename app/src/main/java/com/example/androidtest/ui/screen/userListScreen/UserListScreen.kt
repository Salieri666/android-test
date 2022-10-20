package com.example.androidtest.ui.screen.userListScreen

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.androidtest.R
import com.example.androidtest.ui.component.MessageComponent
import com.example.androidtest.ui.component.UserListComponent
import com.example.androidtest.ui.model.UserUiModel
import com.example.androidtest.ui.utils.getUserUiModelList
import com.example.androidtest.ui.viewModel.UserListScreenViewModel


@Preview(showBackground = true)
@Composable
fun PreviewUserListScreen() {
    val state = UserListScreenState.Success(getUserUiModelList(5))

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
    Scaffold(
        modifier = modifier,
        floatingActionButton = {

            FloatingActionButton(
                onClick = onFloatClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    Icons.Filled.Refresh,
                    stringResource(R.string.refresh),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

        }) { paddingValues ->
        when (state) {
            is UserListScreenState.Success -> {

                UserListComponent(
                    modifier = Modifier.padding(paddingValues),
                    userList = state.list,
                    onClick = onClick
                )

            }

            is UserListScreenState.Loading -> {
                MessageComponent(
                    message = stringResource(R.string.loading),
                    modifier = modifier.fillMaxSize()
                )
            }

            is UserListScreenState.Error -> {
                MessageComponent(
                    message = stringResource(R.string.smth_goes_wrong),
                    modifier = modifier.fillMaxSize()
                )
            }

            is UserListScreenState.Default -> {
                MessageComponent(
                    message = stringResource(R.string.user_list),
                    modifier = modifier.fillMaxSize()
                )
            }

            is UserListScreenState.ConnectionProblem -> {
                MessageComponent(
                    message = stringResource(R.string.connection_problem),
                    modifier = modifier.fillMaxSize()
                )
            }
        }
    }
}
