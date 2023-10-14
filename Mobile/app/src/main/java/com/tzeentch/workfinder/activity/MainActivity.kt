package com.tzeentch.workfinder.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tzeentch.workfinder.NavigationItem
import com.tzeentch.workfinder.ui.composables.Authorization
import com.tzeentch.workfinder.ui.composables.MainScreen
import com.tzeentch.workfinder.ui.composables.ProfileScreen
import com.tzeentch.workfinder.ui.composables.Registration
import com.tzeentch.workfinder.ui.theme.WorkfinderTheme
import com.tzeentch.workfinder.viewModels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkfinderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = NavigationItem.Authorization.route
                    ) {
                        composable(route = NavigationItem.Authorization.route) {
                            Authorization(navController, viewModel)
                        }
                        composable(route = NavigationItem.FillQuest.route) {
                            Registration(navController = navController, viewModel)
                        }
                        composable(route = NavigationItem.MainScreen.route) {
                            MainScreen(navController = navController, viewModel)
                        }
                        composable(route = NavigationItem.Profile.route) {
                            ProfileScreen(navController = navController, viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}