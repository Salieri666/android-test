package com.example.androidtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidtest.di.component.DaggerUserListScreenViewModelComponent
import com.example.androidtest.di.component.UserListScreenViewModelComponent
import com.example.androidtest.di.utils.GenericSavedStateViewModelFactory
import com.example.androidtest.ui.screen.userListScreen.UserListScreen
import com.example.androidtest.ui.theme.AndroidTestTheme
import com.example.androidtest.ui.viewModel.UserListScreenViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ConfigureMainScreen(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun ConfigureMainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "userList") {
        composable("userList") { navBackStackEntry ->
            val userListScreenComponent: UserListScreenViewModelComponent =
                DaggerUserListScreenViewModelComponent.builder().build()
            val vm: UserListScreenViewModel = viewModel {
                GenericSavedStateViewModelFactory(
                    userListScreenComponent.getUserListScreenViewModelFactory(),
                    null,
                    navBackStackEntry
                ).create(UserListScreenViewModel::class.java)
            }

            UserListScreen(modifier = modifier, vm = vm)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidTestTheme {
        ConfigureMainScreen()
    }
}