package com.tzeentch.workfinder.ui.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tzeentch.workfinder.ui.composables.components.CardFields
import com.tzeentch.workfinder.ui.composables.components.PagerController
import com.tzeentch.workfinder.viewModels.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Registration(navController: NavController, viewModel: MainViewModel) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        5
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

    HorizontalPager(state = pagerState, userScrollEnabled = false, pageSpacing = 25.dp) {
        Card {
            val res = pagerState.currentPage
            when (res) {
                0 -> {
                    CardFields(
                        inputHolder = age,
                        textField = "Age",
                        buttonTitle = "Sex",
                        buttonText1 = "man",
                        buttonText2 = "women",
                        buttonHolder = sex
                    )
                }

                1 -> {
                    CardFields(
                        inputHolder = age,
                        textField = "Age",
                        buttonText1 = "man",
                        buttonTitle = "Sex",
                        buttonText2 = "women",
                        buttonHolder = sex
                    )
                }

                2 -> {
                    CardFields(
                        inputHolder = age,
                        textField = "Age",
                        buttonText1 = "man",
                        buttonTitle = "Sex",
                        buttonText2 = "women",
                        buttonHolder = sex
                    )
                }

                3 -> {
                    CardFields(
                        inputHolder = age,
                        textField = "Age",
                        buttonText1 = "man",
                        buttonTitle = "Sex",
                        buttonText2 = "women",
                        buttonHolder = sex
                    )
                }

                4 -> {
                    CardFields(
                        inputHolder = age,
                        textField = "Age",
                        buttonTitle = "Sex",
                        buttonText1 = "man",
                        buttonText2 = "women",
                        buttonHolder = sex
                    )
                }
            }
            PagerController(currPage = res, onNextClick = {
                onScrollClick(res + 1)
            }, onPrevClick = {
                onScrollClick(res - 1)
            }, onFinishClick = {
                
            })
        }
    }

}