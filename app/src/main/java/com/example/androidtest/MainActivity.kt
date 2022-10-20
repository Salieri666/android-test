package com.example.androidtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidtest.di.component.DaggerUserDetailsScreenViewModelComponent
import com.example.androidtest.di.component.DaggerUserListScreenViewModelComponent
import com.example.androidtest.di.component.UserDetailsScreenViewModelComponent
import com.example.androidtest.di.component.UserListScreenViewModelComponent
import com.example.androidtest.di.module.AppModule
import com.example.androidtest.di.utils.GenericSavedStateViewModelFactory
import com.example.androidtest.ui.screen.userDetailsScreen.UserDetailsScreen
import com.example.androidtest.ui.screen.userListScreen.UserListScreen
import com.example.androidtest.ui.theme.AndroidTestTheme
import com.example.androidtest.ui.viewModel.UserDetailsScreenViewModel
import com.example.androidtest.ui.viewModel.UserListScreenViewModel

class MainActivity : ComponentActivity() {

    lateinit var appModule: AppModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appModule = (application as App).appModule

        setContent {
            AndroidTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ConfigureMainScreen(modifier = Modifier.fillMaxSize(), appModule)
                }
            }
        }
    }
}

@Composable
fun ConfigureMainScreen(
    modifier: Modifier = Modifier,
    appModule: AppModule
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "userList") {
        composable("userList") { navBackStackEntry ->

            val vm: UserListScreenViewModel = viewModel {
                getUserListScreenViewModel(appModule, navBackStackEntry)
            }

            UserListScreen(modifier = modifier, vm = vm, navController = navController)
        }

        composable("userDetailsScreen/{userId}",
            arguments = listOf(navArgument("userId") {
                type = NavType.LongType
            }
            )) { navBackStackEntry ->

            val userId =
                navController.currentBackStackEntry?.arguments?.getLong("userId")

            userId?.let {
                val vm: UserDetailsScreenViewModel = viewModel {
                    getUserDetailsScreenViewModel(appModule, navBackStackEntry, userId)
                }

                UserDetailsScreen(modifier = modifier, vm = vm, navController = navController)
            }

        }
    }
}


fun getUserListScreenViewModel(
    appModule: AppModule,
    navBackStackEntry: NavBackStackEntry
): UserListScreenViewModel {
    val userListScreenComponent: UserListScreenViewModelComponent =
        DaggerUserListScreenViewModelComponent.builder()
            .appModule(appModule)
            .build()

    return GenericSavedStateViewModelFactory(
        userListScreenComponent.getUserListScreenViewModelFactory(),
        null,
        navBackStackEntry
    ).create(UserListScreenViewModel::class.java)
}

fun getUserDetailsScreenViewModel(
    appModule: AppModule,
    navBackStackEntry: NavBackStackEntry,
    userId: Long
): UserDetailsScreenViewModel {
    val userDetailsScreenViewModelComponent: UserDetailsScreenViewModelComponent =
        DaggerUserDetailsScreenViewModelComponent.builder()
            .appModule(appModule)
            .build()

    return GenericSavedStateViewModelFactory(
        userDetailsScreenViewModelComponent.getUserDetailsScreenViewModelFactory(),
        mapOf("userId" to userId),
        navBackStackEntry
    ).create(UserDetailsScreenViewModel::class.java)
}

/*@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidTestTheme {
        ConfigureMainScreen()
    }
}*/