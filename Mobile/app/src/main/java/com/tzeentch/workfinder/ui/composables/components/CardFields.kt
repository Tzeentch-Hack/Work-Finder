package com.tzeentch.workfinder.ui.composables.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CardFields(
    textField: String,
    inputHolder: MutableState<String>,
    buttonHolder: MutableState<String>,
    buttonTitle: String,
    buttonText1: String,
    buttonText2: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomOutlinedTextField(
            defText = inputHolder.value,
            defTitle = textField,
            hint = "",
            onValueChange = {
                inputHolder.value = it
            })

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = buttonTitle,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            color = Color(0xFFD4D4D5)
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { buttonHolder.value = buttonText1 },
                shape = RoundedCornerShape(
                    topStart = 22.dp,
                    topEnd = 0.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 22.dp
                ),
                border = BorderStroke(color = Color(0x4DFDFDFD), width = 1.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (buttonText1 == buttonHolder.value) Color(0xff284779) else Color.Transparent,
                    contentColor = Color(0xFFD4D4D5)
                ), modifier = Modifier
                    .weight(1F)
                    .height(55.dp)
            ) {
                Text(text = buttonText1)
            }
            OutlinedButton(
                onClick = { buttonHolder.value = buttonText2 },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (buttonText2 == buttonHolder.value) Color(0xff284779) else Color.Transparent,
                    contentColor = Color(0xFFD4D4D5),
                ),
                border = BorderStroke(color = Color(0x4DFDFDFD), width = 1.dp),
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 22.dp,
                    bottomEnd = 22.dp,
                    bottomStart = 0.dp
                ),
                modifier = Modifier
                    .weight(1F)
                    .height(55.dp)
            ) {
                Text(text = buttonText2)
            }
        }
    }
}