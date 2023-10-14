package com.tzeentch.workfinder.ui.composables.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PagerController(
    currPage: Int,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
    onFinishClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (currPage != 0) {
            Button(
                onClick = {
                    onPrevClick()
                }, modifier = Modifier
                    .height(55.dp)
                    .weight(1f)
            ) {
                Text(text = "<- Назад")
            }
        }
        if (currPage != 3) {
            Button(
                onClick = {
                    onNextClick()
                }, modifier = Modifier
                    .height(55.dp)
                    .weight(1f)
            ) {
                Text(text = "Вперед ->")
            }
        } else {
            Button(
                onClick = {
                    onFinishClick()
                }, modifier = Modifier
                    .height(55.dp)
                    .weight(1f)
            ) {
                Text(text = "Завершить")
            }
        }
    }
}