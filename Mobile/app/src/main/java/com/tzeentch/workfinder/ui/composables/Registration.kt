package com.tzeentch.workfinder.ui.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tzeentch.workfinder.NavigationItem
import com.tzeentch.workfinder.ui.composables.components.CardFields
import com.tzeentch.workfinder.ui.composables.components.PagerController
import com.tzeentch.workfinder.viewModels.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Registration(navController: NavController, viewModel: MainViewModel) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = 0, initialPageOffsetFraction = 0f
    ) {
        4
    }
    val onScrollClick: (Int) -> Unit = remember {
        { index ->
            scope.launch {
                pagerState.animateScrollToPage(index)
            }
        }
    }
    val age = remember {
        mutableStateOf("")
    }

    val sex = remember {
        mutableStateOf("")
    }

    val location = remember {
        mutableStateOf("")
    }

    val graph = remember {
        mutableStateOf("")
    }

    val education = remember {
        mutableStateOf("")
    }

    val work = remember {
        mutableStateOf("")
    }

    val opMode = remember {
        mutableStateOf("")
    }

    HorizontalPager(state = pagerState, userScrollEnabled = false, pageSpacing = 25.dp) {
        Card {
            val res = pagerState.currentPage
            when (res) {
                0 -> {
                    CardFields(
                        inputHolder = age,
                        textField = "Возраст:",
                        buttonTitle = "Пол",
                        buttonText1 = "мужчина",
                        buttonText2 = "женщина",
                        buttonHolder = sex
                    )
                }

                1 -> {
                    CardFields(
                        inputHolder = education,
                        textField = "Образование",
                        buttonText1 = "умственный",
                        buttonTitle = "Какой труд предпочитаете:",
                        buttonText2 = "физический",
                        buttonHolder = work
                    )
                }

                2 -> {
                    CardFields(
                        inputHolder = location,
                        textField = "Место жительства:",
                        buttonText1 = "парт тайм",
                        buttonTitle = "Какой график предпочитаете",
                        buttonText2 = "фулл тайм",
                        buttonHolder = graph
                    )
                }

                3 -> {
                    Text(text = "Режим работы")
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { opMode.value = "Удаленная" },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if ("Удаленная" == opMode.value) Color.Blue else Color.Gray,
                                contentColor = Color.White
                            ),
                            modifier = Modifier.weight(1F)
                        ) {
                            Text(text = "Удаленная")
                        }
                        Button(
                            onClick = { opMode.value = "Гибридная" },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if ("Гибридная" == opMode.value) Color.Blue else Color.Gray,
                                contentColor = Color.White
                            ),
                            modifier = Modifier.weight(1F)
                        ) {
                            Text(text = "Гибридная")
                        }
                        Button(
                            onClick = { opMode.value = "В офисе" },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if ("В офисе" == opMode.value) Color.Blue else Color.Gray,
                                contentColor = Color.White
                            ),
                            modifier = Modifier.weight(1F)
                        ) {
                            Text(text = "В офисе")
                        }
                    }
                }
            }
            PagerController(currPage = res, onNextClick = {
                onScrollClick(res + 1)
            }, onPrevClick = {
                onScrollClick(res - 1)
            }, onFinishClick = {
                navController.navigate(NavigationItem.MainScreen.route)
            })
        }
    }

}