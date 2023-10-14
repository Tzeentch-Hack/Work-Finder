package com.tzeentch.workfinder.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tzeentch.workfinder.NavigationItem
import com.tzeentch.workfinder.ui.composables.components.TypewriterText
import com.tzeentch.workfinder.viewModels.MainViewModel

@Composable
fun Greeting(navController: NavController, viewModel: MainViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        TypewriterText(texts = listOf("Hello!", "Salom!", "Привет!"))

        Spacer(modifier = Modifier.height(45.dp))

        Text(
            text = "I am your intellectual assistant.\n" + "I will help you find a job.",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(45.dp))

        Button(onClick = { navController.navigate(NavigationItem.Authorization.route) }) {
            Text(text = "Continue")
        }
    }
}