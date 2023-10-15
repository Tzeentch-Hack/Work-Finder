package com.tzeentch.workfinder.ui.composables

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.tzeentch.workfinder.NavigationItem
import com.tzeentch.workfinder.ui.MainScreenStates
import com.tzeentch.workfinder.ui.composables.components.CustomOutlinedTextField
import com.tzeentch.workfinder.ui.composables.components.loader.Loader
import com.tzeentch.workfinder.viewModels.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel) {


    val showVacancies = remember {
        mutableStateOf(true)
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.getCoursesAndVacancies(query = "")
    }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Button(
                onClick = { navController.navigate(NavigationItem.Profile.route) },
                modifier = Modifier.height(45.dp)
            ) {
                Text(text = "Профиль ->", fontSize = 18.sp)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            when (val res = viewModel.mainState.collectAsState().value) {
                is MainScreenStates.Content -> {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(
                            onClick = { showVacancies.value = true },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (showVacancies.value) Color(0xFF284779) else Color(
                                    0xFF47669B
                                ),
                                contentColor = Color(0xFFD4D4D5)
                            ), modifier = Modifier.weight(1F),
                            shape = RoundedCornerShape(
                                topStart = 22.dp,
                                topEnd = 0.dp,
                                bottomStart = 22.dp,
                                bottomEnd = 0.dp
                            )
                        ) {
                            Text(text = "Вакансии")
                        }
                        OutlinedButton(
                            onClick = { showVacancies.value = false },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (!showVacancies.value) Color(0xFF284779) else Color(
                                    0xFF47669B
                                ),
                                contentColor = Color(0xFFD4D4D5)
                            ),
                            modifier = Modifier.weight(1F),
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 22.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 22.dp
                            )
                        ) {
                            Text(text = "Курсы")
                        }
                    }
                    if (showVacancies.value) {
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            items(res.vacancies.size) { index ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    onClick = {
                                        val url = res.vacancies[index].url
                                        val i = Intent(Intent.ACTION_VIEW)
                                        i.data = Uri.parse(url)
                                        startActivity(context, i, null)
                                    },
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(
                                            0xFF47669B
                                        )
                                    )
                                ) {
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        Column(
                                            modifier = Modifier
                                                .height(120.dp)
                                                .padding(5.dp)
                                        ) {
                                            res.vacancies[index].spec?.let {
                                                Text(
                                                    text = it,
                                                    modifier = Modifier.fillMaxWidth(),
                                                    textAlign = TextAlign.Start
                                                )
                                            }
                                            res.vacancies[index].title?.let { Text(text = it) }
                                            res.vacancies[index].empl?.let {
                                                Text(
                                                    text = it,
                                                    modifier = Modifier
                                                        .fillMaxWidth(),
                                                    textAlign = TextAlign.End,
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            contentPadding = PaddingValues(vertical = 15.dp)
                        ) {
                            items(res.courses.size) { index ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    onClick = {
                                        val url = res.courses[index].url
                                        val i = Intent(Intent.ACTION_VIEW)
                                        i.data = Uri.parse(url)
                                        startActivity(context, i, null)
                                    },
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(
                                            0xFF47669B
                                        )
                                    )
                                ) {
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        AsyncImage(
                                            model = res.courses[index].image,
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .height(120.dp)
                                                .width(120.dp)
                                        )
                                        Box(
                                            modifier = Modifier
                                                .height(120.dp)
                                                .padding(5.dp)
                                        ) {
                                            res.courses[index].title?.let { Text(text = it) }
                                            res.courses[index].subTitle?.let {
                                                Text(
                                                    text = it,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .align(Alignment.BottomEnd),
                                                    textAlign = TextAlign.End,
                                                )
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }

                is MainScreenStates.Error -> {

                }

                is MainScreenStates.Initial -> {

                }

                else -> {
                    Loader()
                }
            }
        }
    }
}