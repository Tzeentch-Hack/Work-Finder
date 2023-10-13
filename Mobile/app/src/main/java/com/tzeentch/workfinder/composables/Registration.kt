package com.tzeentch.workfinder.composables

import android.graphics.pdf.PdfDocument.Page
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tzeentch.workfinder.NavigationItem
import com.tzeentch.workfinder.composables.component.CardFields
import com.tzeentch.workfinder.composables.component.CustomOutlinedTextField
import com.tzeentch.workfinder.composables.component.PagerController

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Registration(navController: NavController) {
    val page = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        5
    }

    val age = remember {
        mutableStateOf("")
    }

    val sex = remember {
        mutableStateOf("")
    }

    HorizontalPager(state = page, userScrollEnabled = false, pageSpacing = 25.dp) {
        Card {
            when (page.currentPage) {
                0 -> {
                    CardFields(
                        inputHolder = age,
                        textField = "Age",
                        buttonTitle = "Sex",
                        buttonText1 = "man",
                        buttonText2 = "women",
                        buttonHolder = sex
                    )
                    PagerController(pager = page)
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
                    PagerController(pager = page)
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
                    PagerController(pager = page)
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
                    PagerController(pager = page)
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
                    PagerController(pager = page)
                }
            }
        }
    }

}