package com.tzeentch.workfinder.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tzeentch.workfinder.Constants
import com.tzeentch.workfinder.NavigationItem
import com.tzeentch.workfinder.dto.ProfileDto
import com.tzeentch.workfinder.ui.GreetingStates
import com.tzeentch.workfinder.ui.composables.components.CardFields
import com.tzeentch.workfinder.ui.composables.components.CustomOutlinedTextField
import com.tzeentch.workfinder.ui.composables.components.PagerController
import com.tzeentch.workfinder.ui.composables.components.loader.Loader
import com.tzeentch.workfinder.viewModels.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Registration(navController: NavController, viewModel: MainViewModel) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = 0, initialPageOffsetFraction = 0f
    ) {
        Constants.FINISH_PAGE + 1
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

    val email = remember {
        mutableStateOf("")
    }

    val pref = remember {
        mutableStateOf("")
    }

    val phone = remember {
        mutableStateOf("")
    }
    when (viewModel.greetingState.collectAsState().value) {
        is GreetingStates.Loading -> {
            Loader()
        }

        is GreetingStates.GoToMainScreen -> {
            navController.navigate(NavigationItem.MainScreen.route)
        }

        else -> {
            HorizontalPager(state = pagerState, userScrollEnabled = false, pageSpacing = 25.dp) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF47669B)),
                    modifier = Modifier.padding(35.dp)
                ) {
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
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp, horizontal = 8.dp)
                            ) {
                                Text(text = "Режим работы")
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    OutlinedButton(
                                        onClick = { opMode.value = "Удаленная" },
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            containerColor = if ("Удаленная" == opMode.value) Color(
                                                0xff284779
                                            ) else Color.Transparent,
                                            contentColor = Color.White
                                        ),
                                        border = BorderStroke(
                                            color = Color(0x4DFDFDFD),
                                            width = 1.dp
                                        ),
                                        shape = RoundedCornerShape(
                                            topStart = 22.dp,
                                            topEnd = 0.dp,
                                            bottomStart = 0.dp,
                                            bottomEnd = 0.dp
                                        ),
                                        modifier = Modifier
                                            .weight(1F)
                                            .height(55.dp)
                                    ) {
                                        Text(text = "Удаленная")
                                    }
                                    OutlinedButton(
                                        onClick = { opMode.value = "В офисе" },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if ("В офисе" == opMode.value) Color(
                                                0xff284779
                                            ) else Color.Transparent,
                                            contentColor = Color.White
                                        ),
                                        border = BorderStroke(
                                            color = Color(0x4DFDFDFD),
                                            width = 1.dp
                                        ),
                                        shape = RoundedCornerShape(
                                            topStart = 0.dp,
                                            topEnd = 22.dp,
                                            bottomStart = 0.dp,
                                            bottomEnd = 0.dp
                                        ),
                                        modifier = Modifier
                                            .weight(1F)
                                            .height(55.dp)
                                    ) {
                                        Text(text = "В офисе")
                                    }
                                }
                                OutlinedButton(
                                    onClick = { opMode.value = "Гибридная" },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if ("Гибридная" == opMode.value) Color(
                                            0xff284779
                                        ) else Color.Transparent,
                                        contentColor = Color.White
                                    ),
                                    border = BorderStroke(color = Color(0x4DFDFDFD), width = 1.dp),
                                    shape = RoundedCornerShape(
                                        topStart = 0.dp,
                                        topEnd = 0.dp,
                                        bottomStart = 22.dp,
                                        bottomEnd = 22.dp
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(55.dp)
                                ) {
                                    Text(text = "Гибридная")
                                }
                            }
                        }

                        4 -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp, horizontal = 8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CustomOutlinedTextField(
                                    defText = email.value,
                                    defTitle = "Email",
                                    hint = "",
                                    onValueChange = {
                                        email.value = it
                                    })

                                Spacer(modifier = Modifier.height(30.dp))

                                CustomOutlinedTextField(
                                    defText = phone.value,
                                    defTitle = "Телефон",
                                    hint = "",
                                    onValueChange = {
                                        phone.value = it
                                    })
                            }
                        }

                        5 -> {
                            CustomOutlinedTextField(
                                defText = email.value,
                                defTitle = "Предпочтения",
                                hint = "",
                                onValueChange = {
                                    email.value = it
                                })
                        }
                    }
                    PagerController(currPage = res, onNextClick = {
                        onScrollClick(res + 1)
                    }, onPrevClick = {
                        onScrollClick(res - 1)
                    }, onFinishClick = {
                        viewModel.sendProfileData(
                            ProfileDto(
                                phone = phone.value,
                                email = email.value,
                                education = education.value,
                                age = age.value,
                                gender = sex.value,
                                residence = location.value,
                                remoteOrLocal = opMode.value,
                                workMode = graph.value,
                                laborPreferences = work.value,
                                spec = "",
                                pref = pref.value
                            )
                        )
                    })
                }
            }
        }
    }
}