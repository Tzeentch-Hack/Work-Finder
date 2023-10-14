package com.tzeentch.workfinder.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tzeentch.workfinder.NavigationItem
import com.tzeentch.workfinder.ui.GreetingStates
import com.tzeentch.workfinder.ui.composables.components.CustomOutlinedTextField
import com.tzeentch.workfinder.ui.composables.components.loader.Loader
import com.tzeentch.workfinder.viewModels.MainViewModel

@Composable
fun Authorization(navController: NavController, viewModel: MainViewModel) {

    when (val res = viewModel.greetingState.collectAsState().value) {
        is GreetingStates.Initial -> {
            Greeting(viewModel)
        }

        is GreetingStates.Registered -> {
            navController.navigate(NavigationItem.MainScreen.route)
        }

        is GreetingStates.FillQuestionary -> {
            navController.navigate(NavigationItem.FillQuest.route)
        }

        is GreetingStates.Loading -> {
            Loader()
        }

        is GreetingStates.Form -> {
            var name by remember {
                mutableStateOf("")
            }

            var password by remember {
                mutableStateOf("")
            }

            var error by remember(res) {
                mutableStateOf(res.error)
            }

            Column(
                modifier = Modifier.padding(top = 45.dp, start = 15.dp, end = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(40.dp)
            ) {

                Text(text = "Введите свои регистрационные данные")

                CustomOutlinedTextField(defText = name,
                    defTitle = "Имя",
                    hint = "работник месяца 213",
                    isError = error.isNotEmpty(),
                    onValueChange = {
                        error = ""
                        name = it
                    })

                CustomOutlinedTextField(defText = password,
                    defTitle = "Пароль",
                    isError = error.isNotEmpty(),
                    hint = "*******",
                    onValueChange = {
                        error = ""
                        password = it
                    })

                AnimatedVisibility(error.isNotEmpty()) {
                    Text(text = error, textAlign = TextAlign.Center, color = Color.Red)
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(onClick = {
                        viewModel.loginUser(name, password)
                    }, enabled = name.isNotEmpty() && password.isNotEmpty() && error.isEmpty()) {
                        Text(text = "Войти")
                    }

                    Button(onClick = {
                        viewModel.registerUser(name, password)
                    }, enabled = name.isNotEmpty() && password.isNotEmpty() && error.isEmpty()) {
                        Text(text = "Создать")
                    }
                }
            }
        }
    }
}