package com.tzeentch.workfinder.ui.composables

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.tzeentch.workfinder.ui.MainScreenStates
import com.tzeentch.workfinder.ui.composables.components.loader.Loader
import com.tzeentch.workfinder.viewModels.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel) {

    LaunchedEffect(key1 = Unit) {
        viewModel.getCoursesAndVacancies()
    }

    val showVacancies = remember {
        mutableStateOf(true)
    }

    val context = LocalContext.current

    when (val res = viewModel.mainState.collectAsState().value) {
        is MainScreenStates.Content -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = { showVacancies.value = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (showVacancies.value) Color.Green else Color.Gray,
                            contentColor = Color.White
                        ), modifier = Modifier.weight(1F)
                    ) {
                        Text(text = "Вакансии")
                    }
                    Button(
                        onClick = { showVacancies.value = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!showVacancies.value) Color.Blue else Color.Gray,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.weight(1F)
                    ) {
                        Text(text = "Курсы")
                    }
                }
                if (showVacancies.value) {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(res.vacancies.size) { index ->
                            Card {
                                Text(text = "")
                            }
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(15.dp)
                    ) {
                        items(res.courses.size) { index ->
                            Card(modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp), onClick = {
                                val url = res.courses[index].url
                                val i = Intent(Intent.ACTION_VIEW)
                                i.data = Uri.parse(url)
                                startActivity(context, i, null)
                            }) {
                                AsyncImage(
                                    model = res.courses[index].image,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp)
                                )
                                res.courses[index].title?.let { Text(text = it) }
                                res.courses[index].subTitle?.let { Text(text = it) }
                            }
                        }
                    }
                }
            }
        }

        is MainScreenStates.Error -> {

        }

        else -> {
            Loader()
        }
    }
}