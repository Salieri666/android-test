package com.example.androidtest.ui.screen.userDetailsScreen

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidtest.R
import com.example.androidtest.ui.component.MessageComponent
import com.example.androidtest.ui.component.UserDetailsComponent
import com.example.androidtest.ui.component.UserItemComponent
import com.example.androidtest.ui.model.UserUiModel
import com.example.androidtest.ui.navigation.MainNavigation
import com.example.androidtest.ui.utils.getUserUiModel
import com.example.androidtest.ui.viewModel.UserDetailsScreenViewModel


@Preview(showBackground = true)
@Composable
fun PreviewUserListScreen() {
    val state = UserDetailsScreenState.Success(getUserUiModel(1))

    UserDetailsScreen(state = state, modifier = Modifier.fillMaxSize())
}

@Composable
fun UserDetailsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    vm: UserDetailsScreenViewModel
) {
    val state by vm.state.collectAsState()
    val context = LocalContext.current

    UserDetailsScreen(
        modifier = modifier,
        state = state,
        friendsOnClick = { friend ->
            if (friend.isActive) {
                navController.navigate(MainNavigation.UserDetails.pathWithArgs(friend.id))
            } else {
                Toast.makeText(context, context.getString(R.string.user_is_disabled), Toast.LENGTH_SHORT).show()
            }
        },
        onEmailClick = {
            vm.setAction(UserDetailsScreenAction.OpenEmail(it))
        },
        onPhoneClick = {
            vm.setAction(UserDetailsScreenAction.OpenPhone(it))
        },
        onCoordinatesClick = { latitude, longitude ->
            vm.setAction(UserDetailsScreenAction.OpenMap(latitude, longitude))
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserDetailsScreen(
    modifier: Modifier = Modifier,
    state: UserDetailsScreenState,
    friendsOnClick: (UserUiModel) -> Unit = {},
    onPhoneClick: (String) -> Unit = {},
    onEmailClick: (String) -> Unit = {},
    onCoordinatesClick: (Double, Double) -> Unit = { _, _ -> }
) {

    when (state) {
        is UserDetailsScreenState.Success -> {
            BoxWithConstraints {

                LazyColumn(modifier = modifier) {
                    item {
                        UserDetailsComponent(
                            user = state.user,
                            onPhoneClick = onPhoneClick,
                            onEmailClick = onEmailClick,
                            onCoordinatesClick = onCoordinatesClick
                        )
                    }

                    stickyHeader {
                        Box(
                            modifier = Modifier
                                .padding(bottom = 5.dp)
                                .background(MaterialTheme.colorScheme.tertiary)
                                .padding(20.dp)
                                .fillMaxWidth()

                        ) {
                            Text(
                                text = "Friends", style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onTertiary
                                )
                            )
                        }
                    }


                    items(state.user.friends,
                        key = {
                            it.id
                        }) {
                        UserItemComponent(user = it, modifier = Modifier
                            .padding(bottom = 8.dp, start = 4.dp, end = 4.dp),
                            onClick = { friendsOnClick(it) }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.padding(20.dp))
                    }
                }
            }
        }

        is UserDetailsScreenState.Loading -> {
            MessageComponent(message = stringResource(R.string.loading), modifier = modifier)
        }

        is UserDetailsScreenState.Error -> {
            MessageComponent(message = stringResource(R.string.smth_goes_wrong), modifier = modifier)
        }

        is UserDetailsScreenState.Default -> {
            MessageComponent(message = stringResource(R.string.user_list), modifier = modifier)
        }
    }


}