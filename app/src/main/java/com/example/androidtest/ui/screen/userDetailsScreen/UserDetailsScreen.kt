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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidtest.ui.component.MessageComponent
import com.example.androidtest.ui.component.UserDetailsComponent
import com.example.androidtest.ui.component.UserItemComponent
import com.example.androidtest.ui.model.UserUiModel
import com.example.androidtest.ui.viewModel.UserDetailsScreenViewModel


@Preview(showBackground = true)
@Composable
fun PreviewUserListScreen() {
    val item =  UserUiModel(
        1, 2,"1", "Test_name", "test@email.com", true,
        23, "Company_name", "+11111",
        "test_address", "about", "blue",
        "apple",
        "2022",
        emptyList(),
        "Coordinates_22"
    )
    val list = List(1) {item}

    val user = item.copy(friends = list)
    val state = UserDetailsScreenState.Success(user)

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

    UserDetailsScreen(modifier = modifier, state = state, friendsOnClick = { friend ->
        if (friend.isActive) {
            navController.navigate("userDetailsScreen/${friend.id}")
        } else {
            Toast.makeText(context, "User is disabled", Toast.LENGTH_SHORT).show()
        }
    })
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserDetailsScreen(
    modifier: Modifier = Modifier,
    state: UserDetailsScreenState,
    friendsOnClick: (UserUiModel) -> Unit = {}
) {

    when(state) {
        is UserDetailsScreenState.Success -> {
            BoxWithConstraints {

                LazyColumn(modifier = modifier) {
                    item {
                        UserDetailsComponent(user = state.user)
                    }

                    stickyHeader {
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.background)
                                .padding(20.dp)
                                .fillMaxWidth()

                        ) {
                            Text(
                                text = "Friends", style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
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
            MessageComponent(message = "Loading...", modifier = modifier)
        }

        is UserDetailsScreenState.Error -> {
            MessageComponent(message = "Something goes wrong", modifier = modifier)
        }

        is UserDetailsScreenState.Default -> {
            MessageComponent(message = "User List", modifier = modifier)
        }
    }



}