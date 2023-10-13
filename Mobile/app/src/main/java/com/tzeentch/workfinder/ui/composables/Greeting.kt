package com.tzeentch.workfinder.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tzeentch.workfinder.NavigationItem
import com.tzeentch.workfinder.viewModels.MainViewModel

@Composable
fun Greeting(navController: NavController,viewModel: MainViewModel) {
    Column(
        modifier = Modifier.padding(top = 45.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Привет!\n" +
                    "Я твой интеллектуальный помошник.\n" +
                    "Помогу тебе с поиском работы.",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(45.dp))

        Button(onClick = { navController.navigate(NavigationItem.Authorization.route) }) {
            Text(text = "Приступим")
        }
    }
}