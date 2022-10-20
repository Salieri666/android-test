package com.example.androidtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidtest.di.module.AppModule
import com.example.androidtest.ui.component.TopBarComponent
import com.example.androidtest.ui.model.TopBarModel
import com.example.androidtest.ui.navigation.MainNavigation
import com.example.androidtest.ui.screen.userDetailsScreen.UserDetailsScreen
import com.example.androidtest.ui.screen.userListScreen.UserListScreen
import com.example.androidtest.ui.theme.AndroidTestTheme
import com.example.androidtest.ui.viewModel.UserDetailsScreenViewModel
import com.example.androidtest.ui.viewModel.UserListScreenViewModel
import com.example.androidtest.ui.viewModel.getUserDetailsScreenViewModel
import com.example.androidtest.ui.viewModel.getUserListScreenViewModel

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigureMainScreen(
    modifier: Modifier = Modifier,
    appModule: AppModule
) {
    val navController = rememberNavController()
    val topBarModel = rememberSaveable {
        mutableStateOf(TopBarModel("", false))
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBarComponent(model = topBarModel.value, onBackClick = { navController.navigateUp() })
        }
    ) { paddingValues ->

        ConfigureNavHost(
            modifier = Modifier.padding(paddingValues),
            navController,
            appModule,
            topBarModel
        )

    }


}

@Composable
fun ConfigureNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    appModule: AppModule,
    topBarModel: MutableState<TopBarModel>
) {
    NavHost(navController = navController, startDestination = MainNavigation.UserList.path) {
        composable(MainNavigation.UserList.path) { navBackStackEntry ->

            topBarModel.value = TopBarModel(stringResource(R.string.users), false)

            val vm: UserListScreenViewModel = viewModel {
                getUserListScreenViewModel(appModule, navBackStackEntry)
            }

            UserListScreen(modifier = modifier, vm = vm, navController = navController)
        }

        composable(MainNavigation.UserDetails.path,
            arguments = listOf(navArgument(MainNavigation.UserDetails.arg) {
                type = NavType.LongType
            }
            )) { navBackStackEntry ->

            topBarModel.value = TopBarModel(stringResource(R.string.user_details), true)

            val userId =
                navController.currentBackStackEntry?.arguments?.getLong(MainNavigation.UserDetails.arg)

            userId?.let {
                val vm: UserDetailsScreenViewModel = viewModel {
                    getUserDetailsScreenViewModel(appModule, navBackStackEntry, userId)
                }

                UserDetailsScreen(modifier = modifier, vm = vm, navController = navController)
            }

        }
    }
}
