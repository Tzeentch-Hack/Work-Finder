package com.tzeentch.workfinder.composables.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerController(pager: PagerState) {
    val scope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        if (pager.currentPage != 0) {
            Button(onClick = {
                scope.launch {
                    pager.animateScrollToPage(pager.currentPage - 1)
                }
            }
            ) {
                Text(text = "<- Previous")
            }
        }
        if (pager.currentPage != 4) {
            Button(onClick = {
                scope.launch {
                    pager.animateScrollToPage(pager.currentPage + 1)
                }
            }) {
                Text(text = "Next ->")
            }
        }
    }
}