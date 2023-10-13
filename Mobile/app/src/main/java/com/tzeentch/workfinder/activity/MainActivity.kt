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
import com.tzeentch.workfinder.ui.composables.Greeting
import com.tzeentch.workfinder.ui.composables.Registration
import com.tzeentch.workfinder.ui.theme.WorkfinderTheme
import com.tzeentch.workfinder.viewModels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkfinderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = NavigationItem.Greeting.route
                    ) {
                        composable(route = NavigationItem.Greeting.route) {
                            Greeting(navController, viewModel)
                        }
                        composable(route = NavigationItem.Authorization.route) {
                            Authorization(navController, viewModel)
                        }
                        composable(route = NavigationItem.Registration.route) {
                            Registration(navController = navController, viewModel)
                        }
                    }
                }
            }
        }
    }
}