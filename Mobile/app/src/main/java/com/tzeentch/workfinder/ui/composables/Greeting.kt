package com.tzeentch.workfinder.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tzeentch.workfinder.ui.GreetingStates
import com.tzeentch.workfinder.ui.composables.components.TypewriterText
import com.tzeentch.workfinder.viewModels.MainViewModel

@Composable
fun Greeting(viewModel: MainViewModel) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(70.dp)
    ) {

        TypewriterText(texts = listOf("Hello!", "Salom!", "Привет!"))

        Spacer(modifier = Modifier.height(45.dp))

        Text(
            text = "Я твой интелектуальный ассистент.\n" + "Помогу тебе с поиском работы.",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
            color = Color(0xFF283C5D)
        )

        Spacer(modifier = Modifier.height(45.dp))

        Button(
            onClick = { viewModel.setState(GreetingStates.Form("")) },
            modifier = Modifier.fillMaxWidth().height(55.dp)
        ) {
            Text(text = "Начать", fontSize = 23.sp)
        }
    }
}