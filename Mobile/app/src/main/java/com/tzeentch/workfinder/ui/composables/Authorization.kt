package com.tzeentch.workfinder.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tzeentch.workfinder.NavigationItem
import com.tzeentch.workfinder.ui.composables.components.CustomOutlinedTextField
import com.tzeentch.workfinder.viewModels.MainViewModel

@Composable
fun Authorization(navController: NavController,viewModel: MainViewModel) {
    var name by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val error by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.padding(top = 45.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {

        Text(text = "Введите свои регистрационные данные")

        CustomOutlinedTextField(
            defText = name,
            defError = error,
            hint = "работник месяца 213",
            onValueChange = {
                name = it
            })

        CustomOutlinedTextField(
            defText = password,
            defError = error,
            hint = "*******",
            onValueChange = {
                password = it
            })

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Button(onClick = {
                navController.navigate(NavigationItem.Registration.route)
            }) {
                Text(text = "Войти")
            }

            Button(onClick = { }) {
                Text(text = "Создать")
            }
        }
    }
}