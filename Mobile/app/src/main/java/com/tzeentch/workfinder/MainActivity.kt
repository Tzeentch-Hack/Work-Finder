package com.tzeentch.workfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tzeentch.workfinder.composables.Authorization
import com.tzeentch.workfinder.composables.Greeting
import com.tzeentch.workfinder.composables.Registration
import com.tzeentch.workfinder.ui.theme.WorkfinderTheme

class MainActivity : ComponentActivity() {
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
                            Greeting(navController)
                        }
                        composable(route = NavigationItem.Authorization.route) {
                            Authorization(navController)
                        }
                        composable(route = NavigationItem.Registration.route) {
                           Registration(navController = navController)
                        }
                    }
                }
            }
        }
    }
}